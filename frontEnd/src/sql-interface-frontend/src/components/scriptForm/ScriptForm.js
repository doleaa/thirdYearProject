import React from 'react'
import PropTypes from 'prop-types'
import './ScriptForm.css'
import Editor from './../editor/Editor'
import Execution from './../execution/Execution'
import ExecutionButtons from './../executionButtons/ExecutionButtons'


const ScriptForm = ({
        title,
        updateTitle,
        header,
        updateHeader,
        elementList }) => {
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
                    .map(item => {
                        return (
                            <Execution
                                key = { item.id }
                                id = { item.id }
                                isInPreview = { item.isInPreview }
                                isLoading = { item.isLoading }
                                mode = { "REPORT_FORM" }
                                editing = { item.editing }
                                selected = { item.selected }
                                date = { item.ranAt }
                                comments = { item.comment ? item.comment.text : item.comment }
                                query = { item.statement.sql }
                            />
                        )
                    })
                }
            </div>
        </div>
    )
}

export default ScriptForm