import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './ExecutionList.css'
import Execution from './../execution/Execution'
import { changePreviewState, getExecutionList }from './../../actions'

const mapStateToProps = state => {
    return {
        executionList: state.history.executionsList,
        listMode: state.history.mode
    }
}

const mapDispatchToProps = dispatch => {
    return {
        changeState: id => { dispatch(changePreviewState(id)) },
        getExecutionList: () => { dispatch(getExecutionList()) }

    }
}

const DisconnectedExecutionList = ({ executionList, listMode, changeState, getExecutionList }) => {
    if (executionList.length ===0) {
        getExecutionList()
    }
    return (
        <div className="col-md-12">
            {executionList.map(item => (
                <Execution
                    key = { item.id }
                    isInPreview = { item.isInPreview }
                    isLoading = { item.isLoading }
                    mode = { listMode }
                    date = { item.date }
                    comments = { item.comments }
                    query = { item.query }
                    changePreviewState = { () => changeState(item.id) }
                />
            ))}
        </div>
    )
}

DisconnectedExecutionList.propTypes = {
    executionList: PropTypes.array,
    changePreviewState: PropTypes.func
}

const ExecutionList = connect(mapStateToProps, mapDispatchToProps)(DisconnectedExecutionList)

export default ExecutionList