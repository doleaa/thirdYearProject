import React from 'react'
import ScriptsListScript from './../scriptsListScript/ScriptsListScript'
import './ScriptsList.css'

const ScriptsList = ({ list }) => (
    <div className="row">
        <div className="col-md-12">
            {list.map(item => (
                <ScriptsListScript
                    title={ item.title }
                    date={ item.createdAt }
                />
            ))}
        </div>
    </div>
)

export default ScriptsList