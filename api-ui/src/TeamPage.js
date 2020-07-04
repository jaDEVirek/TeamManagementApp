import React from 'react';
import TeamTable from './TeamTable.js';
import {AppBar} from '@material-ui/core/';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Toolbar from '@material-ui/core/Toolbar';
import TeamDialog from "./TeamDialog";
import {Link,} from 'react-router-dom';
import {BreakpointsOptions as spacing} from "@material-ui/core/styles/createBreakpoints";

{/*
   @author Wiktor Religo
 */
}

class TeamPage extends React.Component {

    constructor() {
        super();
        this.teamAddedCallback = () => {
            this.tableRef.updateTableWithNewData();
        }
    }

    render() {
        return (
            <div style={{textAlign: 'left'}}>
                <NavigationMenu/>
                <TeamTable onRef={ref => {
                    (this.tableRef = ref);
                }}/>
                <TeamDialog onSave={this.teamAddedCallback}/>
            </div>
        );
    }
}

export const NavigationMenu = () => {
    return (
        <div className={pageStyle.root}>
            <AppBar position="static">
                <Toolbar>
                    <IconButton className={pageStyle.menuButton} color="inherit" aria-label="Menu">
                        <MenuIcon/>
                    </IconButton>
                    <Link style={pageStyle.link} to="/">
                        <Typography variant="title" color="inherit" className={pageStyle.flex}>
                            Back to Home
                        </Typography>
                    </Link>

                    <Button color="inherit"
                            style={{
                                marginLeft: '50px',
                                letterSpacing: 1.5,
                                border: '1.5px solid #00BFFF'
                            }}>
                        <Link style={pageStyle.link} to="/manage">Manage</Link></Button>
                    <Link style={pageStyle.link} to="/teams">
                        <Button color="inherit"
                                style={{marginLeft: '50px', backgroundColor: 'rgb(220, 20, 60,0.8)', letterSpacing: 1.5}}> show
                            show TeamTable </Button>
                    </Link>
                    <Link style={pageStyle.link} to="/persons">
                        <Button color="inherit"
                                style={{marginLeft: '50px', backgroundColor: 'rgb(220, 20, 60,0.8)', letterSpacing: 1.5}}> show
                            PersonTable </Button>
                    </Link>
                </Toolbar>
            </AppBar>
        </div>
    )
        ;
}

export const pageStyle = {
    root: {
        flexGrow: 1,
        padding: '15px',
    },
    flex: {
        flex: 1,
        marginRight: '15px',
    },
    menuButton: {
        float: 'right',
        marginLeft: -12,
        marginRight: 20,
    },
    button: {
        margin: spacing.unit,
    },
    link: {
        textDecoration: 'none',
        color: 'white',
        fontWeight: 'bold',
    }
};
export default TeamPage;
