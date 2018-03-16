import React from 'react'
import spinner from './../../spinner.svg'
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
    deleteScriptFormData,
    updateScriptFormData,
    runScript,
    createSampleForScript,
    runScriptAgainstSample
    }) => (
    <div className="row">
        <div className="col-md-12">
            {list.map(item => {
                if (item.loading) {
                    return (
                        <div className="row  executionSpinner">
                            <img
                                src={spinner}
                                className="col-md-12 spinner"
                                alt="logo"
                            />
                        </div>
                    )
                } else {
                    return (
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
                            updateScriptFormData={() => {updateScriptFormData(item.id)}}
                            runScript={() => {runScript(item.id)}}
                            createSampleForScript={() => {createSampleForScript(item.id)}}
                            runScriptAgainstSample={() => {runScriptAgainstSample(item.id)}}
                            sample = {item.sample}
                        />
                    )}
                }
            )}
        </div>
    </div>
)

export default ScriptsList