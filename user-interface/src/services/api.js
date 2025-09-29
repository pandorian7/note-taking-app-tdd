import axios from 'axios'

// Get the base URL from environment variables
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL
if (!API_BASE_URL) {
  throw new Error('VITE_API_BASE_URL environment variable is not set')
}

// Token management utilities
export const tokenManager = {
  // Get token from localStorage
  getToken: () => {
    try {
      return localStorage.getItem('authToken')
    } catch (error) {
      console.error('Error getting token from localStorage:', error)
      return null
    }
  },

  // Store token in localStorage
  setToken: (token) => {
    try {
      if (token) {
        localStorage.setItem('authToken', token)
      } else {
        localStorage.removeItem('authToken')
      }
    } catch (error) {
      console.error('Error storing token in localStorage:', error)
    }
  },

  // Remove token from localStorage
  clearToken: () => {
    try {
      localStorage.removeItem('authToken')
    } catch (error) {
      console.error('Error clearing token from localStorage:', error)
    }
  },

  // Check if user is authenticated
  isAuthenticated: () => {
    const token = tokenManager.getToken()
    return !!token
  }
}

// Create axios instance with custom configuration
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000, // 10 seconds timeout
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to add auth token to every request
apiClient.interceptors.request.use(
  (config) => {
    const token = tokenManager.getToken()
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

apiClient.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {

    // Throw error property if available, otherwise throw code property
    if (error.response?.data?.error) {
      throw new Error(error.response.data.error)
    } else if (error.response?.data?.code) {
      throw new Error(error.response.data.code)
    }

    return Promise.reject(error)
  }
)

// Authentication API functions
export const authAPI = {
  // Sign in user
  signIn: async (credentials) => {
    try {
      const response = await apiClient.post('/auth/login', {
        username: credentials.username,
        password: credentials.password,
      })

      // Store the token if login is successful
      if (response.data?.token) {
        tokenManager.setToken(response.data.token)
      }

      return response.data
    } catch (error) {
      if (!error.message) {
        throw new Error('Login failed')
      } else {
        throw error
      }
    }
  },

  // Sign up user
  signUp: async (userData) => {
    try {
      const response = await apiClient.post('/auth/signup', {
        username: userData.username,
        password: userData.password,
        firstName: userData.firstName,
        lastName: userData.lastName,
      })

      // Signup returns empty response with 201 status when successful
      if (response.status === 201) {
        return { success: true, message: 'Account created successfully' }
      }

      return response.data
    } catch (error) {

      if (!error.message) {
        throw new Error('Signup failed')
      } else {
        throw error
      }
    }
  },

  // Sign out user
  signOut: () => {
    tokenManager.clearToken()
    // Redirect to home or login page
    window.location.href = '/'
  },
}

// Notes API functions
export const notesAPI = {
  // Get all notes for the user
  getAllNotes: async () => {
    try {
      const response = await apiClient.get('/notes')
      return response.data.notes
    } catch (error) {
      if (!error.message) {
        throw new Error('Failed to fetch notes')
      } else {
        throw error
      }
    }
  },

  // Create a new note
  createNote: async (noteData) => {
    try {
      const response = await apiClient.post('/notes', {
        title: noteData.title,
        content: noteData.content,
      })
      return response.data
    } catch (error) {
      if (!error.message) {   
        throw new Error('Failed to create note')
      } else {
        throw error
      }
    }
  },

  // Update an existing note
  updateNote: async (noteId, noteData) => {
    try {
      const response = await apiClient.put(`/notes/${noteId}`, {
        title: noteData.title,
        content: noteData.content,
      })
      return response.data
    } catch (error) {
      if (!error.message) {   
        throw new Error('Failed to update note')
      } else {
        throw error
      }
    }
  },

  // Delete a note
  deleteNote: async (noteId) => {
    try {
      const response = await apiClient.delete(`/notes/${noteId}`)
      return response.data
    } catch (error) {
      if (!error.message) {   
        throw new Error('Failed to delete note')
      } else {
        throw error
      }
    }
  },
}

// Export the configured axios client for any custom requests
export { apiClient }

// Export a default object with all API functions
const api = {
  auth: authAPI,
  notes: notesAPI,
  tokenManager,
  client: apiClient,
}

export default api