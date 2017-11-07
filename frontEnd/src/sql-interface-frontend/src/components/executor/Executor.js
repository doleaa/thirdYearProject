import React from 'react'
import { connect } from 'react-redux'
import Editor from './../editor/Editor'
import ExecutionButtons from './../executionButtons/ExecutionButtons'
import { setQuery, executeQuery } from './../../actions'
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
        executeSql: query => { dispatch(executeQuery(query)) }
    }
}


const DisconnectedExecutor = ({query, loading, result, setSqlString, executeSql}) => {
    const updateQuery = event => {
        setSqlString(event.target.value)
    }

    const executeQuery = () => {
        executeSql(query)
    }

    return (
        <div className="col-md-10 ContainerView">
            <div className='row'>
                <Editor
                    query={query}
                    updateQuery={updateQuery}
                />
                <ExecutionButtons
                    executeQuery={executeQuery}
                />
            </div>
        </div>
    )
}

const Executor = connect(mapStateToProps, mapDispatchToProps)(DisconnectedExecutor)

export default Executor