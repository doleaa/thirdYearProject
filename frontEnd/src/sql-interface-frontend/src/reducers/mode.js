const mode = (state = "EXECUTOR", action) => {
    switch (action.type) {
        case 'SET_MODE':
            return action.mode
        default:
            return state
    }
}

export default mode