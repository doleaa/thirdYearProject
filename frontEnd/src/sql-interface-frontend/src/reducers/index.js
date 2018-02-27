import { combineReducers } from 'redux'
import execution from './execution'
import history from './history'

const sqlInterfaceApp = combineReducers({ execution, history })

export default sqlInterfaceApp