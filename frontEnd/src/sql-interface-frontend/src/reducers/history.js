const mockedExecutions = [
    {
        id: 1,
        comments: "some comments, not many though",
        date: "20-11-2017",
        query: "create table book;",
        isInPreview: true
    },
    {
            id: 2,
            comments: "some other comments, but still not too many",
            date: "20-11-2017",
            query: "drop table table book;",
            isInPreview: true
    }
]

const initialState = {
    executionsList: mockedExecutions
}

const history = ( state = initialState, action ) => {
    switch (action.type) {
        case "CHANGE_PREVIEW_STATE":
            const executionId = action.executionId

            const newExecutionList = state.executionsList.map((execution) => {
                if (execution.id === executionId) {
                    execution.isInPreview = !execution.isInPreview
                    return execution
                } else {
                    return execution
                }
            })

            return {
                executionsList: newExecutionList
            }
        default:
            return state
    }
}

export default history