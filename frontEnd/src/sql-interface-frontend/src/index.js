import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { createStore } from 'redux'
import sqlInterfaceApp from './reducers'
import App from './components/app/App'

let store = createStore(sqlInterfaceApp)

render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById('root')
)
