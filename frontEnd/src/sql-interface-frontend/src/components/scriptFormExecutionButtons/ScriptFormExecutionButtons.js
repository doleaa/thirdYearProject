import React from 'react'
import PropTypes from 'prop-types'
import './ScriptFormExecutionButtons.css'

const ScriptFormExecutionButtons = ({execute, name}) => (
    <div className="col-md-12">
        <button
            type="button"
            className="btn btn-primary pull-left"
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