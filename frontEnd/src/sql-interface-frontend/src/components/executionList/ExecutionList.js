import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './ExecutionList.css'
import Execution from './../execution/Execution'
import {
    changePreviewState,
    getExecutionList,
    setExecutionListToEditMode,
    setExecutionListToViewMode,
    startEditingExecution,
    stopEditingExecution,
    updateExecutionComments
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
        startEditingExecution: id => { dispatch(startEditingExecution(id)) },
        stopEditingExecution: id => { dispatch(stopEditingExecution(id)) },
        updateExecutionComments: ( id, comments ) => { dispatch(updateExecutionComments(id, comments)) }

    }
}

const ExecutionListButtons = ({ mode, setEdit, setView }) => {
    if ( mode === "VIEW") {
        return (
            <div className="row">
                <div className="col-md-12">
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick = { setEdit }
                    >
                        Edit
                    </button>
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
    }
}

const ExecutionHistoryList = ({ executionList, listMode, changeState, startEdit, stopEdit, updateExecutionComments }) => (
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
                    date = { item.date }
                    comments = { item.comments }
                    query = { item.query }
                    changePreviewState = { () => changeState(item.id) }
                    startEdit = { () => startEdit(item.id) }
                    stopEdit = { () => stopEdit(item.id) }
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
    startEditingExecution,
    stopEditingExecution,
    updateExecutionComments
}) => {
    if (executionList.length ===0) {
        getExecutionList()
    }
    return (
        <div className="col-md-12">
            <ExecutionListButtons
                mode = { listMode }
                setEdit = { setListEditMode }
                setView = { setListViewMode }
            />
            <ExecutionHistoryList
                executionList = { executionList }
                listMode = { listMode }
                changeState = { changeState }
                startEdit = { startEditingExecution }
                stopEdit = { stopEditingExecution }
                updateExecutionComments = { updateExecutionComments }
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