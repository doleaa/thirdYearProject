import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './ExecutionList.css'
import Execution from './../execution/Execution'
import { changePreviewState }from './../../actions'

const mapStateToProps = state => {
    return {
        executionList: state.history.executionsList
    }
}

const mapDispatchToProps = dispatch => {
    return {
        changeState: id => { dispatch(changePreviewState(id)) }
    }
}

const DisconnectedExecutionList = ({ executionList, changeState }) => (
    <div className="col-md-12">
        {executionList.map(item => (
            <Execution
                key = { item.id }
                isInPreview = { item.isInPreview }
                date = { item.date }
                comments = { item.comments }
                query = { item.query }
                changePreviewState = { () => changeState(item.id) }
            />
        ))}
    </div>
)

DisconnectedExecutionList.propTypes = {
    executionList: PropTypes.array,
    changePreviewState: PropTypes.func
}

const ExecutionList = connect(mapStateToProps, mapDispatchToProps)(DisconnectedExecutionList)

export default ExecutionList