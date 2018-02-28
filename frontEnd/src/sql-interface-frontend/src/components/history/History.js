import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './History.css'
import Execution from './../execution/Execution'
import ReportForm from './../reportForm/ReportForm'
import ExecutionHistoryList from './../executionHistoryList/ExecutionHistoryList'
import ExecutionListButtons from './../executionListButtons/ExecutionListButtons'
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

const DisconnectedHistory = ({
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

DisconnectedHistory.propTypes = {
    executionList: PropTypes.array,
    changePreviewState: PropTypes.func
}

const History = connect(mapStateToProps, mapDispatchToProps)(DisconnectedHistory)

export default History