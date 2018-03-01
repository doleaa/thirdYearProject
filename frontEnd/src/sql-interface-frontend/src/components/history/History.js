import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './History.css'
import ScriptForm from './../scriptForm/ScriptForm'
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
    selectExecution,
    unSelectExecution,
    unSelectAllExecutions,
    updateExecutionComments,
    setExecutionListToScriptForm,
    setScriptFormData,
    deleteScriptFormData,
    updateScriptFormTitle,
    updateScriptFormHeader
} from './../../actions'

const mapStateToProps = state => {
    return {
        executionList: state.history.executionsList,
        historyMode: state.history.mode,
        scriptForm: state.history.scriptForm
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
        selectExecution: id => { dispatch(selectExecution(id)) },
        unSelectExecution: id => { dispatch(unSelectExecution(id)) },
        unSelectAllExecutions: id => { dispatch(unSelectAllExecutions()) },
        updateExecutionComments: ( id, comments ) => { dispatch(updateExecutionComments(id, comments)) },
        setExecutionListToScriptForm: () => { dispatch(setExecutionListToScriptForm()) },
        setScriptFormData: (title, header, elementList) => { dispatch(setScriptFormData(title, header, elementList)) },
        deleteScriptFormData: () => { dispatch(deleteScriptFormData()) },
        updateScriptFormTitle: title => { dispatch(updateScriptFormTitle(title)) },
        updateScriptFormHeader: header => { dispatch(updateScriptFormHeader(header)) }

    }
}

const DisconnectedHistory = ({
    executionList,
    historyMode,
    scriptForm,
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
    setExecutionListToScriptForm,
    setScriptFormData,
    deleteScriptFormData,
    updateScriptFormTitle,
    updateScriptFormHeader
}) => {
    const setScriptFormBtn = () => {
        setScriptFormData("", "", executionList.filter(item => item.selected))
        setExecutionListToScriptForm()
        unSelectAllExecutions()
    }
    if (executionList.length ===0) {
        getExecutionList()
    }
    if ( historyMode === "REPORT_FORM" ) {
        return (
            <div className="col-md-12">
                <ExecutionListButtons
                        mode = { historyMode }
                        setEdit = { setListEditMode }
                        setView = { setListViewMode }
                        setScriptForm = { setScriptFormBtn }
                        setSelect = { setListSelectMode }
                        deleteScriptFormData = { deleteScriptFormData }
                    />
                <ScriptForm
                    title={scriptForm.title}
                    updateTitle={updateScriptFormTitle}
                    header={scriptForm.header}
                    updateHeader={updateScriptFormHeader}
                    elementList={scriptForm.elementList}
                />
            </div>
        )
    }
    return (
        <div className="col-md-12">
            <ExecutionListButtons
                mode = { historyMode }
                setEdit = { setListEditMode }
                setView = { setListViewMode }
                setScriptForm = { setScriptFormBtn }
                setSelect = { setListSelectMode }
                deleteScriptFormData = { deleteScriptFormData }
            />
            <ExecutionHistoryList
                executionList = { executionList }
                listMode = { historyMode }
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