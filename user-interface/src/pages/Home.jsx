import React from 'react'
import { Link } from 'react-router-dom'

const Home = () => {
  return (
    <div className="page-container">
      <div className="container">
        <div className="welcome-section">
          <h1 className="hero-title">NoteTaker</h1>
          <p className="hero-subtitle">
            Your personal space for capturing ideas, organizing thoughts, and keeping track of what matters most.
          </p>
          
          <div className="nav-links">
            <Link to="/notes" className="btn btn-primary">
              📝 View My Notes
            </Link>
            <Link to="/signin" className="btn btn-outline">
              🔑 Sign In
            </Link>
            <Link to="/signup" className="btn btn-secondary">
              ✨ Get Started
            </Link>
          </div>
        </div>

        <div style={{ marginTop: '4rem' }}>
          <div className="notes-grid" style={{ gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))' }}>
            <div className="card" style={{ textAlign: 'center' }}>
              <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>📝</div>
              <h3 className="note-title">Easy Note Taking</h3>
              <p className="card-subtitle">
                Quickly jot down your thoughts and ideas with our intuitive interface.
              </p>
            </div>
            
            <div className="card" style={{ textAlign: 'center' }}>
              <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>🔍</div>
              <h3 className="note-title">Organize & Search</h3>
              <p className="card-subtitle">
                Keep your notes organized and find them quickly when you need them.
              </p>
            </div>
            
            <div className="card" style={{ textAlign: 'center' }}>
              <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>🔒</div>
              <h3 className="note-title">Secure & Private</h3>
              <p className="card-subtitle">
                Your notes are private and secure. Only you can access your personal content.
              </p>
            </div>
          </div>
        </div>

        <div className="card" style={{ marginTop: '3rem', textAlign: 'center' }}>
          <h2 className="card-title" style={{ marginBottom: '1rem' }}>
            Ready to get started?
          </h2>
          <p className="card-subtitle" style={{ marginBottom: '1.5rem' }}>
            Join thousands of users who trust NoteTaker with their ideas and thoughts.
          </p>
          <Link to="/signup" className="btn btn-primary">
            Create Your Account →
          </Link>
        </div>
      </div>
    </div>
  )
}

export default Home