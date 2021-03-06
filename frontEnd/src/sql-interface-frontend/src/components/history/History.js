import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './History.css'
import ScriptForm from './../scriptForm/ScriptForm'
import ExecutionHistoryList from './../executionHistoryList/ExecutionHistoryList'
import ScriptsList from './../scriptsList/ScriptsList'
import ExecutionListButtons from './../executionListButtons/ExecutionListButtons'
import FileSaver from 'file-saver'
import {
    changePreviewState,
    getExecutionList,
    getScriptsList,
    setExecutionListToEditMode,
    setExecutionListToViewMode,
    setExecutionListToScriptList,
    setExecutionListToSelectMode,
    startEditingExecution,
    saveExecutionComments,
    selectExecution,
    unSelectExecution,
    unSelectAllExecutions,
    updateExecutionComments,
    setExecutionListToScriptForm,
    updateScriptFormCommentElement,
    startEditingScriptFormCommentElement,
    stopEditingScriptFormCommentElement,
    setScriptFormData,
    deleteScriptFormData,
    updateScriptFormTitle,
    updateScriptFormHeader,
    moveScriptFormElementFromTo,
    startMovingScriptFormElementFrom,
    addScriptFormCommentElementUnder,
    removeScriptFormElement,
    saveScript,
    updateScript,
    startUpdatingScript,
    stopUpdatingScript,
    startLoadingScript,
    runScript,
    createSampleForScript,
    runScriptAgainstSample
} from './../../actions'

const mapStateToProps = state => {
    return {
        executionList: state.history.executionsList,
        historyMode: state.history.mode,
        scriptForm: state.history.scriptForm,
        scriptsList: state.history.scriptsList
    }
}

const mapDispatchToProps = dispatch => {
    return {
        changeState: id => { dispatch(changePreviewState(id)) },
        getExecutionList: () => { dispatch(getExecutionList()) },
        getScriptsList: () => { dispatch(getScriptsList()) },
        setListEditMode: () => { dispatch(setExecutionListToEditMode()) },
        setListViewMode: () => { dispatch(setExecutionListToViewMode()) },
        setScriptListViewMode: () => { dispatch(setExecutionListToScriptList()) },
        setListSelectMode: () => { dispatch(setExecutionListToSelectMode()) },
        startEditingExecution: id => { dispatch(startEditingExecution(id)) },
        stopEditingExecution: ( id, comments ) => { dispatch(saveExecutionComments(id, comments)) },
        selectExecution: id => { dispatch(selectExecution(id)) },
        unSelectExecution: id => { dispatch(unSelectExecution(id)) },
        unSelectAllExecutions: id => { dispatch(unSelectAllExecutions()) },
        updateExecutionComments: ( id, comments ) => { dispatch(updateExecutionComments(id, comments)) },
        setExecutionListToScriptForm: () => { dispatch(setExecutionListToScriptForm()) },
        updateScriptFormCommentElement: (index, comment) => { dispatch(updateScriptFormCommentElement(index, comment)) },
        startEditingScriptFormCommentElement: index => { dispatch(startEditingScriptFormCommentElement(index)) },
        stopEditingScriptFormCommentElement: index => { dispatch(stopEditingScriptFormCommentElement(index)) },
        setScriptFormData: (title, header, elementList) => { dispatch(setScriptFormData(title, header, elementList)) },
        deleteScriptFormData: () => { dispatch(deleteScriptFormData()) },
        updateScriptFormTitle: title => { dispatch(updateScriptFormTitle(title)) },
        updateScriptFormHeader: header => { dispatch(updateScriptFormHeader(header)) },
        addScriptFormCommentElementUnder: index => { dispatch(addScriptFormCommentElementUnder(index)) },
        moveScriptFormElementFromTo: (from, to) => { dispatch(moveScriptFormElementFromTo(from, to)) },
        startMovingScriptFormElementFrom: index => { dispatch(startMovingScriptFormElementFrom(index)) },
        removeScriptFormElement: index => { dispatch(removeScriptFormElement(index)) },
        saveScriptFormData: scriptForm => { dispatch(saveScript(scriptForm)) },
        updateScriptFormData: scriptForm => { dispatch(updateScript(scriptForm)) },
        startUpdatingScript: id => { dispatch(startUpdatingScript(id)) },
        runScript: id => { dispatch(runScript(id)) },
        createSampleForScript: id => { dispatch(createSampleForScript(id)) },
        runScriptAgainstSample: id => { dispatch(runScriptAgainstSample(id)) },
        stopUpdatingScript: id => { dispatch(stopUpdatingScript(id)) },
        startLoadingScript: id => { dispatch(startLoadingScript(id)) }
    }
}

let triedOnce = false;

const DisconnectedHistory = ({
    executionList,
    historyMode,
    scriptForm,
    scriptsList,
    changeState,
    getExecutionList,
    getScriptsList,
    setListEditMode,
    setListViewMode,
    setScriptListViewMode,
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
    updateScriptFormCommentElement,
    startEditingScriptFormCommentElement,
    stopEditingScriptFormCommentElement,
    setScriptFormData,
    deleteScriptFormData,
    updateScriptFormTitle,
    updateScriptFormHeader,
    addScriptFormCommentElementUnder,
    moveScriptFormElementFromTo,
    startMovingScriptFormElementFrom,
    removeScriptFormElement,
    saveScriptFormData,
    updateScriptFormData,
    startUpdatingScript,
    runScript,
    createSampleForScript,
    runScriptAgainstSample,
    stopUpdatingScript,
    startLoadingScript
}) => {
    const exportScript = scriptForm => {
        const blob = new Blob(
            [scriptForm.elementList.map(element => {
                if (element.statement) {
                    return element.statement.sql
                } else {
                    return "/* " + element.comment.text + " */"
                }
            }).join('\n')],
            {type: "text/plain;charset=utf-8"}
        )
        FileSaver.saveAs(blob, scriptForm.title+".sql")
    }

    const setScriptFormBtn = () => {
        setScriptFormData("", "",
            executionList
                .filter(item => item.selected)
                .map(item => {
                    return {execution: item}
                })
                .reverse()
        )
        setExecutionListToScriptForm()
        unSelectAllExecutions()
    }
    const updateScriptData = (id, scriptForm) => {
        scriptForm.id = id
        startLoadingScript(id)
        updateScriptFormData(scriptForm)
        getScriptsList()
    }

    const createScriptSample = id => {
        startLoadingScript(id)
        createSampleForScript(id)
        getScriptsList()
    }
    const setScriptFormBtnFromExistingScript = script => {
        setScriptFormData(script.title, script.header,
            script.elements
                .sort((a, b) => { return a.position - b.position })
                .map(item => {
                    if (item.statement) {
                        item.execution = {statement: item.statement}
                    }
                    return item
                })
        )
        startUpdatingScript(script.id)
    }
    if (!triedOnce) {
        getExecutionList()
        triedOnce = true
    }
    if ( historyMode === "SCRIPT_FORM" ) {
        return (
            <div className="col-md-12">
                <ExecutionListButtons
                    mode = { historyMode }
                    setEdit = { setListEditMode }
                    setView = { setListViewMode }
                    setScriptList = {() => {
                        getScriptsList()
                        setScriptListViewMode()
                    }}
                    setScriptForm = { setScriptFormBtn }
                    setSelect = { setListSelectMode }
                    deleteScriptFormData = { deleteScriptFormData }
                    saveScriptFormData = {() => { saveScriptFormData(scriptForm) }}
                />
                <ScriptForm
                    title={scriptForm.title}
                    updateTitle={updateScriptFormTitle}
                    header={scriptForm.header}
                    updateHeader={updateScriptFormHeader}
                    elementList={scriptForm.elementList}
                    updateCommentText={updateScriptFormCommentElement}
                    movingIndex={scriptForm.movingIndex}
                    moveFromTo={moveScriptFormElementFromTo}
                    moveFrom={startMovingScriptFormElementFrom}
                    addCommentUnder={addScriptFormCommentElementUnder}
                    stopEdit={stopEditingScriptFormCommentElement}
                    startEdit={startEditingScriptFormCommentElement}
                    removeScriptFormElement={removeScriptFormElement}
                />
            </div>
        )
    } else if ( historyMode === "SCRIPT_LIST" ) {
        return (
            <div className="col-md-12">
                <ExecutionListButtons
                    mode = { historyMode }
                    setEdit = { setListEditMode }
                    setView = { setListViewMode }
                    setScriptList = {() => {
                        getScriptsList()
                        setScriptListViewMode()
                    }}
                    setScriptForm = { setScriptFormBtn }
                    setSelect = { setListSelectMode }
                    deleteScriptFormData = { deleteScriptFormData }
                    saveScriptFormData = {() => { saveScriptFormData(scriptForm) }}
                />
                <ScriptsList
                    list = { scriptsList }
                    startUpdate = { setScriptFormBtnFromExistingScript }
                    cancelUpdate = { stopUpdatingScript }
                    title={scriptForm.title}
                    updateTitle={updateScriptFormTitle}
                    header={scriptForm.header}
                    updateHeader={updateScriptFormHeader}
                    elementList={scriptForm.elementList}
                    updateCommentText={updateScriptFormCommentElement}
                    movingIndex={scriptForm.movingIndex}
                    moveFromTo={moveScriptFormElementFromTo}
                    moveFrom={startMovingScriptFormElementFrom}
                    addCommentUnder={addScriptFormCommentElementUnder}
                    stopEdit={stopEditingScriptFormCommentElement}
                    startEdit={startEditingScriptFormCommentElement}
                    removeScriptFormElement={removeScriptFormElement}
                    deleteScriptFormData={deleteScriptFormData}
                    updateScriptFormData={(id) => {updateScriptData(id, scriptForm)}}
                    exportScript={() => exportScript(scriptForm)}
                    runScript={(id) => {
                        startLoadingScript(id)
                        runScript(id)
                    }}
                    createSampleForScript={(id) => {
                        createScriptSample(id)
                    }}
                    runScriptAgainstSample={(id) => {
                        startLoadingScript(id)
                        runScriptAgainstSample(id)
                    }}
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
                setScriptList = {() => {
                    getScriptsList()
                    setScriptListViewMode()
                }}
                setScriptForm = { setScriptFormBtn }
                setSelect = { setListSelectMode }
                deleteScriptFormData = { deleteScriptFormData }
                saveScriptFormData = {() => { saveScriptFormData(scriptForm) }}
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