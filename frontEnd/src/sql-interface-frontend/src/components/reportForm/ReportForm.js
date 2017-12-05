import React from 'react'
import PropTypes from 'prop-types'
import './ReportForm.css'
import Editor from './../editor/Editor'
import Execution from './../execution/Execution'
import ExecutionButtons from './../executionButtons/ExecutionButtons'


const ReportForm = ({ executions, addReportNote, deleteReportNote, updateReportNote }) => (
    <div className="row">
        <div className="col-md-12">
            <div className="row description">
            <Editor
                rows={3}
                initialValue={""}
                updateValue={""}
                placeholder="Write Something about this report"
            />
            </div>
            {executions
                .filter(item => item.selected )
                .map(item => {
                if ( item.reportNote === undefined ) {
                    return (
                        <div className="row">
                            <div className="col-md-12">
                                <Execution
                                    key = { item.id }
                                    id = { item.id }
                                    isInPreview = { item.isInPreview }
                                    isLoading = { item.isLoading }
                                    mode = { "REPORT_FORM" }
                                    editing = { item.editing }
                                    selected = { item.selected }
                                    date = { item.date }
                                    comments = { item.comments }
                                    query = { item.query }
                                />
                            </div>
                            <div className="row item">
                                <ExecutionButtons
                                    execute={() => { addReportNote(item.id) }}
                                    name={"+"}
                                />
                            </div>
                        </div>
                    )
                } else {
                    return (
                        <div className="row">
                            <div className="col-md-12">
                                <Execution
                                    key = { item.id }
                                    id = { item.id }
                                    isInPreview = { item.isInPreview }
                                    isLoading = { item.isLoading }
                                    mode = { "REPORT_FORM" }
                                    editing = { item.editing }
                                    selected = { item.selected }
                                    date = { item.date }
                                    comments = { item.comments }
                                    query = { item.query }
                                />
                                <div className="row item">
                                    <Editor
                                        rows={3}
                                        initialValue={item.reportNote}
                                        updateValue={updateReportNote}
                                        placeholder="Comment"
                                    />
                                </div>
                                <div className="row item">
                                    <ExecutionButtons
                                        execute={() => { deleteReportNote(item.id) }}
                                        name={"x"}
                                    />
                                </div>
                            </div>
                        </div>
                    )
                }
            })}

        </div>
    </div>
)

ReportForm.propTypes = {
    addReportNote: PropTypes.func
}

export default ReportForm