import React, { Component } from 'react';
import logo from './logo.svg';
import spinner from './spinner.svg';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="row">
          <div className="col-md-2 App-menu">
            <img src={spinner} className="App-logo" alt="logo" />
          </div>
          <div className="col-md-10 App-intro">
            Continue by editing <code>src/App.js</code>.
          </div>
      </div>
    );
  }
}

export default App;
