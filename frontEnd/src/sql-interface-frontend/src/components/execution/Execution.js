import React from 'react'
import spinner from './../../spinner.svg'
import PropTypes from 'prop-types'
import './Execution.css'
import Editor from './../editor/Editor'

const Execution = ({
    id,
    isInPreview,
    isLoading,
    mode,
    editing,
    selected,
    select,
    unselect,
    date,
    comments,
    query,
    changePreviewState,
    startEdit,
    stopEdit,
    updateComments
}) => {

    const updateExecComments = event => {
        updateComments(id, event.target.value)
    }

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
    } else if ( mode ==="EDIT" && editing ) {
        return (
            <div
            className="row execution mousable"
            onDoubleClick = {() => stopEdit()}
            >
                <div className="col-md-12 execution-date">
                    { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
                </div>
                <div className="col-md-12 comments">
                    { query }
                </div>
                <Editor
                    rows={5}
                    initialValue={comments}
                    updateValue={updateExecComments}
                    placeholder="TYPE YOUR COMMENTS HERE"
                />
            </div>
        )
    } else if ( mode ==="EDIT" ) {
        return (
            <div
            className="row execution mousable"
            onDoubleClick = {() => startEdit()}
            >
                <div className="col-md-12 execution-date">
                    { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
                </div>
                <div className="col-md-12 comments">
                    { query }
                </div>
                <div className="col-md-12 query">
                    { comments }
                </div>
            </div>
        )
    } else if ( mode ==="REPORT_FORM" ) {
        return (
            <div
            className="row reportFormMode"
            >
                <div className="col-md-12 execution-date">
                    { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
                </div>
                <div className="col-md-12 comments">
                    { query }
                </div>
                <div className="col-md-12 query">
                    { comments }
                </div>
            </div>
        )
    } else if ( mode ==="SELECT" && selected ) {
        return (
            <div
            className="row selected"
            onClick = {() => unselect()}
            >
                <div className="col-md-12 execution-date">
                    { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
                </div>
                <div className="col-md-12 comments">
                    { query }
                </div>
                <div className="col-md-12 query">
                    { comments }
                </div>
            </div>
        )
    } else if ( mode ==="SELECT" ) {
        return (
            <div
            className="row execution"
            onClick = {() => select()}
            >
                <div className="col-md-12 execution-date">
                    { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
                </div>
                <div className="col-md-12 comments">
                    { query }
                </div>
                <div className="col-md-12 query">
                    { comments }
                </div>
            </div>
        )
    } else if ( isInPreview && mode ==="VIEW") {
        return (
            <div
            className="row execution mousable"
            onDoubleClick = {() => changePreviewState()}
            >
                <div className="col-md-4 execution-date">
                    { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
                </div>
                <div className="col-md-8 comments-preview">
                    { query }
                </div>
            </div>
        )
    } else if (mode ==="VIEW") {
        return (
            <div
            className="row execution mousable"
            onDoubleClick = {() => changePreviewState()}
            >
                <div className="col-md-12 execution-date">
                    { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
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
    editing: PropTypes.bool,
    selected: PropTypes.bool,
    select: PropTypes.func,
    unselect: PropTypes.func,
    date: PropTypes.string,
    query: PropTypes.string,
    changePreviewState: PropTypes.func,
    startEdit: PropTypes.func,
    stopEdit: PropTypes.func
}

export default Execution