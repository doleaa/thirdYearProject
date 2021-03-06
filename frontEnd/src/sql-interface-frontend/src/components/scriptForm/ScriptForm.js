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
        stopEdit,
        startEdit,
        removeScriptFormElement}) => {
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
                    placeholder="Abstract"
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
                                            query = { item.execution.statement.sql }
                                            isMoving = { movingIndex !== undefined && movingIndex === index }
                                            isMovable = { movingIndex === undefined }
                                            moveFrom = {() => {moveFrom(index)}}
                                            moveTo = {() => {moveFromTo(movingIndex, index)}}
                                        />
                                    }
                                    {item.comment && item.comment.editing &&
                                        <div className="row">
                                            <Editor
                                                rows={3}
                                                initialValue={item.comment.text}
                                                updateValue={updateComment}
                                                placeholder="Comment"
                                            />
                                        </div>
                                    }
                                    {item.comment && !item.comment.editing &&
                                        <ExecutionLikeComment
                                            comments={item.comment.text}
                                            isMoving = { movingIndex !== undefined && movingIndex === index }
                                            isMovable = { movingIndex === undefined }
                                            moveFrom = {() => {moveFrom(index)}}
                                            moveTo = {() => {moveFromTo(movingIndex, index)}}
                                        />
                                    }
                                </div>
                                <div className="col-md-1">
                                    {movingIndex === undefined && item.comment &&
                                        <ScriptFormElementButtons
                                            addCommentUnder = {() => {addCommentUnder(index)}}
                                            remove = {() => {removeScriptFormElement(index)}}
                                            isComment = { true }
                                            editing = {item.comment.editing}
                                            startEdit = {() => {startEdit(index)}}
                                            stopEdit = {() => {stopEdit(index)}}
                                        />
                                    }
                                    {movingIndex === undefined && !item.comment &&
                                        <ScriptFormElementButtons
                                            addCommentUnder = {() => {addCommentUnder(index)}}
                                            remove = {() => {removeScriptFormElement(index)}}
                                            isComment = { false }
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

export default ScriptForm