import React from 'react'
import History from './../history/History'
import Executor from './../executor/Executor'
import './ActivityView.css'

const ActivityView = () => {

    return (
        <div>
            <Executor/>
            <History/>
        </div>
    )
}

export default ActivityView