import React, { useState, useEffect } from 'react'

const EditNoteModal = ({ isOpen, onClose, onSave, note }) => {
  const [formData, setFormData] = useState({
    title: '',
    content: ''
  })

  useEffect(() => {
    if (note) {
      setFormData({
        title: note.title,
        content: note.content
      })
    }
  }, [note])

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    if (formData.title.trim() && formData.content.trim()) {
      onSave({
        ...note,
        title: formData.title,
        content: formData.content
      })
      onClose()
    }
  }

  const handleClose = () => {
    if (note) {
      setFormData({
        title: note.title,
        content: note.content
      })
    }
    onClose()
  }

  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      handleClose()
    }
  }

  if (!isOpen || !note) return null

  return (
    <div className="modal-overlay" onClick={handleOverlayClick}>
      <div className="modal">
        <div className="modal-header">
          <h2 className="modal-title">Edit Note</h2>
          <button 
            className="modal-close" 
            onClick={handleClose}
            type="button"
          >
            ×
          </button>
        </div>
        
        <form onSubmit={handleSubmit}>
          <div className="modal-body">
            <div className="form-group">
              <label htmlFor="edit-title" className="form-label">Title</label>
              <input
                type="text"
                id="edit-title"
                name="title"
                className="form-input"
                value={formData.title}
                onChange={handleChange}
                placeholder="Enter note title"
                required
                autoFocus
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="edit-content" className="form-label">Content</label>
              <textarea
                id="edit-content"
                name="content"
                className="form-input form-textarea"
                value={formData.content}
                onChange={handleChange}
                placeholder="Write your note content here..."
                required
              />
            </div>
          </div>
          
          <div className="modal-footer">
            <button 
              type="button" 
              className="btn btn-cancel"
              onClick={handleClose}
            >
              Cancel
            </button>
            <button 
              type="submit" 
              className="btn btn-primary"
            >
              Update Note
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default EditNoteModal