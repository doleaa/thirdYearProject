import React from 'react'
import { connect } from 'react-redux'
import FileSaver from 'file-saver'
import Editor from './../editor/Editor'
import ExecutionButtons from './../executionButtons/ExecutionButtons'
import ResultTable from './../resultTable/ResultTable'
import {
    setQuery,
    executeQuery,
    hideErrorExecutionResponse,
    clearExecutionResponseData
} from './../../actions'
import './Executor.css'

const mapStateToProps = state => {
    return {
        query: state.execution.query,
        loading: state.execution.loading,
        result: state.execution.result
    }
}

const mapDispatchToProps = dispatch => {
    return {
        setSqlString: query => { dispatch(setQuery(query)) },
        executeSql: query => { dispatch(executeQuery(query)) },
        hideErrorResult: () => { dispatch(hideErrorExecutionResponse()) },
        clearResultData: () => { dispatch(clearExecutionResponseData()) }
    }
}


const DisconnectedExecutor = ({query, loading, result, setSqlString, executeSql, hideErrorResult, clearResultData}) => {
    const updateQuery = event => {
        setSqlString(event.target.value)
    }

    const statementsList = [
        {sql: "CREATE TABLE alex;", createdBy: "alex"},
        {sql: "DROP TABLE alex;", createdBy: "alex"},
        {sql: "CREATE TABLE george;", createdBy: "george"},
        {sql: "DROP TABLE george;", createdBy: "george"}
    ]

    const saveSqlScript = () => {
        var blob = new Blob([statementsList.map(statement => statement.sql).join('\n')], {type: "text/plain;charset=utf-8"});
        FileSaver.saveAs(blob, "TestScript.sql");
    }

    const executeQuery = () => {
        executeSql(query)
    }

    if (result !== undefined && result.ok !== undefined && !result.ok) {
        return (
            <div className="ContainerView">
                <div className='row'>
                    <Editor
                        rows={10}
                        initialValue={query}
                        updateValue={updateQuery}
                        placeholder="TYPE SQL IN HERE"
                    />
                    <ExecutionButtons
                        execute={executeQuery}
                        name={"Execute"}
                    />
                    <div className="col-md-12">
                        There seems to have been a problem with executing your query,
                        please make sure your sql is correct and try again.
                        <a className="pull-right" onClick= {() => { hideErrorResult() }}>x</a>
                    </div>
                </div>
            </div>
        )
    } else {
        return (
            <div className="ContainerView">
                <div className='row'>
                    <Editor
                        rows={10}
                        initialValue={query}
                        updateValue={updateQuery}
                        placeholder="TYPE SQL IN HERE"
                    />
                </div>
                <div className='row'>
                    <ExecutionButtons
                        execute={executeQuery}
                        name={"Execute"}
                    />
                </div>
                <div className='row'>
                    {result.data && result.data.columns && result.data.rows &&
                        <div className="col-md-12">
                            <a className="pull-right" onClick= {() => { clearResultData() }}>x</a>
                        </div>
                    }
                    <ResultTable
                        columns={
                            (result.data && result.data.columns) ?
                            result.data.columns : []
                        }
                        rows={
                            (result.data && result.data.rows) ?
                            result.data.rows : []
                        }
                    />
                </div>
            </div>
        )
    }

}

const Executor = connect(mapStateToProps, mapDispatchToProps)(DisconnectedExecutor)

export default Executor