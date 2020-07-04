import React from 'react';
import {NavigationMenu} from "./TeamPage";
import Board from "dnd-component/Board";

{/*
   @author Wiktor Religo
 */
}

class Management extends React.Component {
    constructor() {
        super()
    }

    render() {
        return (
            <div>
                <NavigationMenu/>
                <Board/>
            </div>
        );
    }
}

export default Management;
