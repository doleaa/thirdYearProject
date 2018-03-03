import React from 'react'
import ScriptFormExecutionButtons from './../scriptFormExecutionButtons/ScriptFormExecutionButtons'
import './ScriptFormElementButtons.css'

const ScriptFormElementButtons = ({moveFrom, moveFromTo, addCommentUnder, remove}) => (
    <div className="row">
        { moveFrom && <ScriptFormExecutionButtons execute={moveFrom} size={6} name="move"/>}
        { moveFromTo && <ScriptFormExecutionButtons execute={moveFromTo} size={6} name="here"/>}
        { addCommentUnder && <ScriptFormExecutionButtons execute={addCommentUnder} size={3} name="+"/>}
        { remove && <ScriptFormExecutionButtons execute={remove} size={3} name="x"/>}
    </div>
)

export default ScriptFormElementButtons