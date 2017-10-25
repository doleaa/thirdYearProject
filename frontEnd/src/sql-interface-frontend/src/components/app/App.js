import React from 'react'
import Menu from './../menu/Menu'
import ActivityView from './../activityView/ActivityView'
import './App.css'

const App = () => (
    <div id="container" className="container">
        <div className="row App">
          <Menu/>
          <ActivityView/>
        </div>
    </div>
)

export default App
