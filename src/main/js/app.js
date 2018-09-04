// 'use strict';
import React from 'react'

// const React = require('react');
const ReactDOM = require('react-dom');

import {
    HashRouter as Router,
    Route,
    Link
} from 'react-router-dom'
import Home from "./home";
import Test from "./test";

class App extends React.Component {

	render() {
		return (
            <Router>
                <div>
                    <Route exact path="/" component={Home}/>
                    <Route path="/test" component={Test}/>
                </div>
            </Router>
        )
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
);
