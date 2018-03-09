import React from 'react'
import ScriptForm from './../scriptForm/ScriptForm'
import ExecutionListButtons from './../executionListButtons/ExecutionListButtons'
import './../execution/Execution.css'

const ScriptsListScript = ({
    data,
    startUpdate,
    cancelUpdate,
    title,
    updateTitle,
    header,
    updateHeader,
    elementList,
    updateCommentText,
    movingIndex,
    moveFromTo,
    moveFrom,
    addCommentUnder,
    stopEdit,
    startEdit,
    removeScriptFormElement,
    deleteScriptFormData
    }) => {
    if (data.updating) {
        return (
            <div className="row execution mousable">
                <div className="col-md-12">
                    <ExecutionListButtons
                        mode = "SCRIPT_FORM"
                        setView = {() => { cancelUpdate(data.id) }}
                        deleteScriptFormData = { deleteScriptFormData }
                        saveScriptFormData = {() => { cancelUpdate(data.id) }}
                    />
                    <ScriptForm
                        title={title}
                        updateTitle={updateTitle}
                        header={header}
                        updateHeader={updateHeader}
                        elementList={elementList}
                        updateCommentText={updateCommentText}
                        movingIndex={movingIndex}
                        moveFromTo={moveFromTo}
                        moveFrom={moveFrom}
                        addCommentUnder={addCommentUnder}
                        stopEdit={stopEdit}
                        startEdit={startEdit}
                        removeScriptFormElement={removeScriptFormElement}
                    />
                </div>
            </div>
        )
    } else {
        return (
            <div className="row execution mousable" onDoubleClick = {() => {startUpdate(data)}}>
                <div className="col-md-4 execution-createdAt">
                     { data.createdAt.dayOfWeek }, { data.createdAt.dayOfMonth } { data.createdAt.month } { data.createdAt.year }, { data.createdAt.hour } : { data.createdAt.minute } : { data.createdAt.second }
                 </div>
                 <div className="col-md-8 query-preview">
                     { data.title }
                 </div>
            </div>
        )
    }
}

export default ScriptsListScript