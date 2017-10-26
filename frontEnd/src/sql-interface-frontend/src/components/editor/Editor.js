import React from 'react'
import PropTypes from 'prop-types'
import './Editor.css'

const Editor = ({query, updateQuery}) => (
    <div className="col-md-12">
        <textarea
            rows="10"
            placeholder="TYPE SQL IN HERE"
            defaultValue={query}
            onChange={updateQuery}
        />
    </div>
)

Editor.propTypes = {
    query: PropTypes.string.isRequired,
    updateQuery: PropTypes.func
}

export default Editor