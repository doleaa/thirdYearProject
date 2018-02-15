import React from 'react'
import PropTypes from 'prop-types'
import './ResultTable.css'

const ResultTable = ({columns, rows}) => (
    <div className="col-md-12">
        <div className="tableOuter">
            <table className="table">
                <thead>
                    <tr>
                        {columns.map(column => (
                            <th> { column } </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {rows.map(row => (
                        <tr>
                            {columns.map(column => (
                                <th>{ row[column] }</th>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    </div>
)

ResultTable.propTypes = {
    columns: PropTypes.array.isRequired,
    rows: PropTypes.array.isRequired
}

export default ResultTable