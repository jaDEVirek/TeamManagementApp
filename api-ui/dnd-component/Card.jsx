import React from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Avatar from "./Avatar";
import IconButton from "@material-ui/core/es/IconButton/IconButton";
import FavoriteIcon from '@material-ui/icons/Favorite';
import AssignmentIcon from '@material-ui/icons/Assignment';
import green from "@material-ui/core/es/colors/green";
import pink from "@material-ui/core/es/colors/pink";
import PageviewIcon from '@material-ui/icons/Pageview';
import Popup from "reactjs-popup";


{/*
   @author Wiktor Religo
 */
}
const styles = theme => ({
    card: {
        display: 'flex',
        backgroundColor: '#F8F8FF',
    },
    details: {
        display: 'flex',
        flexDirection: 'column',
    },
    content: {
        flex: '1 0 auto',
        paddingBottom: 5,
    },
    greenAvatar: {
        margin: 10,
        color: '#fff',
        backgroundColor: green[500],
    },
    pinkAvatar: {
        margin: 10,
        color: '#fff',
        backgroundColor: pink[500],
    },
});

function TeamCard(props) {
    const {teamName, name, position, classes} = props;

    return (
        <div
            style={{boxShadow: '0px 1px 5px 0px rgba(0, 0, 0, 0.2), 0px 2px 2px 0px rgba(0, 0, 0, 0.14), 0px 3px 1px -2px rgba(0, 0, 0, 0.12)',}}>
            <Card className={classes.card}>
                <Avatar teamName={teamName}/>
                <div className={classes.details}>
                    <CardContent className={classes.content}>
                        <Typography variant="headline">
                            {name}
                        </Typography>
                        <Typography variant="subheading" color="textSecondary">
                            {position}
                        </Typography>
                    </CardContent>
                    <div style={{float: 'right', marginLeft: 55, padding: 3}}>
                        <IconButton aria-label="Add to favorites">
                            <FavoriteIcon color='secondary'/>
                        </IconButton>
                        <Popup
                            trigger={<IconButton>
                                <AssignmentIcon className={classes.greenAvatar}/>
                            </IconButton>}
                            position="right bottom"
                            on="hover"
                        >
                            <PopContext args={name}
                                        args2={position}/>
                        </Popup>
                        <IconButton>
                            <PageviewIcon className={classes.pinkAvatar}/>
                        </IconButton>
                    </div>
                </div>
            </Card>
        </div>
    );
}

const PopContext = ({args, args2}) => (
    <div>
        <div style={PopStyles.header}> {args} </div>
        <div style={PopStyles.popContent}>
            Tu znajdują się informacje o członku <br/>

            <span style={PopStyles.span}>Staż pracy:</span>pracy: miesięcy <br/>
            <span style={PopStyles.span}>Rola:</span> {args2} <br/>
            <span style={PopStyles.span}>Miejsce:</span> Kraków
        </div>
    </div>
);


const PopStyles = {
    header: {
        width: '100%',
        borderBottom: '1px solid gray',
        fontSize: 19,
        textAlign: 'center',
        padding: 5,
    },
    popContent: {
        fontSize: 16,
        width: '100%',
        padding: '7px 3px',
    },
    span: {
        color: 'red',
        fontWeight: 'bold',
    }

}

TeamCard.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
    name: PropTypes.object.isRequired,
    position: PropTypes.object.isRequired,
    teamName: PropTypes.object.isRequired,
};


export default withStyles(styles, {withTheme: true})(TeamCard);
