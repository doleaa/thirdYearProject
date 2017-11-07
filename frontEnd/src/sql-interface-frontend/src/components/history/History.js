import React from 'react'
import './History.css'
import ExecutionList from './../executionList/ExecutionList'

const History = () => (
    <div className="col-md-10 ContainerView">
        <div className='row'>
            <ExecutionList/>
        </div>
    </div>
)

export default History