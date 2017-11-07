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

export const executeQuery = query => {
    return dispatch => {
        dispatch(initiateExecution())

        const dbMap = {
            "url": "jdbc:h2:/tmp/bam/test;AUTO_SERVER=TRUE",
            "username": "sa",
            "password": ""
        }

        const requestBody = {
            "sqlCommand" : query,
            "dbMap": dbMap,
            "comments": ""
        }

        const http = new XMLHttpRequest()
        const url = "http://127.0.0.1:8090/command"
        const method = "POST"

        http.open(method, url, true)
        http.setRequestHeader("Content-type", "application/json")

        http.onreadystatechange = () => {
            if ( http.status === 200 ) {
                dispatch(interpretResponse(JSON.parse(http.responseText)))
            }
        }

        http.send(JSON.stringify(requestBody))
    }
}
