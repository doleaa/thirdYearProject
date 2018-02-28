import React from 'react'
import spinner from './../../spinner.svg'
import PropTypes from 'prop-types'
import './Execution.css'
import {
    EditingExecution,
    EditableExecution,
    PreviewExecution,
    NormalViewExecution,
    SelectableExecution,
    SelectedExecution
} from './ExecutionEnum'


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
    resultTableData,
    query,
    changePreviewState,
    startEdit,
    stopEdit,
    updateComments
}) => {

    const updateExecComments = event => {
        updateComments(id, event.target.value)
    }
    const stopEditing = () => {
        stopEdit(id, comments)
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
            <EditingExecution
                query={query}
                date={date}
                comments={comments}
                updateExecComments={updateExecComments}
                stopEdit={stopEditing}
            />
        )
    } else if ( mode ==="EDIT" ) {
        return (
            <EditableExecution
                query={query}
                date={date}
                comments={comments}
                updateExecComments={updateExecComments}
                startEdit={startEdit}
            />
        )
    } else if ( mode ==="REPORT_FORM" ) {
        return (
            <div
            className="row reportFormMode"
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
    } else if ( mode ==="SELECT" && selected ) {
        return (
            <SelectedExecution
                date={date}
                query={query}
                comments={comments}
                unselect={unselect}
            />
        )
    } else if ( mode ==="SELECT" ) {
        return (
            <SelectableExecution
                date={date}
                query={query}
                comments={comments}
                select={select}
            />
        )
    } else if ( isInPreview && mode ==="VIEW") {
        return (
            <PreviewExecution
                date={date}
                query={query}
                changePreviewState={changePreviewState}
            />
        )
    } else if (mode ==="VIEW") {
        return (
            <NormalViewExecution
                date={date}
                query={query}
                comments={comments}
                changePreviewState={changePreviewState}
                resultTableData={resultTableData}
            />
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
    date: PropTypes.object,
    query: PropTypes.string,
    changePreviewState: PropTypes.func,
    startEdit: PropTypes.func,
    stopEdit: PropTypes.func
}

export default Execution