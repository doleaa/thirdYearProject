import React from 'react'
import Execution from './../execution/Execution'
import './ExecutionHistoryList.css'

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

export default ExecutionHistoryList