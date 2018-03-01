import React from 'react'
import ScriptFormExecutionButtons from './../scriptFormExecutionButtons/ScriptFormExecutionButtons'
import './ScriptFormElementButtons.css'

const ScriptFormElementButtons = ({moveFrom, moveFromTo, addCommentUnder}) => (
    <div className="row">
        { moveFrom && <ScriptFormExecutionButtons execute={moveFrom} name="move"/>}
        { moveFromTo && <ScriptFormExecutionButtons execute={moveFromTo} name="here"/>}
        { addCommentUnder && <ScriptFormExecutionButtons execute={addCommentUnder} name="+"/>}
    </div>
)

export default ScriptFormElementButtons