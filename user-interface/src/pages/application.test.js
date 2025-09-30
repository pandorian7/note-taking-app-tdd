const { Builder, By } = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');
const generatePassword = require('generate-password');

const headless = process.env.HEADLESS?.toLowerCase() !== 'false';

describe('Test Note Taking App', () => {
  /** @type {import('selenium-webdriver').WebDriver} */
  let driver;

  const options = new chrome.Options();
  options.addArguments([
    '--headless',
    '--no-sandbox',
    '--disable-dev-shm-usage',
    '--disable-gpu',
    '--disable-extensions',
    '--disable-background-timer-throttling',
    '--disable-backgrounding-occluded-windows',
    '--disable-renderer-backgrounding',
    '--disable-features=TranslateUI',
    '--disable-ipc-flooding-protection',
    '--disable-web-security',
    '--disable-features=VizDisplayCompositor',
    '--disable-logging',
    '--disable-default-apps',
    '--disable-sync',
    '--no-first-run',
    '--no-default-browser-check',
    '--remote-debugging-port=9222'
  ]);

  const baseUrl = 'http://localhost:5173';

  let shortUsername, username, weakPassword, strongPassword;
  let firstName = "user_firstName"
  let lastName = "user_lastName"

  let noteData = {
    title: {
      original: "Original Title",
      updated: "Updated Title"
    },
    content: {
      original: "This is the original content of the note.",
      updated: "This is the updated content of the note."
    }
  }

  beforeAll(async () => {
    if (headless) {
      driver = await new Builder()
        .forBrowser('chrome')
        .setChromeOptions(options)
        .build();
    } else {
      driver = await new Builder()
        .forBrowser('chrome')
        .build();
    }

    const rand = Math.random().toString(36).substring(2, 6);
    shortUsername = `u${rand.substring(0, 2)}`;
    username = `user${rand}`;
    weakPassword = generatePassword.generate({ length: 6, numbers: true });
    strongPassword = generatePassword.generate({ length: 12, numbers: true, symbols: true, uppercase: true });
  });

  const countElements = async (findBy) => {
    return await driver.findElements(findBy).then(elements => elements.length);
  }

  const parseNote = async (noteElement) => {
    return {
      element: noteElement,
      title: await noteElement.findElement(By.css('h3.note-title')).getText(),
      content: await noteElement.findElement(By.css('p.note-content')).getText(),
      edit: async () => await noteElement.findElement(By.css('button.btn-secondary')).click(),
      delete: async () => await noteElement.findElement(By.css('button.btn-danger')).click(),
    };
  }

  const element = {
    signinButton: () => driver.findElement(By.css('a[href="/signin"]')),
    getStartedButton: () => driver.findElement(By.css('a.btn-secondary[href="/signup"]')),
    createYourAccountButton: () => driver.findElement(By.css('a.btn-primary[href="/signup"]')),
    backToHomeButton: () => driver.findElement(By.css('a[href="/"]')),
    addNewNoteButton: () => driver.findElement(By.css('button.btn-primary')),
    signOutButton: () => driver.findElement(By.css('button.btn-danger')),
    notes: async () => {
      const noteElements = await driver.findElements(By.css('.note-card'));
      return Promise.all(noteElements.map(parseNote));
    }
  }

  const input = {
    firstName: () => driver.findElement(By.css("input#firstName")),
    lastName: () => driver.findElement(By.css("input#lastName")),
    username: () => driver.findElement(By.css("input#username")),
    password: () => driver.findElement(By.css("input#password")),
    title: (edit) => driver.findElement(By.css(`input#${edit ? 'edit-title' : 'title'}`)),
    content: (edit) => driver.findElement(By.css(`textarea#${edit ? 'edit-content' : 'content'}`)),
    confirmDelete: () => driver.findElement(By.css('div.modal-footer > button.btn-danger')),
    submit: () => driver.findElement(By.css('button[type="submit"]'))

  }

  const occurred = {
    shortUsername: async () => driver.getPageSource().then(source => source.includes('Username Should be longer')),
    weakPassword: async () => driver.getPageSource().then(source => source.includes('Password Should have at least 1 Capital Letter, 1 Simple Letter, 1 Number, 1 Special Character')),
    accountCreated: async () => driver.getPageSource().then(source => source.includes('Account created successfully')),
    invalidCredentials: async () => driver.getPageSource().then(source => source.includes('Username/Password is Incorrect')),
    noteModal: async () => !!(await countElements(By.css('.modal'))),
    deleteNoteConfirm: async () => driver.getPageSource().then(source => source.includes('Are you sure you want to delete this note')),
  }

  const base = async () => await driver.get(baseUrl);
  const wait = async (ms) => await driver.sleep(ms ?? 1000);

  test('Go to sign in page', async () => {
    await base().then(wait)
    element.signinButton().click();
    await wait();
    const url = await driver.getCurrentUrl();
    expect(url).toBe(baseUrl + '/signin');
  });

  test('Go to sign up page using get started button', async () => {
    await base().then(wait);
    element.getStartedButton().click();
    await wait();
    const url = await driver.getCurrentUrl();
    expect(url).toBe(baseUrl + '/signup');
  });

  test('Go to sign up page using create your account button', async () => {
    await base().then(wait);
    element.createYourAccountButton().click();
    await wait();
    const url = await driver.getCurrentUrl();
    expect(url).toBe(baseUrl + '/signup');
  });

  test('Login with short username should show error', async () => {
    await driver.get(`${baseUrl}/signup`)
    await wait();

    await input.firstName().sendKeys(firstName);
    await input.lastName().sendKeys(lastName);
    await input.username().sendKeys(shortUsername);
    await input.password().sendKeys(strongPassword);
    await input.submit().click();
    await wait();
    expect((await occurred.shortUsername())).toBe(true);
  });

  test('Login with weak password should show error', async () => {
    await driver.get(`${baseUrl}/signup`)
    await wait();

    await input.firstName().sendKeys(firstName);
    await input.lastName().sendKeys(lastName);
    await input.username().sendKeys(username);
    await input.password().sendKeys(weakPassword);
    await input.submit().click();
    await wait();
    expect((await occurred.weakPassword())).toBe(true);
  });

  test('Signup with valid username and strong password should create account', async () => {
    await driver.get(`${baseUrl}/signup`)
    await wait();

    await input.firstName().sendKeys(firstName);
    await input.lastName().sendKeys(lastName);
    await input.username().sendKeys(username);
    await input.password().sendKeys(strongPassword);
    await input.submit().click();
    await wait();
    expect((await occurred.accountCreated())).toBe(true);
    await wait(2000);
    expect(await driver.getCurrentUrl()).toBe(baseUrl + '/signin');
  });

  test('Login with invalid random credentials should show error', async () => {
    await driver.get(`${baseUrl}/signin`);
    await wait();

    const invalidUsername = "wrong_username";
    const invalidPassword = "wrong_password";


    await input.username().sendKeys(invalidUsername);
    await input.password().sendKeys(invalidPassword);
    await input.submit().click();
    await wait();

    expect(await occurred.invalidCredentials()).toBe(true);
  });



  test('Login with correct credentials should redirect to /notes', async () => {
    await driver.get(`${baseUrl}/signin`);
    await wait();

    await input.username().sendKeys(username);
    await input.password().sendKeys(strongPassword);
    await input.submit().click();
    await wait();

    const url = await driver.getCurrentUrl();
    expect(url).toBe(baseUrl + '/notes');
  });

  test('Back to home button should redirect to home page', async () => {
    await driver.get(`${baseUrl}/notes`);
    await wait();

    await element.backToHomeButton().click();
    await wait();

    const url = await driver.getCurrentUrl();
    expect(url).toBe(baseUrl + '/');
  });

  test('Add New Note button should open note modal', async () => {

    await driver.get(`${baseUrl}/notes`);
    await wait();
    await element.addNewNoteButton().click();
    await wait();

    expect(await occurred.noteModal()).toBe(true);
  });



  test('Should create a new note and display it in notes list', async () => {

    await driver.get(`${baseUrl}/notes`);
    await wait();

    await element.addNewNoteButton().click();
    await wait();
    await input.title().sendKeys(noteData.title.original);
    await input.content().sendKeys(noteData.content.original);
    await input.submit().click();
    await wait(2000);

    const notes = await element.notes();

    expect(notes.length).toBe(1);
    expect(notes[0].title).toBe(noteData.title.original);
    expect(notes[0].content).toBe(noteData.content.original);
  });

  test('Edit dialog should prefill textboxes with note data', async () => {
    await driver.get(`${baseUrl}/notes`);
    await wait();

    let notes = await element.notes();
    expect(notes.length).toBeGreaterThan(0);

    await notes[0].edit();
    await wait();

    const titleInput = await input.title('edit');
    const contentInput = await input.content('edit');

    const titleValue = await titleInput.getAttribute('value');
    const contentValue = await contentInput.getAttribute('value');

    expect(titleValue).toBe(noteData.title.original);
    expect(contentValue).toBe(noteData.content.original);

  });

  test('Should edit an existing note and update its content', async () => {
    await driver.get(`${baseUrl}/notes`);
    await wait();

    let notes = await element.notes();
    expect(notes.length).toBeGreaterThan(0);

    await notes[0].edit();
    await wait();

    const titleInput = await input.title('edit');
    const contentInput = await input.content('edit');

    await titleInput.clear();
    await titleInput.sendKeys(noteData.title.updated);

    await contentInput.clear();
    await contentInput.sendKeys(noteData.content.updated);

    await input.submit().click();
    await wait(2000);

    notes = await element.notes();

    expect(notes[0].title).toBe(noteData.title.updated);
    expect(notes[0].content).toBe(noteData.content.updated);
  });

  test('Delete confirmation modal should appear when deleting a note', async () => {
    await driver.get(`${baseUrl}/notes`);
    await wait();

    let notes = await element.notes();

    expect(notes.length).toBeGreaterThan(0);

    await notes[0].delete();
    await wait();

    expect(await occurred.deleteNoteConfirm()).toBe(true);
  });

  test('Should delete an existing note and remove it from notes list', async () => {
    await driver.get(`${baseUrl}/notes`);
    await wait();

    let notes = await element.notes();
    expect(notes.length).toBe(1);

    await notes[0].delete();
    await wait();

    await input.confirmDelete().click();
    await wait(2000);

    notes = await element.notes();
    expect(notes.length).toBe(0);
  });

  test('Sign out button should log out and redirect to home', async () => {

    await driver.get(`${baseUrl}/notes`);
    await wait(2000);
    await element.signOutButton().click();
    await wait(2000);

    const url = await driver.getCurrentUrl();
    expect(url).toBe(baseUrl + '/');
  });

  afterAll(async () => {
    await driver.quit();
  });

});