import { combineReducers } from 'redux'
import mode from './mode'
import execution from './execution'

const sqlInterfaceApp = combineReducers({ mode, execution })

export default sqlInterfaceApp