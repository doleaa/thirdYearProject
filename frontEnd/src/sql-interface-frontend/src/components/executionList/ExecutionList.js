import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './ExecutionList.css'
import Execution from './../execution/Execution'
import ReportForm from './../reportForm/ReportForm'
import {
    changePreviewState,
    getExecutionList,
    setExecutionListToEditMode,
    setExecutionListToViewMode,
    setExecutionListToSelectMode,
    startEditingExecution,
    saveExecutionComments,
    addReportNoteToExecution,
    deleteReportNoteFromExecution,
    selectExecution,
    unSelectExecution,
    unSelectAllExecutions,
    updateExecutionComments,
    updateExecutionReportNote,
    setExecutionListToReportForm
} from './../../actions'

const mapStateToProps = state => {
    return {
        executionList: state.history.executionsList,
        listMode: state.history.mode
    }
}

const mapDispatchToProps = dispatch => {
    return {
        changeState: id => { dispatch(changePreviewState(id)) },
        getExecutionList: () => { dispatch(getExecutionList()) },
        setListEditMode: () => { dispatch(setExecutionListToEditMode()) },
        setListViewMode: () => { dispatch(setExecutionListToViewMode()) },
        setListSelectMode: () => { dispatch(setExecutionListToSelectMode()) },
        startEditingExecution: id => { dispatch(startEditingExecution(id)) },
        stopEditingExecution: ( id, comments ) => { dispatch(saveExecutionComments(id, comments)) },
        addReportNoteToExecution: id => { dispatch(addReportNoteToExecution(id)) },
        deleteReportNoteFromExecution: id => { dispatch(deleteReportNoteFromExecution(id)) },
        selectExecution: id => { dispatch(selectExecution(id)) },
        unSelectExecution: id => { dispatch(unSelectExecution(id)) },
        unSelectAllExecutions: id => { dispatch(unSelectAllExecutions()) },
        updateExecutionComments: ( id, comments ) => { dispatch(updateExecutionComments(id, comments)) },
        updateExecutionReportNote: ( id, comments ) => { dispatch(updateExecutionReportNote(id, comments)) },
        setExecutionListToReportForm: ( id, comments ) => { dispatch(setExecutionListToReportForm(id, comments)) }

    }
}

const ExecutionListButtons = ({ mode, setEdit, setView, setReportForm, setSelect, unselectAll }) => {
    if ( mode === "VIEW") {
        return (
            <div className="row">
                <div className="col-md-12">
                    <div className="pull-left">
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick = { () => {} }
                        >
                            Reports
                        </button>
                    </div>
                    <div className="pull-right">
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick = { setEdit }
                        >
                            Comment/Edit
                        </button>
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick={ setSelect }
                        >
                            Select
                        </button>
                    </div>
                </div>
            </div>
        )
    } else if ( mode === "EDIT" ) {
        return (
            <div className="row">
                <div className="col-md-12">
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ setView }
                    >
                        Done
                    </button>
                </div>
            </div>
        )
    } else if ( mode === "SELECT" ) {
        return (
            <div className="row">
                <div className="col-md-12">
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ () => {
                            setView()
                            unselectAll()
                        } }
                    >
                        Cancel
                    </button>
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ setReportForm }
                    >
                        Create Report
                    </button>
                </div>
            </div>
        )
    } else if ( mode === "REPORT_FORM" ) {
        return (
            <div className="row buttgr">
                <div className="col-md-12">
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ () => {
                            setView()
                            unselectAll()
                        } }
                    >
                        Save
                    </button>
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ () => {
                            setView()
                            unselectAll()
                        } }
                    >
                        Cancel
                    </button>
                </div>
            </div>
        )
    }
}

const ExecutionHistoryList = ({
    executionList,
    listMode,
    changeState,
    startEdit,
    stopEdit,
    updateExecutionComments,
    select,
    unselect
}) => (
    <div className="row">
        <div className="col-md-12">
            {executionList.map(item => (
                <Execution
                    key = { item.id }
                    id = { item.id }
                    isInPreview = { item.isInPreview }
                    isLoading = { item.isLoading }
                    mode = { listMode }
                    editing = { item.editing }
                    selected = { item.selected }
                    select = { () => select(item.id) }
                    unselect = { () => unselect(item.id) }
                    date = { item.ranAt }
                    comments = { item.comment ? item.comment.text : item.comment }
                    resultTableData = {
                        item.result ? JSON.parse(item.result.resultString) : {columns: [], rows: []}
                    }
                    query = { item.statement.sql }
                    changePreviewState = { () => changeState(item.id) }
                    startEdit = { () => startEdit(item.id) }
                    stopEdit = { stopEdit }
                    updateComments = { updateExecutionComments }
                />
            ))}
        </div>
    </div>
)

const DisconnectedExecutionList = ({
    executionList,
    listMode,
    changeState,
    getExecutionList,
    setListEditMode,
    setListViewMode,
    setListSelectMode,
    startEditingExecution,
    stopEditingExecution,
    addReportNoteToExecution,
    deleteReportNoteFromExecution,
    selectExecution,
    unSelectExecution,
    unSelectAllExecutions,
    updateExecutionComments,
    updateExecutionReportNote,
    setExecutionListToReportForm
}) => {
    if (executionList.length ===0) {
        getExecutionList()
    }
    if ( listMode === "REPORT_FORM" ) {
        return (
            <div className="col-md-12">
                <ExecutionListButtons
                        mode = { listMode }
                        setEdit = { setListEditMode }
                        setView = { setListViewMode }
                        setReportForm = { setExecutionListToReportForm }
                        setSelect = { setListSelectMode }
                        unselectAll = { unSelectAllExecutions }
                    />
                <ReportForm
                    executions={executionList}
                    addReportNote={addReportNoteToExecution}
                    deleteReportNote={deleteReportNoteFromExecution}
                    updateReportNote={updateExecutionReportNote}
                />
            </div>
        )
    }
    return (
        <div className="col-md-12">
            <ExecutionListButtons
                mode = { listMode }
                setEdit = { setListEditMode }
                setView = { setListViewMode }
                setReportForm = { setExecutionListToReportForm }
                setSelect = { setListSelectMode }
                unselectAll = { unSelectAllExecutions }
            />
            <ExecutionHistoryList
                executionList = { executionList }
                listMode = { listMode }
                changeState = { changeState }
                startEdit = { startEditingExecution }
                stopEdit = { stopEditingExecution }
                updateExecutionComments = { updateExecutionComments }
                select = { selectExecution }
                unselect = { unSelectExecution }
            />
        </div>
    )
}

DisconnectedExecutionList.propTypes = {
    executionList: PropTypes.array,
    changePreviewState: PropTypes.func
}

const ExecutionList = connect(mapStateToProps, mapDispatchToProps)(DisconnectedExecutionList)

export default ExecutionList