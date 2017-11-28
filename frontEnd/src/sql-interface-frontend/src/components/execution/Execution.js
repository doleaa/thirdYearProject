import React from 'react'
import spinner from './../../spinner.svg'
import PropTypes from 'prop-types'
import './Execution.css'

const Execution = ({ isInPreview, isLoading, mode, date, comments, query, changePreviewState }) => {

    if ( isLoading ) {
        return (
            <div className="row  executionSpinner">
                <img
                    src={spinner}
                    className="col-md-12 spinner"
                    alt="logo"
                />
            </div>
        )
    } else if ( mode ==="EDIT" ) {
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
    } else if ( isInPreview && mode ==="VIEW") {
        return (
            <div
            className="row execution"
            onClick = {() => changePreviewState()}
            >
                <div className="col-md-4 execution-date">
                    { date }
                </div>
                <div className="col-md-8 comments-preview">
                    { query }
                </div>
            </div>
        )
    } else if (mode ==="VIEW") {
        return (
            <div
            className="row execution"
            onClick = {() => changePreviewState()}
            >
                <div className="col-md-12 execution-date">
                    { date }
                </div>
                <div className="col-md-12 query">
                    { query }
                </div>
                <div className="col-md-12 comments">
                    { comments }
                </div>
            </div>
        )
    }
}

Execution.propTypes = {
    isInPreview: PropTypes.bool,
    isLoading: PropTypes.bool,
    mode: PropTypes.string,
    date: PropTypes.string,
    query: PropTypes.string,
    changePreviewState: PropTypes.func
}

export default Execution