const mockedExecutions = [
    {
        id: 1,
        comments: "some comments, not many though",
        date: "20-11-2017",
        query: "create table book;",
        isInPreview: true,
        isLoading: false
    },
    {
        id: 2,
        comments: "some other comments, but still not too many",
        date: "20-11-2017",
        query: "drop table table book;",
        isInPreview: true,
        isLoading: false
    },
    {
        id: 3,
        comments: "some other comments, but still not too many",
        date: "20-11-2017",
        query: "drop table table book;",
        isInPreview: true,
        isLoading: false
    }
]

const initialState = {
    executionsList: [],
    mode: "VIEW"
}

const history = ( state = initialState, action ) => {
    switch (action.type) {
        case "SET_INITIAL_EXECUTION_LIST_STATE":
            return initialState
        case "SET_EXECUTION_MODE":
            return Object.assign({}, state, {
                mode: action.mode
            })
        case "CHANGE_PREVIEW_STATE":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.executionId) {
                            execution.isInPreview = !execution.isInPreview
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "START_EXECUTION_LOADING":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.executionId) {
                            execution.isLoading = true
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "STOP_EXECUTION_LOADING":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.executionId) {
                            execution.isLoading = false
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "START_EDITING_EXECUTION":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.executionId) {
                            execution.editing = true
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "STOP_EDITING_EXECUTION":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.executionId) {
                            execution.editing = false
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "ADD_REPORT_NOTE_TO_EXECUTION":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.executionId) {
                            execution.reportNote = ""
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "DELETE_REPORT_NOTE_FROM_EXECUTION":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.executionId) {
                            execution.reportNote = undefined
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "UNSELECT_ALL_EXECUTIONS":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.selected) {
                            execution.selected = false
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "UNSELECT_EXECUTION":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.executionId) {
                            execution.selected = false
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "SELECT_EXECUTION":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.executionId) {
                            execution.selected = true
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "UPDATE_EXECUTION_COMMENTS":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.id) {
                            execution.comments = action.comments
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "UPDATE_EXECUTION_REPORT_NOTE":
            return Object.assign({}, state, {
                executionsList:
                    state.executionsList.map((execution) => {
                        if (execution.id === action.id) {
                            execution.reportNote = action.reportNote
                            return execution
                        } else {
                            return execution
                        }
                    })
            })
        case "MAP_RESPONSE_LIST":
            return Object.assign({}, state, {
                executionsList: action.executionsList
            })
        default:
            return state
    }
}

export default history