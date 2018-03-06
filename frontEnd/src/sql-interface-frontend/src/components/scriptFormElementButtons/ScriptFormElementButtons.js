import React from 'react'
import ScriptFormExecutionButtons from './../scriptFormExecutionButtons/ScriptFormExecutionButtons'
import './ScriptFormElementButtons.css'

const ScriptFormElementButtons = ({addCommentUnder, remove, isComment, editing, startEdit, stopEdit}) => (
    <div className="row elementButtons">
        { isComment && editing && <ScriptFormExecutionButtons execute={stopEdit} size={6} name="Save"/> }
        { isComment && !editing && <ScriptFormExecutionButtons execute={startEdit} size={6} name="Edit"/> }
        <ScriptFormExecutionButtons execute={addCommentUnder} size={3} name="+"/>
        <ScriptFormExecutionButtons execute={remove} size={3} name="x"/>
    </div>
)

export default ScriptFormElementButtons