export const setMode = mode => {
    return {
        type: "SET_MODE",
        mode
    }
}

export const setQuery = query => {
    return {
        type: "SET_QUERY",
        query
    }
}

const initiateExecution = () => {
    return {
        type: "INITIATE_EXECUTION"
    }
}

const interpretResponse = response => {
    return {
        type: "INTERPRET_RESPONSE",
        response
    }
}

const dbMap = {
    "url": "jdbc:h2:/tmp/bang/test;AUTO_SERVER=TRUE",
    "username": "sa",
    "password": ""
}

export const executeQuery = query => {
    return dispatch => {
        dispatch(initiateExecution())

        const requestBody = {
            "sqlCommand" : query,
            "dbMap": dbMap,
            "comments": ""
        }

        const url = "http://127.0.0.1:8090/execution"
        const method = "POST"

        fetch(url, {
            method,
            mode: 'cors',
            body: JSON.stringify(requestBody),
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        })
        .then(response => {
            if (response.status !== 200) {
                throw new Error(response.statusText)
            }
            dispatch(getExecutionList())
            return response.json()
        })
        .then(json => {
            json.status = 200;
            dispatch(interpretResponse(json))
        })
        .catch(error => {
            dispatch(interpretResponse({status: 500}))
            console.log(error)
        })
    }
}

const mapResponseList = list => {
    return {
        type: "MAP_RESPONSE_LIST",
        executionsList: list
    }
}

export const getExecutionList = () => {
    return dispatch => {
        const url = "http://127.0.0.1:8090/executions"
        const headers = new Headers(dbMap)

        const request = new Request(url, {
            headers
        })

        fetch(request)
        .then(response => {
            return response.json()
        })
        .then(json => {
            const list = json
                .map(item => {
                    item.isInPreview = true
                    item.isLoading = false
                    item.editing = false
                    item.selected = false
                    return item
                })
            dispatch(mapResponseList(list))
        })
        .catch( error => console.log(error) )
    }
}

export const changePreviewState = id => {
    return {
        type: "CHANGE_PREVIEW_STATE",
        executionId: id
    }
}

export const startEditingExecution = id => {
    return {
        type: "START_EDITING_EXECUTION",
        executionId: id
    }
}

export const stopEditingExecution = id => {
    return {
        type: "STOP_EDITING_EXECUTION",
        executionId: id
    }
}

export const addReportNoteToExecution = id => {
    return {
        type: "ADD_REPORT_NOTE_TO_EXECUTION",
        executionId: id
    }
}

export const deleteReportNoteFromExecution = id => {
    return {
        type: "DELETE_REPORT_NOTE_FROM_EXECUTION",
        executionId: id
    }
}

export const selectExecution = id => {
    return {
        type: "SELECT_EXECUTION",
        executionId: id
    }
}

export const unSelectExecution = id => {
    return {
        type: "UNSELECT_EXECUTION",
        executionId: id
    }
}

export const unSelectAllExecutions = () => {
    return {
        type: "UNSELECT_ALL_EXECUTIONS"
    }
}

export const setExecutionListToEditMode = mode => {
    return {
        type: "SET_EXECUTION_LIST_MODE",
        mode: "EDIT"
    }
}

export const setExecutionListToSelectMode = mode => {
    return {
        type: "SET_EXECUTION_LIST_MODE",
        mode: "SELECT"
    }
}

export const setExecutionListToReportForm = mode => {
    return {
        type: "SET_EXECUTION_LIST_MODE",
        mode: "REPORT_FORM"
    }
}

export const setExecutionListToViewMode = mode => {
    return {
        type: "SET_EXECUTION_LIST_MODE",
        mode: "VIEW"
    }
}

export const updateExecutionComments = (id, comments) => {
    return {
        type: "UPDATE_EXECUTION_COMMENTS",
        id,
        comments
    }
}

export const updateExecutionReportNote = (id, reportNote) => {
    return {
        type: "UPDATE_EXECUTION_REPORT_NOTE",
        id,
        reportNote
    }
}

export const setInitialExecutionListState = () => {
    return {
        type: "SET_INITIAL_EXECUTION_LIST_STATE"
    }
}

export const hideErrorExecutionResponse = () => {
    return {
        type: "HIDE_ERROR_RESPONSE"
    }
}

export const clearExecutionResponseData = () => {
    return {
        type: "CLEAR_RESPONSE_DATA"
    }
}
