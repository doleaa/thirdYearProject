import React from 'react'
import PropTypes from 'prop-types'
import './Execution.css'

const Execution = ({ isInPreview, date, comments, query }) => {

    if ( isInPreview ) {
        return (
            <div className="row execution">
                <div className="col-md-4 execution-date">
                    {date}
                </div>
                <div className="col-md-8 comments-preview">
                    {comments}
                </div>
            </div>
        )
    } else {
        return (
            <div className="row execution">
                <div
                className="col-md-12 execution-date"
                onClick = {() => { this.props.isInPreview = !this.props.isInPreview }}
                >
                    {date}
                </div>
                <div
                className="col-md-12 comments"
                onClick = {() => { this.props.isInPreview = !this.props.isInPreview }}
                >
                    {comments}
                </div>
                <div className="col-md-12 query">
                </div>
                <div className="col-md-12 query">
                    {query}
                </div>
            </div>
        )
    }
}

Execution.propTypes = {
    isInPreview: PropTypes.bool
}

export default Execution