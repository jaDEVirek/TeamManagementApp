import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import {createData} from "./TeamTable";
import {Delete, Save} from "@material-ui/icons";

/*
   @author Wiktor Religo
*/


class TeamDialog extends React.Component {
    constructor() {
        super()
        this.state = {
            open: false,
            name: '',
            description: '',
            city: '',
            headcount: '',
        };
        this.handleClickOpen = () => {
            this.setState({
                open: true,
                name: '',
                description: '',
                city: '',
                headcount: '',
            });
        };

        this.handleClose = () => {
            this.setState({open: false});
        };
        this.handleCloseWithChanges = () => {
            this.publishData();
            this.setState({open: false});
        }
    }

    handleChanges({target}) {
        this.setState(
            {
                [target.name]: target.value
            }
        )
    }

    publishData() {
        this.addTeamToDatabase(createData(this.state.name, this.state.city, this.state.description, this.state.headcount));
    }

    addTeamToDatabase(teamProps) {
        fetch('http://localhost:9090/teams',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*',
                    'mode': 'no-corse',
                },
                body: JSON.stringify(teamProps)
            }).then(respData => this.props.onSave(respData)
        ).catch(err => console.error(err))
    }


    render() {
        return (
            <div style={{marginTop: '15px'}}>
                <Button variant="raised" color="secondary" aria-label="add" onClick={this.handleClickOpen}>Add
                    Team</Button>
                <Dialog
                    open={this.state.open}
                    onClose={this.handleClose}
                    aria-labelledby="form-dialog-title"
                >
                    <DialogTitle id="form-dialog-title">Adding Team to table: </DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            Please fill the form with correct data about your Team.
                        </DialogContentText>
                        <TextField
                            autoFocus
                            margin="dense"
                            name="name"
                            label="Name of the team"
                            type="text"
                            fullWidth
                            value={this.state.name}
                            onChange={this.handleChanges.bind(this)}
                        />
                        <TextField
                            autoFocus
                            margin="dense"
                            name="city"
                            label="City"
                            type="text"
                            value={this.state.city}
                            onChange={this.handleChanges.bind(this)}
                            fullWidth
                        />
                        <TextField
                            autoFocus
                            margin="dense"
                            name="description"
                            label="Description"
                            type="text"
                            onChange={this.handleChanges.bind(this)}
                            value={this.state.description}
                            fullWidth
                        />
                        <TextField
                            autoFocus
                            margin="dense"
                            name="headcount"
                            label="Headcount"
                            type="number"
                            value={this.state.headcount}
                            onChange={this.handleChanges.bind(this)}
                            fullWidth
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button variant="raised" color="secondary" onClick={this.handleClose} color="secondary">
                            Cancel
                            <Delete/>
                        </Button>
                        <Button variant="raised" size="small" onClick={this.handleCloseWithChanges} color="primary">
                            Save
                            <Save/>
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}

export default TeamDialog;
