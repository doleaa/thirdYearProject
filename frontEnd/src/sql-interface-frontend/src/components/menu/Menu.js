import React from 'react'
//import spinner from './../../spinner.svg'
import { setMode } from './../../actions'
import { connect } from 'react-redux'
import MenuItem from './../menuItem/MenuItem'
import './Menu.css'
//        <img src={spinner} className="Spinner" alt="logo" />

const mapDispatchToProps = dispatch => {
    return {
        setMode: mode => {
            dispatch(setMode(mode))
        }
    }
}

const DisconnectedMenu = ({ setMode }) => (
    <div className="col-md-2 Menu">
        <div className="row">
            <MenuItem
                name="Executor"
                onMenuItemClick={() => setMode("EXECUTOR")}
            />
            <MenuItem
                name="History"
                onMenuItemClick={() => setMode("HISTORY")}
            />
            <MenuItem
                name="Notes"
                onMenuItemClick={() => setMode("NOTES")}
            />
        </div>
    </div>
)

const Menu = connect(null, mapDispatchToProps)(DisconnectedMenu)

export default Menu