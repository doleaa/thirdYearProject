import React from 'react'
import './History.css'
import ExecutionList from './../executionList/ExecutionList'

const History = () => (
    <div className="ContainerView">
        <div className='row'>
            <ExecutionList/>
        </div>
    </div>
)

export default History