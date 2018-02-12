const initialState = {
    query: "",
    loading: false,
    result: {}
}

const execution = (state = initialState, action) => {
    switch (action.type) {
        case "SET_QUERY":
            return Object.assign({}, state, {
                query: action.query
            })
        case "INITIATE_EXECUTION":
            return Object.assign({}, state, {
                loading: true
            })
        case "INTERPRET_RESPONSE":
            const { response } = action
            if (response.status === 200) {
                return Object.assign({}, state, {
                    loading: false,
                    result: {
                        ok: true,
                        data: JSON.parse(response.responseText)
                    }
                })
            } else {
                return Object.assign({}, state, {
                    loading: false,
                    result: {
                        ok: false
                    }
                })
            }
        case "HIDE_ERROR_RESPONSE":
            return Object.assign({}, state, {
                result: {
                    ok: true
                }
            })
        default:
            return state
    }
}

export default execution