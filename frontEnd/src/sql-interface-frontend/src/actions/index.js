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
    "url": "jdbc:h2:/tmp/bam/test;AUTO_SERVER=TRUE",
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

        const http = new XMLHttpRequest()
        const url = "http://127.0.0.1:8090/execution"
        const method = "POST"

        http.open(method, url, true)
        http.setRequestHeader("Content-type", "application/json")

        http.onreadystatechange = () => {
            if ( http.status === 200 ) {
                dispatch(interpretResponse(http))
            }
        }

        http.send(JSON.stringify(requestBody))
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

export const setInitialExecutionListState = () => {
    return {
        type: "SET_INITIAL_EXECUTION_LIST_STATE"
    }
}
