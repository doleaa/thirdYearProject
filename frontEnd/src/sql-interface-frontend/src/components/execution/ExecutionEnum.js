import React from 'react'
import Editor from './../editor/Editor'
import ResultTable from './../resultTable/ResultTable'
import ExecutionButtons from './../executionButtons/ExecutionButtons'
import './../execution/Execution.css'

export const ScriptFormExecution = ({ query, comments, resultTableData, isMoving, isMovable, moveFrom, moveTo }) => {
    return (
        <div className={
            isMoving ?
                "row scriptFormExecution movingScriptFormExecution" :
                "row scriptFormExecution mousable"
            }
            onClick = { isMovable ? moveFrom : moveTo}
        >
            <div className="col-md-12 script-form-query">
                { query }
            </div>
            <div className="col-md-12 comments">
                { comments }
            </div>
            {resultTableData && resultTableData.columns && resultTableData.columns.length > 0 &&
                <div>
                    <div className="col-md-12 above-result-table"/>
                    <ResultTable
                        columns={resultTableData.columns}
                        rows={resultTableData.rows}
                    />
                </div>
            }
        </div>
    )
}

export const ExecutionLikeComment = ({ comments, isMoving, isMovable, moveFrom, moveTo }) => {
    return (
        <div className={
            isMoving ?
               "row scriptFormExecution movingScriptFormExecution" :
               "row scriptFormExecution mousable"
            }
            onClick = { isMovable ? moveFrom : moveTo}
        >
            <div className="col-md-12 script-form-query">
                { comments }
            </div>
        </div>
    )
}

export const EditingExecution = ({date, query, comments, updateExecComments, stopEdit}) => {
    return (
        <div
        className="row execution mousable"
        >
            <div className="col-md-12 execution-date">
                <div
                    className="pull-right stopExecutionEditButton"
                    onClick={stopEdit}
                >
                    Save
                </div>
                { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
            </div>
            <div className="col-md-12 query">
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
}

export const EditableExecution = ({date, query, comments, startEdit}) => {
    return (
        <div
        className="row execution mousable"
        onClick = {() => startEdit()}
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

export const PreviewExecution = ({date, query, changePreviewState}) => {
    return (
        <div
        className="row execution mousable"
        onClick = {() => changePreviewState()}
        >
            <div className="col-md-4 execution-date">
                { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
            </div>
            <div className="col-md-8 query-preview">
                { query }
            </div>
        </div>
    )
}

export const NormalViewExecution = ({date, query, comments, changePreviewState, resultTableData}) => {
    return (
        <div
        className="row execution mousable"
        onClick = {() => changePreviewState()}
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
            {resultTableData && resultTableData.columns && resultTableData.columns.length > 0 &&
                <div>
                    <div className="col-md-12 above-result-table"/>
                    <ResultTable
                        columns={resultTableData.columns}
                        rows={resultTableData.rows}
                    />
                </div>
            }
        </div>
    )
}

export const SelectableExecution = ({date, query, comments, select}) => {
    return (
        <div
        className="row execution"
        onClick = {() => select()}
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

export const SelectedExecution = ({date, query, comments, unselect}) => {
    return (
        <div
        className="row selected"
        onClick = {() => unselect()}
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