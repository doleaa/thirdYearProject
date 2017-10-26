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

        const dbMap = {}

        const requestBody = {
            sqlCommand: query,
            dbMap
        }
        dispatch(interpretResponse({status: 200, text: () => (null)}))
    }
}