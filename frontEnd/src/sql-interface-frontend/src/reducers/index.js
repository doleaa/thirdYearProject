import { combineReducers } from 'redux'
import mode from './mode'

const sqlInterfaceApp = combineReducers({ mode })

export default sqlInterfaceApp