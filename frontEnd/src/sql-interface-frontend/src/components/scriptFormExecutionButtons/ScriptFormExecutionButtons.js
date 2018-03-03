import React from 'react'
import PropTypes from 'prop-types'
import './ScriptFormExecutionButtons.css'

const ScriptFormExecutionButtons = ({execute, name, size}) => (
    <div className={`col-md-${size}`}>
        <button
            type="button"
            className="btn btn-primary"
            onClick={execute}
        >
            { name }
        </button>
    </div>
)

ScriptFormExecutionButtons.propTypes = {
    execute: PropTypes.func.isRequired,
    name: PropTypes.string.isRequired
}

export default ScriptFormExecutionButtons