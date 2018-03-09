import React from 'react'
import ScriptsListScript from './../scriptsListScript/ScriptsListScript'
import './ScriptsList.css'

const ScriptsList = ({
    list,
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
    }) => (
    <div className="row">
        <div className="col-md-12">
            {list.map(item => (
                <ScriptsListScript
                    data = { item }
                    startUpdate = {() => { startUpdate(item) }}
                    cancelUpdate = {() => { cancelUpdate(item.id) }}
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
                    deleteScriptFormData={deleteScriptFormData}
                />
            ))}
        </div>
    </div>
)

export default ScriptsList