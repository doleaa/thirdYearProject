import React from 'react'
import PropTypes from 'prop-types'
import './ExecutionButtons.css'

const ExecutionButtons = ({executeQuery}) => (
    <div className="col-md-12">
        <button
            type="button"
            class="btn btn-primary"
            onClick={executeQuery}
        >
            Execute
        </button>
    </div>
)

ExecutionButtons.propTypes = {
    executeQuery: PropTypes.func.isRequired
}

export default ExecutionButtons