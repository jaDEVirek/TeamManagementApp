import React from 'react';
import ReactDOM from 'react-dom';


import TeamPage from "./TeamPage.js";
import Management from "./Management.js";
import {HashRouter as Router, Route, Switch} from 'react-router-dom'
import Home from "./Home.jsx"
import Persons from "./Persons.jsx";

/*
   @author Wiktor Religo
*/

ReactDOM.render(
    <Router>
        <Switch>
            <Route exact path="/" component={Home}/>
            <Route path="/teams" component={TeamPage}/>
            <Route path="/persons" component={Persons}/>
            <Route path="/manage" component={
                Management}/>
        </Switch>
    </Router>
    , document.getElementById('app'));

