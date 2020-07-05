import React from 'react';
import {NavigationMenu} from "./TeamPage.js";
import HomeIcon from '@material-ui/icons/Home';

/*
   @author Wiktor Religo
 */


class Home extends React.Component {
    constructor() {
        super()
    }

    render() {
        return (
            <div>
                <NavigationMenu/>
                <HomeIcon color="error" style={{fontSize: 380, paddingTop: 150}}/>
            </div>
        )
    }
}

export default Home;
