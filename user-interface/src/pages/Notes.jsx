import React, { useState } from 'react'
import { Link } from 'react-router-dom'

const Notes = () => {
  const [notes, setNotes] = useState([
    {
      id: 1,
      title: "Meeting Notes",
      content: "Discussed the new project requirements with the team. Need to focus on user authentication and note-taking functionality. Timeline: 2 weeks for MVP."
    },
    {
      id: 2,
      title: "Grocery List",
      content: "Milk, Bread, Eggs, Apples, Chicken, Rice, Pasta, Tomatoes, Onions, Cheese. Don't forget to check if we need more coffee!"
    },
    {
      id: 3,
      title: "Book Ideas",
      content: "1. The Art of Problem Solving 2. JavaScript Mastery Guide 3. Design Patterns in Modern Web Development 4. Building Scalable Applications"
    },
    {
      id: 4,
      title: "Workout Plan",
      content: "Monday: Chest & Triceps, Tuesday: Back & Biceps, Wednesday: Legs, Thursday: Shoulders, Friday: Cardio, Weekend: Rest or light activities"
    },
    {
      id: 5,
      title: "Travel Destinations",
      content: "Places to visit: Japan (Cherry blossoms), Iceland (Northern lights), New Zealand (Adventure sports), Italy (History & Food), Norway (Fjords)"
    }
  ])

  const handleEdit = (noteId) => {
    console.log('Edit note:', noteId)
    // Handle edit functionality here
    alert(`Edit functionality for note ${noteId} - Coming soon!`)
  }

  const handleDelete = (noteId) => {
    if (window.confirm('Are you sure you want to delete this note?')) {
      setNotes(notes.filter(note => note.id !== noteId))
    }
  }

  return (
    <div className="page-container">
      <div className="container">
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <h1 className="card-title" style={{ fontSize: '2.5rem', marginBottom: '0.5rem' }}>
            My Notes
          </h1>
          <p className="card-subtitle" style={{ fontSize: '1.1rem' }}>
            Organize your thoughts and ideas
          </p>
          <div className="nav-links" style={{ marginTop: '1rem' }}>
            <Link to="/" className="btn btn-outline">
              ← Back to Home
            </Link>
            <button className="btn btn-primary">
              + Add New Note
            </button>
          </div>
        </div>

        {notes.length === 0 ? (
          <div className="card" style={{ textAlign: 'center', padding: '3rem' }}>
            <h3 style={{ color: 'var(--text-secondary)', marginBottom: '1rem' }}>
              No notes yet
            </h3>
            <p className="card-subtitle">
              Create your first note to get started!
            </p>
          </div>
        ) : (
          <div className="notes-grid">
            {notes.map(note => (
              <div key={note.id} className="note-card">
                <h3 className="note-title">{note.title}</h3>
                <p className="note-content">{note.content}</p>
                <div className="note-actions">
                  <button 
                    className="btn btn-secondary btn-small"
                    onClick={() => handleEdit(note.id)}
                  >
                    ✏️ Edit
                  </button>
                  <button 
                    className="btn btn-danger btn-small"
                    onClick={() => handleDelete(note.id)}
                  >
                    🗑️ Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}

export default Notes