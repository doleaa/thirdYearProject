const initialState = {
    executionsList: [],
    mode: "VIEW",
    scriptForm: {},
    scriptsList: []
}

const newElement = {
    position: "",
    comment: {},
    execution: {}
}

const history = ( state = initialState, action ) => {
    switch (action.type) {
        case "SET_INITIAL_EXECUTION_LIST_STATE":
            return initialState

        case "SET_EXECUTION_LIST_MODE":
            return Object.assign({}, state, {
                mode: action.mode
            })

        case "CHANGE_EXECUTION_PREVIEW_STATE":
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

        case "SET_SCRIPT_FORM_DATA":
            return Object.assign({}, state, {
                scriptForm: {
                    title: action.title,
                    header: action.header,
                    elementList: action.elementList
                }
            })

        case "DELETE_SCRIPT_FORM_DATA":
            return Object.assign({}, state, {scriptForm: {}})

        case "UPDATE_SCRIPT_FORM_TITLE":
            return Object.assign({}, state, {
                scriptForm: {
                    title: action.title,
                    header: state.scriptForm.header,
                    elementList: state.scriptForm.elementList
                }
            })

        case "UPDATE_SCRIPT_FORM_HEADER":
            return Object.assign({}, state, {
                scriptForm: {
                    title: state.scriptForm.title,
                    header: action.header,
                    elementList: state.scriptForm.elementList
                }
            })

        case "MOVE_SCRIPT_FORM_ELEMENT_FROM_TO":
            const scriptFormElementList = state.scriptForm.elementList
            const elementCopy = scriptFormElementList[action.from]
            scriptFormElementList.splice(action.from, 1)
            scriptFormElementList.splice(action.to, 0, elementCopy)
            return Object.assign({}, state, {
               scriptForm: {
                   title: state.scriptForm.title,
                   header: state.scriptForm.header,
                   elementList: scriptFormElementList
               }
            })

        case "START_MOVING_SCRIPT_FORM_ELEMENT_FROM":
            return Object.assign({}, state, {
              scriptForm: {
                  title: state.scriptForm.title,
                  header: state.scriptForm.header,
                  elementList: state.scriptForm.elementList,
                  movingIndex: action.index
              }
           })

        case "ADD_SCRIPT_FORM_COMMENT_ELEMENT_UNDER":
            const currentScriptFormElementList = state.scriptForm.elementList
            const newCommentElement = {comment: {text: "", editing: false}}
            currentScriptFormElementList.splice(action.under+1, 0, newCommentElement)
            return Object.assign({}, state, {
               scriptForm: {
                   title: state.scriptForm.title,
                   header: state.scriptForm.header,
                   elementList: currentScriptFormElementList
               }
            })

        case "UPDATE_SCRIPT_FORM_COMMENT_ELEMENT_TEXT":
            return Object.assign({}, state, {
                scriptForm: {
                    title: state.scriptForm.title,
                    header: state.scriptForm.header,
                    elementList: state.scriptForm.elementList.map((item, index) => {
                        if (index === action.index) {
                            item.comment.text = action.text
                            return item
                        }
                        return item
                    })
                }
            })


        case "REMOVE_SCRIPT_FORM_ELEMENT":
                const list = state.scriptForm.elementList
                list.splice(action.index, 1)
            return Object.assign({}, state, {
                scriptForm: {
                    title: state.scriptForm.title,
                    header: state.scriptForm.header,
                    elementList: list
                }
            })


        case "START_EDITING_SCRIPT_FORM_COMMENT_ELEMENT_TEXT":
            return Object.assign({}, state, {
                scriptForm: {
                    title: state.scriptForm.title,
                    header: state.scriptForm.header,
                    elementList: state.scriptForm.elementList.map((item, index) => {
                        if (index === action.index) {
                            item.comment.editing = true
                            return item
                        }
                        return item
                    })
                }
            })


        case "STOP_EDITING_SCRIPT_FORM_COMMENT_ELEMENT_TEXT":
            return Object.assign({}, state, {
                scriptForm: {
                    title: state.scriptForm.title,
                    header: state.scriptForm.header,
                    elementList: state.scriptForm.elementList.map((item, index) => {
                        if (index === action.index) {
                            item.comment.editing = false
                            return item
                        }
                        return item
                    })
                }
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
                            execution.comment = action.comment
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
                            if (execution.comment) {
                                execution.comment.text = action.comments
                            } else {
                                execution.comment = {}
                                execution.comment.text = action.comments
                            }
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