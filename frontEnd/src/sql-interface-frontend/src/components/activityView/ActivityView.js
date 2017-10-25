import React from 'react'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import History from './../history/History'
import Notes from './../notes/Notes'
import Executor from './../executor/Executor'
import './ActivityView.css'

const mapStateToProps = state => {
    return {
        mode: state.mode
    }
}

const DisconnectedActivityView = ({ mode }) => {

    if (mode === "HISTORY") { return ( <History/> ) }

    if (mode === "NOTES") { return ( <Notes/> ) }

    return ( <Executor/> )
}

DisconnectedActivityView.propTypes = {
    mode: PropTypes.string.isRequired
}

const ActivityView = connect(mapStateToProps)(DisconnectedActivityView)

export default ActivityView