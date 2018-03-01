import React from 'react'
import ScriptFormExecutionButtons from './../scriptFormExecutionButtons/ScriptFormExecutionButtons'
import './ScriptFormElementButtons.css'

const ScriptFormElementButtons = ({moveFrom, moveFromTo, addCommentUnder, remove}) => (
    <div className="row">
        { moveFrom && <ScriptFormExecutionButtons execute={moveFrom} name="move"/>}
        { moveFromTo && <ScriptFormExecutionButtons execute={moveFromTo} name="here"/>}
        { addCommentUnder && <ScriptFormExecutionButtons execute={addCommentUnder} name="+"/>}
        { remove && <ScriptFormExecutionButtons execute={remove} name="x"/>}
    </div>
)

export default ScriptFormElementButtons