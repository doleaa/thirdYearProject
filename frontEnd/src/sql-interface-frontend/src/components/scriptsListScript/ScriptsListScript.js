import React from 'react'
import './../execution/Execution.css'

const ScriptsListScript = ({title, date}) => (
     <div
     className="row execution mousable"
     >
         <div className="col-md-4 execution-date">
             { date.dayOfWeek }, { date.dayOfMonth } { date.month } { date.year }, { date.hour } : { date.minute } : { date.second }
         </div>
         <div className="col-md-8 query-preview">
             { title }
         </div>
     </div>
)

export default ScriptsListScript