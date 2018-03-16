import React from 'react'

const ExecutionListButtons = ({
        mode,
        setEdit,
        setView,
        setScriptList,
        setScriptForm,
        setSelect,
        deleteScriptFormData,
        saveScriptFormData,
        runScript,
        createSampleForScript,
        runScriptAgainstSample,
        sample
    }) => {
    if ( mode === "VIEW") {
        return (
            <div className="row">
                <div className="col-md-12">
                    <div className="pull-left">
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick = { setScriptList }
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
                            saveScriptFormData()
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
    } else if ( mode === "SCRIPT_FORM_EDITING" ) {
        return (
            <div className="row buttgr">
                <div className="col-md-12">
                    <button
                        type="button"
                        className="btn btn-primary pull-right"
                        onClick={ () => {
                            saveScriptFormData()
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
                        Close
                    </button>
                    <button
                        type="button"
                        className="btn btn-primary pull-left"
                        onClick={ () => { runScript() } }
                    >
                        Run
                    </button>
                    <button
                        type="button"
                        className="btn btn-primary pull-left"
                        onClick={ () => { createSampleForScript() } }
                    >
                        Create Sample
                    </button>
                    { runScriptAgainstSampleBtn(sample, runScriptAgainstSample) }
                </div>
            </div>
        )
    } else if ( mode === "SCRIPT_LIST" ) {
        return (
            <div className="row">
                <div className="col-md-12">
                    <div className="pull-left">
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick = { setView }
                        >
                            Executions
                        </button>
                    </div>
                </div>
            </div>
        )
    }
}

const runScriptAgainstSampleBtn = (sample, runScriptAgainstSample) => {
    if ( sample !== null ) {
        return(
            <button
              type="button"
              className="btn btn-primary pull-left"
              onClick={ () => { runScriptAgainstSample() } }
            >
              Run Against Sample
            </button>
        )
    }
     return null;
}

export default ExecutionListButtons