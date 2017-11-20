import { combineReducers } from 'redux'
import mode from './mode'
import execution from './execution'
import history from './history'

const sqlInterfaceApp = combineReducers({ mode, execution, history })

export default sqlInterfaceApp