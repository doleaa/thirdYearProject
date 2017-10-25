import React from 'react'
import './Executor.css'

const Executor = () => (
    <div className="col-md-10 ContainerView">
        <div className='row'>
            <div className="col-md-12">
                <textarea
                    rows="10"
                />
            </div>
            <div className="col-md-12">
                <button
                    type="button"
                    class="btn btn-primary"
                >
                    Execute
                </button>
            </div>
        </div>
        EXECUTOR. Continue by editing <code>src/App.js</code>.
    </div>
)

export default Executor