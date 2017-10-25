import React from 'react'
import PropTypes from 'prop-types'
import './MenuItem.css'

const MenuItem = ({ name, onMenuItemClick }) => (
    <div
        className="col-md-12 MenuItem"
        onClick = {() => onMenuItemClick()}
    >
        { name }
    </div>
)

MenuItem.propTypes = {
    name: PropTypes.string.isRequired,
    onMenuItemClick: PropTypes.func
}

export default MenuItem