import React from 'react'
import spinner from './../../spinner.svg'
import { setMode,  setInitialExecutionListState } from './../../actions'
import { connect } from 'react-redux'
import MenuItem from './../menuItem/MenuItem'
import './Menu.css'

const mapDispatchToProps = dispatch => {
    return {
        setMode: mode => {
            dispatch(setMode(mode))
        },
        setInitialExecutionListState: () => {
            dispatch(setInitialExecutionListState())
        }
    }
}

const DisconnectedMenu = ({ setMode, setInitialExecutionListState }) => (
    <div className="col-md-2 fixed-tope Menu">
        <div className="row">
            <img
                src={spinner}
                className="col-md-12"
                alt="logo"
            />
            <MenuItem
                name="Executor"
                onMenuItemClick={() => {
                    setMode("EXECUTOR")
                    setInitialExecutionListState()
                }}
            />
            <MenuItem
                name="History"
                onMenuItemClick={() => setMode("HISTORY")}
            />
            <MenuItem
                name="Notes"
                onMenuItemClick={() => {
                    setMode("NOTES")
                    setInitialExecutionListState()
                }}
            />
            <MenuItem
                name="Scripts"
                onMenuItemClick={() => {
                    setMode("SCRIPTS")
                    setInitialExecutionListState()
                }}
            />
        </div>
    </div>
)

const Menu = connect(null, mapDispatchToProps)(DisconnectedMenu)

export default Menu