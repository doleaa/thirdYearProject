import React from 'react'
import HistoryContainer from './../historyContainer/HistoryContainer'
import Executor from './../executor/Executor'
import './ActivityView.css'

const ActivityView = () => {

    return (
        <div>
            <Executor/>
            <HistoryContainer/>
        </div>
    )
}

export default ActivityView