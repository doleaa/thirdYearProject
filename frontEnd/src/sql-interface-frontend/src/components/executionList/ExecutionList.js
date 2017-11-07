import React from 'react'
import './ExecutionList.css'
import Execution from './../execution/Execution'

const ExecutionList = () => (
    <div className="col-md-12">
        <div className="row execution">
            <div className="col-md-4 execution-date">
                24-03-2015
            </div>
            <div className="col-md-8 comments-preview">
                Comments fdafafdassd fsdgdshjgd dsfhbafdsahj fadshiadsglas fdsaklhasdl.
            </div>
        </div>
        <div className="row execution">
            <div className="col-md-12 execution-date">
                24-03-2015
            </div>
            <div className="col-md-12 comments">
                Comments fdafafdassd fsdgdshjgd dsfhbafdsahj fadshiadsglas fdsaklhasdl.
            </div>
            <div className="col-md-12 query">
            </div>
            <div className="col-md-12 query">
                Some random query.
            </div>
        </div>
        <Execution
            isInPreview = { true }
            date = "24-03-2015"
            comments = "Comments fdafafdassd fsdgdshjgd dsfhbafdsahj fadshiadsglas fdsaklhasdl."
            query = "Some random query."
        />
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
        <div className="execution">
        </div>
    </div>
)

export default ExecutionList