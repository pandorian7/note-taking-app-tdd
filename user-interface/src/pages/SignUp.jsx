import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import api from '../services/api'

const SignUp = () => {
  const navigate = useNavigate()
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    username: '',
    password: ''
  })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
    // Clear messages when user starts typing
    if (error) setError('')
    if (success) setSuccess('')
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    setError('')
    setSuccess('')

    try {
      const response = await api.auth.signUp({
        firstName: formData.firstName,
        lastName: formData.lastName,
        username: formData.username,
        password: formData.password
      })
      
      console.log('Signup successful:', response)
      setSuccess('Account created successfully! You can now sign in.')
      
      // Reset form
      setFormData({
        firstName: '',
        lastName: '',
        username: '',
        password: ''
      })
      
      // Redirect to sign in page after a short delay
      setTimeout(() => {
        navigate('/signin')
      }, 2000)
      
    } catch (error) {
      console.error('Signup failed:', error)
      
      // Use the actual error message from the API
      setError(error.message || 'Signup failed. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page-container">
      <div className="container">
        <div style={{ maxWidth: '400px', margin: '0 auto' }}>
          <div className="card">
            <div className="card-header">
              <h1 className="card-title">Create Account</h1>
              <p className="card-subtitle">Join us and start taking notes</p>
            </div>
            
            <form onSubmit={handleSubmit}>
              {error && (
                <div style={{
                  backgroundColor: 'var(--danger-light)',
                  color: 'var(--danger-color)',
                  padding: '0.75rem 1rem',
                  borderRadius: '8px',
                  marginBottom: '1rem',
                  fontSize: '0.875rem',
                  border: '1px solid var(--danger-color)'
                }}>
                  {error}
                </div>
              )}
              
              {success && (
                <div style={{
                  backgroundColor: '#d1fae5',
                  color: 'var(--secondary-color)',
                  padding: '0.75rem 1rem',
                  borderRadius: '8px',
                  marginBottom: '1rem',
                  fontSize: '0.875rem',
                  border: '1px solid var(--secondary-color)'
                }}>
                  {success}
                </div>
              )}
              
              <div className="form-group">
                <label htmlFor="firstName" className="form-label">First Name</label>
                <input
                  type="text"
                  id="firstName"
                  name="firstName"
                  className="form-input"
                  value={formData.firstName}
                  onChange={handleChange}
                  placeholder="Enter your first name"
                  required
                  disabled={loading}
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="lastName" className="form-label">Last Name</label>
                <input
                  type="text"
                  id="lastName"
                  name="lastName"
                  className="form-input"
                  value={formData.lastName}
                  onChange={handleChange}
                  placeholder="Enter your last name"
                  required
                  disabled={loading}
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="username" className="form-label">Username</label>
                <input
                  type="text"
                  id="username"
                  name="username"
                  className="form-input"
                  value={formData.username}
                  onChange={handleChange}
                  placeholder="Choose a username"
                  required
                  disabled={loading}
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="password" className="form-label">Password</label>
                <input
                  type="password"
                  id="password"
                  name="password"
                  className="form-input"
                  value={formData.password}
                  onChange={handleChange}
                  placeholder="Create a password"
                  required
                  disabled={loading}
                />
              </div>
              
              <button 
                type="submit" 
                className="btn btn-primary" 
                style={{ width: '100%', marginTop: '1rem' }}
                disabled={loading}
              >
                {loading ? 'Creating Account...' : 'Create Account'}
              </button>
            </form>
            
            <div style={{ textAlign: 'center', marginTop: '1.5rem' }}>
              <p className="card-subtitle">
                Already have an account?{' '}
                <Link to="/signin" className="nav-link" style={{ display: 'inline', padding: '0' }}>
                  Sign in here
                </Link>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default SignUp