import React from 'react'
import PropTypes from 'prop-types'
import './Execution.css'

const Execution = ({ isInPreview, date, comments, query, changePreviewState }) => {

    if ( isInPreview ) {
        return (
            <div
            className="row execution"
            onClick = {() => changePreviewState()}
            >
                <div className="col-md-4 execution-date">
                    { date }
                </div>
                <div className="col-md-8 comments-preview">
                    { comments }
                </div>
            </div>
        )
    } else {
        return (
            <div
            className="row execution"
            onClick = {() => changePreviewState()}
            >
                <div className="col-md-12 execution-date">
                    { date }
                </div>
                <div className="col-md-12 comments">
                    { comments }
                </div>
                <div className="col-md-12 query">
                    { query }
                </div>
            </div>
        )
    }
}

Execution.propTypes = {
    isInPreview: PropTypes.bool,
    date: PropTypes.string,
    query: PropTypes.string,
    changePreviewState: PropTypes.func
}

export default Execution