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
                        data: response
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
        case "CLEAR_RESPONSE_DATA":
            return Object.assign({}, state, {
                result: {
                    data: {}
                }
            })
        default:
            return state
    }
}

export default execution