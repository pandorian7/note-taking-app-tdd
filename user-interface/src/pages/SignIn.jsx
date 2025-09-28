import React, { useState } from 'react'
import { Link } from 'react-router-dom'

const SignIn = () => {
  const [formData, setFormData] = useState({
    username: '',
    password: ''
  })

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    console.log('Sign in data:', formData)
    // Handle sign in logic here
  }

  return (
    <div className="page-container">
      <div className="container">
        <div style={{ maxWidth: '400px', margin: '0 auto' }}>
          <div className="card">
            <div className="card-header">
              <h1 className="card-title">Welcome Back</h1>
              <p className="card-subtitle">Sign in to your account</p>
            </div>
            
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="username" className="form-label">Username</label>
                <input
                  type="text"
                  id="username"
                  name="username"
                  className="form-input"
                  value={formData.username}
                  onChange={handleChange}
                  placeholder="Enter your username"
                  required
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
                  placeholder="Enter your password"
                  required
                />
              </div>
              
              <button type="submit" className="btn btn-primary" style={{ width: '100%', marginTop: '1rem' }}>
                Sign In
              </button>
            </form>
            
            <div style={{ textAlign: 'center', marginTop: '1.5rem' }}>
              <p className="card-subtitle">
                Don't have an account?{' '}
                <Link to="/signup" className="nav-link" style={{ display: 'inline', padding: '0' }}>
                  Sign up here
                </Link>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default SignIn