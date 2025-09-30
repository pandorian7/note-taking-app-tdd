import React, { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import AddNoteModal from '../components/AddNoteModal'
import EditNoteModal from '../components/EditNoteModal'
import DeleteConfirmModal from '../components/DeleteConfirmModal'
import api from '../services/api'

const Notes = () => {
  const navigate = useNavigate()
  const [notes, setNotes] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [operationLoading, setOperationLoading] = useState(false)

  const [isAddModalOpen, setIsAddModalOpen] = useState(false)
  const [isEditModalOpen, setIsEditModalOpen] = useState(false)
  const [editingNote, setEditingNote] = useState(null)
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false)
  const [deletingNote, setDeletingNote] = useState(null)

  // Check authentication and fetch notes on component mount
  useEffect(() => {
    const fetchNotes = async () => {
      // Check if user is authenticated
      if (!api.tokenManager.isAuthenticated()) {
        navigate('/signin')
        return
      }

      try {
        setLoading(true)
        setError('')
        const fetchedNotes = await api.notes.getAllNotes()
        setNotes(fetchedNotes)
      } catch (error) {
        console.error('Failed to fetch notes:', error)
        if (error.code === 'UNAUTHORIZED' || error.status === 401) {
          // Token might be expired, redirect to signin
          navigate('/signin')
        } else {
          setError('Failed to load notes. Please try again.')
        }
      } finally {
        setLoading(false)
      }
    }

    fetchNotes()
  }, [navigate])

  const handleAddNote = async (noteData) => {
    try {
      setOperationLoading(true)
      const newNote = await api.notes.createNote({
        title: noteData.title,
        content: noteData.content
      })
      setNotes([newNote, ...notes])
    } catch (error) {
      console.error('Failed to create note:', error)
      if (error.status === 401) {
        navigate('/signin')
      } else {
        alert('Failed to create note. Please try again.')
      }
    } finally {
      setOperationLoading(false)
    }
  }

  const handleEditNote = (noteId) => {
    const note = notes.find(n => n.id === noteId)
    if (note) {
      setEditingNote(note)
      setIsEditModalOpen(true)
    }
  }

  const handleUpdateNote = async (updatedNote) => {
    try {
      setOperationLoading(true)
      const updated = await api.notes.updateNote(updatedNote.id, {
        title: updatedNote.title,
        content: updatedNote.content
      })
      setNotes(notes.map(note => 
        note.id === updatedNote.id ? updated : note
      ))
      setEditingNote(null)
    } catch (error) {
      console.error('Failed to update note:', error)
      if (error.status === 401) {
        navigate('/signin')
      } else {
        alert('Failed to update note. Please try again.')
      }
    } finally {
      setOperationLoading(false)
    }
  }

  const handleDeleteClick = (noteId) => {
    const note = notes.find(n => n.id === noteId)
    if (note) {
      setDeletingNote(note)
      setIsDeleteModalOpen(true)
    }
  }

  const handleDeleteConfirm = async () => {
    if (deletingNote) {
      try {
        setOperationLoading(true)
        await api.notes.deleteNote(deletingNote.id)
        setNotes(notes.filter(note => note.id !== deletingNote.id))
        setDeletingNote(null)
      } catch (error) {
        console.error('Failed to delete note:', error)
        if (error.status === 401) {
          navigate('/signin')
        } else {
          alert('Failed to delete note. Please try again.')
        }
      } finally {
        setOperationLoading(false)
      }
    }
  }

  const handleSignOut = () => {
    api.auth.signOut()
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
            <button 
              className="btn btn-primary"
              onClick={() => setIsAddModalOpen(true)}
              disabled={operationLoading}
            >
              + Add New Note
            </button>
            <button 
              className="btn btn-danger"
              onClick={handleSignOut}
            >
              Sign Out
            </button>
          </div>
        </div>

        {error && (
          <div style={{
            backgroundColor: 'var(--danger-light)',
            color: 'var(--danger-color)',
            padding: '1rem',
            borderRadius: '8px',
            marginBottom: '2rem',
            textAlign: 'center',
            border: '1px solid var(--danger-color)'
          }}>
            {error}
          </div>
        )}

        {loading ? (
          <div className="card" style={{ textAlign: 'center', padding: '3rem' }}>
            <div style={{ fontSize: '2rem', marginBottom: '1rem' }}>📝</div>
            <h3 style={{ color: 'var(--text-secondary)', marginBottom: '1rem' }}>
              Loading your notes...
            </h3>
          </div>
        ) : notes.length === 0 ? (
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
                    onClick={() => handleEditNote(note.id)}
                    disabled={operationLoading}
                  >
                    ✏️ Edit
                  </button>
                  <button 
                    className="btn btn-danger btn-small"
                    onClick={() => handleDeleteClick(note.id)}
                    disabled={operationLoading}
                  >
                    🗑️ Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}

        {/* Add Note Modal */}
        <AddNoteModal
          isOpen={isAddModalOpen}
          onClose={() => setIsAddModalOpen(false)}
          onSave={handleAddNote}
        />

        {/* Edit Note Modal */}
        <EditNoteModal
          isOpen={isEditModalOpen}
          onClose={() => {
            setIsEditModalOpen(false)
            setEditingNote(null)
          }}
          onSave={handleUpdateNote}
          note={editingNote}
        />

        {/* Delete Confirmation Modal */}
        <DeleteConfirmModal
          isOpen={isDeleteModalOpen}
          onClose={() => {
            setIsDeleteModalOpen(false)
            setDeletingNote(null)
          }}
          onConfirm={handleDeleteConfirm}
          noteTitle={deletingNote?.title}
        />
      </div>
    </div>
  )
}

export default Notes