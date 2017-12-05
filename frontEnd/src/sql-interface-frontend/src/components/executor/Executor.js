import React from 'react'
import { connect } from 'react-redux'
import Editor from './../editor/Editor'
import ExecutionButtons from './../executionButtons/ExecutionButtons'
import {
    setQuery,
    executeQuery,
    hideErrorExecutionResponse
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
        hideErrorResult: () => { dispatch(hideErrorExecutionResponse()) }
    }
}


const DisconnectedExecutor = ({query, loading, result, setSqlString, executeSql, hideErrorResult}) => {
    const updateQuery = event => {
        setSqlString(event.target.value)
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
                    <ExecutionButtons
                        execute={executeQuery}
                        name={"Execute"}
                    />
                </div>
            </div>
        )
    }

}

const Executor = connect(mapStateToProps, mapDispatchToProps)(DisconnectedExecutor)

export default Executor