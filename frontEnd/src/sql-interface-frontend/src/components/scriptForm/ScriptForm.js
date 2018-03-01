import React from 'react'
import PropTypes from 'prop-types'
import './ScriptForm.css'
import Editor from './../editor/Editor'
import ExecutionButtons from './../executionButtons/ExecutionButtons'
import { ScriptFormExecution, ExecutionLikeComment } from './../execution/ExecutionEnum'
import ScriptFormElementButtons from './../scriptFormElementButtons/ScriptFormElementButtons'


const ScriptForm = ({
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
        startEdit,
        stopEdit}) => {
    const updateScriptTitle = event => {
        updateTitle(event.target.value)
    }
    const updateScriptHeader = event => {
        updateHeader(event.target.value)
    }
    return (
        <div className="row">
            <div className="col-md-12">
                <div className="row description">
                <Editor
                    rows={1}
                    initialValue={title}
                    updateValue={updateScriptTitle}
                    placeholder="Title"
                />
                <Editor
                    rows={3}
                    initialValue={header}
                    updateValue={updateScriptHeader}
                    placeholder="Header"
                />
                </div>
                {elementList
                    .map((item, index) => {
                        const updateComment = event => {
                            updateCommentText(index, event.target.value)
                        }

                        return (
                            <div key={index}>
                                <div className="col-md-11">
                                    {item.execution &&
                                        <ScriptFormExecution
                                            comments = { item.execution.comment ? item.execution.comment.text : item.execution.comment }
                                            query = { item.execution.statement.sql }
                                            resultTableData = {
                                                item.execution.result ? JSON.parse(item.execution.result.resultString) : {columns: [], rows: []}
                                            }
                                        />
                                    }
                                    {item.comment && item.comment.editing &&
                                        <div className="row">
                                            <EditingEditor
                                                item={item}
                                                updateComment={updateComment}
                                                stopEdit={() => {stopEdit(index)}}
                                            />
                                        </div>
                                    }
                                    {item.comment && !item.comment.editing &&
                                        <ExecutionLikeComment comments={item.comment.text} startEdit={() => {startEdit(index)}}/>
                                    }
                                </div>
                                <div className="col-md-1">
                                    {movingIndex && movingIndex !== index &&
                                        <ScriptFormElementButtons
                                            moveFromTo={() => {moveFromTo(movingIndex, index)}}
                                        />
                                    }
                                    {!movingIndex && index !== 0 &&
                                        <ScriptFormElementButtons
                                            moveFrom={() => {moveFrom(index)}}
                                            addCommentUnder={() => {addCommentUnder(index)}}
                                        />
                                    }
                                    {!movingIndex && index === 0 &&
                                        <ScriptFormElementButtons
                                            addCommentUnder={() => {addCommentUnder(index)}}
                                        />
                                    }
                                    {!movingIndex && elementList.length === 1 &&
                                        <ScriptFormElementButtons
                                            addCommentUnder={() => {addCommentUnder(index)}}
                                        />
                                    }
                                </div>
                            </div>
                        )
                    })
                }
            </div>
        </div>
    )
}

const EditingEditor = ({item, updateComment, stopEdit}) => (
    <div className="row">
        <Editor
            rows={3}
            initialValue={item.comment.text}
            updateValue={updateComment}
            placeholder="Comment"
        />
        <ExecutionButtons execute={stopEdit} name="Save"/>
    </div>
)

export default ScriptForm