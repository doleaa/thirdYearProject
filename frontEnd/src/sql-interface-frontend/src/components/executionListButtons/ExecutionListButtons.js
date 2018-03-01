import React from 'react'

const ExecutionListButtons = ({ mode, setEdit, setView, setScriptForm, setSelect, deleteScriptFormData }) => {
    if ( mode === "VIEW") {
        return (
            <div className="row">
                <div className="col-md-12">
                    <div className="pull-left">
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick = { () => {} }
                        >
                            Scripts
                        </button>
                    </div>
                    <div className="pull-right">
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick = { setEdit }
                        >
                            Comment/Edit
                        </button>
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick={ setSelect }
                        >
                            Select
                        </button>
                    </div>
                </div>
            </div>
        )
    } else if ( mode === "EDIT" ) {
        return (
            <div className="row">
                <div className="col-md-12">
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ setView }
                    >
                        Done
                    </button>
                </div>
            </div>
        )
    } else if ( mode === "SELECT" ) {
        return (
            <div className="row">
                <div className="col-md-12">
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ () => {
                            setView()
                        } }
                    >
                        Cancel
                    </button>
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ setScriptForm }
                    >
                        Create Script
                    </button>
                </div>
            </div>
        )
    } else if ( mode === "SCRIPT_FORM" ) {
        return (
            <div className="row buttgr">
                <div className="col-md-12">
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ () => {
                            setView()
                        } }
                    >
                        Save
                    </button>
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ () => {
                            setView()
                            deleteScriptFormData()
                        } }
                    >
                        Cancel
                    </button>
                </div>
            </div>
        )
    }
}

export default ExecutionListButtons