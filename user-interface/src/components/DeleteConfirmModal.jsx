import React from 'react'

const DeleteConfirmModal = ({ isOpen, onClose, onConfirm, noteTitle }) => {
  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose()
    }
  }

  const handleConfirm = () => {
    onConfirm()
    onClose()
  }

  if (!isOpen) return null

  return (
    <div className="modal-overlay" onClick={handleOverlayClick}>
      <div className="modal" style={{ maxWidth: '400px' }}>
        <div className="modal-header">
          <h2 className="modal-title">Delete Note</h2>
          <button 
            className="modal-close" 
            onClick={onClose}
            type="button"
          >
            ×
          </button>
        </div>
        
        <div className="modal-body">
          <div style={{ textAlign: 'center', padding: '1rem 0' }}>
            <div style={{ 
              fontSize: '3rem', 
              color: 'var(--danger-color)', 
              marginBottom: '1rem' 
            }}>
              ⚠️
            </div>
            <p style={{ 
              fontSize: '1.1rem', 
              color: 'var(--text-primary)', 
              marginBottom: '0.5rem',
              fontWeight: '500'
            }}>
              Are you sure you want to delete this note?
            </p>
            {noteTitle && (
              <p style={{ 
                color: 'var(--text-secondary)', 
                fontStyle: 'italic',
                marginBottom: '1rem',
                padding: '0.5rem 1rem',
                backgroundColor: 'var(--bg-tertiary)',
                borderRadius: '6px',
                border: '1px solid var(--border-color)'
              }}>
                "{noteTitle}"
              </p>
            )}
            <p style={{ 
              color: 'var(--text-secondary)', 
              fontSize: '0.9rem' 
            }}>
              This action cannot be undone.
            </p>
          </div>
        </div>
        
        <div className="modal-footer">
          <button 
            type="button" 
            className="btn btn-cancel"
            onClick={onClose}
          >
            Cancel
          </button>
          <button 
            type="button" 
            className="btn btn-danger"
            onClick={handleConfirm}
            autoFocus
          >
            🗑️ Delete Note
          </button>
        </div>
      </div>
    </div>
  )
}

export default DeleteConfirmModal