const backEndHostName = "127.0.0.1"

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

        const url = `http://${backEndHostName}:8090/execution`
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
        const url = `http://${backEndHostName}:8090/executions`
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
        type: "CHANGE_EXECUTION_PREVIEW_STATE",
        executionId: id
    }
}

export const saveScript = scriptForm => {
    return dispatch => {
        scriptForm.elementList = scriptForm.elementList
                .map((element, index) => {
                    element.position = index + 1
                    return element
                })

        const requestBody = {
            "dbMap": dbMap,
            "script": scriptForm
        }

        const url = `http://${backEndHostName}:8090/script`
        const method = "POSt"

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
                dispatch(setExecutionListToViewMode())
                throw new Error(response.statusText)
            }
            return response.json()
        })
        .then(json => {
            console.log(json)
            dispatch(setExecutionListToViewMode())
        })
        .catch( error => console.log(error) )
    }



}

export const setScriptFormData = (title, header, elementList) => {
    return {
        type: "SET_SCRIPT_FORM_DATA",
        title,
        header,
        elementList
    }
}

export const deleteScriptFormData = () => {
    return {
        type: "DELETE_SCRIPT_FORM_DATA"
    }
}

export const updateScriptFormTitle = title => {
    return {
        type: "UPDATE_SCRIPT_FORM_TITLE",
        title
    }
}
export const updateScriptFormHeader = header => {
    return {
        type: "UPDATE_SCRIPT_FORM_HEADER",
        header
    }
}

export const moveScriptFormElementFromTo = (from, to) => {
    return {
        type: "MOVE_SCRIPT_FORM_ELEMENT_FROM_TO",
        from,
        to
    }
}

export const startMovingScriptFormElementFrom = index => {
    return {
        type: "START_MOVING_SCRIPT_FORM_ELEMENT_FROM",
        index
    }
}

export const addScriptFormCommentElementUnder = under => {
    return {
        type: "ADD_SCRIPT_FORM_COMMENT_ELEMENT_UNDER",
        under
    }
}

export const updateScriptFormCommentElement = (index, comment) => {
    return {
        type: "UPDATE_SCRIPT_FORM_COMMENT_ELEMENT_TEXT",
        text: comment,
        index
    }
}

export const removeScriptFormElement = index => {
    return {
        type: "REMOVE_SCRIPT_FORM_ELEMENT",
        index
    }
}

export const startEditingScriptFormCommentElement = index => {
    return {
        type: "START_EDITING_SCRIPT_FORM_COMMENT_ELEMENT_TEXT",
        index
    }
}

export const stopEditingScriptFormCommentElement = index => {
    return {
        type: "STOP_EDITING_SCRIPT_FORM_COMMENT_ELEMENT_TEXT",
        index
    }
}

export const startEditingExecution = id => {
    return {
        type: "START_EDITING_EXECUTION",
        executionId: id
    }
}

const startExecutionLoading = id => {
    return {
        type: "START_EXECUTION_LOADING",
        executionId: id
    }
}

export const saveExecutionComments = ( id, comments ) => {
    return dispatch => {
        dispatch(startExecutionLoading(id))

        const requestBody = {
            "dbMap": dbMap,
            "newComment": comments
        }

        const url = `http://${backEndHostName}:8090/execution/${id}/comment`
        const method = "PUT"

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
            return response.json()
        })
        .then(json => {
            dispatch(stopEditingExecution(id, json))
            dispatch(stopExecutionLoading(id))
        })
        .catch(error => {
            console.log(error)
        })
    }
}

export const stopEditingExecution = ( id, comment ) => {
    return {
        type: "STOP_EDITING_EXECUTION",
        executionId: id,
        comment: comment
    }
}

export const stopExecutionLoading = id => {
    return {
        type: "STOP_EXECUTION_LOADING",
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

export const setExecutionListToScriptForm = () => {
    return {
        type: "SET_EXECUTION_LIST_MODE",
        mode: "SCRIPT_FORM"
    }
}

export const setExecutionListToViewMode = () => {
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
