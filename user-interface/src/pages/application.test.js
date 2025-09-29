const { Builder, By } = require('selenium-webdriver');

describe('Test Note Taking App', () => {
  let driver;

  beforeAll(async () => {
    driver = await new Builder().forBrowser('chrome').build();
  });

  afterAll(async () => {
    await driver.quit();
  });


});