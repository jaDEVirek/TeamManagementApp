import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import AddIcon from '@material-ui/icons/Add';
import {Delete, Save} from "@material-ui/icons";


{/*
   @author Wiktor Religo
*/
}


class DialogPanel extends React.Component {
    constructor() {
        super();
        this.state = {
            open: false,
            name: '',
            lastName: '',
            location: '',
            email: '',
            status: '',
            role: '',
        };
        this.handleClickOpen = () => {
            this.setState({
                open: true,
                name: '',
                lastName: '',
                location: '',
                email: '',
                status: '',
                role: '',
            });
        };
        this.handleClose = () => {
            this.setState({open: false});
            this.publish();
        };


    }


    handleChanges({target}) {
        this.setState(
            {
                [target.name]: target.value
            }
        )

    }

    publish() {
        this.addPersonToDatabase(this.dataTable2(this.state.name, this.state.lastname, this.state.location, this.state.email, this.state.status, this.state.role));
    }

    dataTable2(firstName, lastName, location, email, status, role) {
        return {firstName, lastName, location, email, status, role};
    }


    addPersonToDatabase(personProps) {
        fetch('http://localhost:9090/people',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*',
                    'mode': 'no-corse',
                },
                body: JSON.stringify(personProps)
            }).then(respData => this.props.onSave(respData)
        ).catch(err => console.error(err))
    }


    render() {
        return (
            <div style={{marginTop: '15px', float: 'left'}}>
                <Button variant="raised" color="primary" aria-label="add"
                        onClick={this.handleClickOpen}><AddIcon/></Button>
                <Dialog
                    open={this.state.open}
                    onClose={this.handleClose}
                    aria-labelledby="form-dialog-title"
                >
                    <DialogTitle id="form-dialog-title">Adding Person to table: </DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            Pleas fill data.
                        </DialogContentText>
                        <TextField
                            autoFocus
                            margin="dense"
                            name="name"
                            label="Name"
                            type="text"
                            fullWidth
                            value={this.state.name}
                            onChange={this.handleChanges.bind(this)}


                        />
                        <TextField
                            autoFocus
                            margin="dense"
                            name="lastname"
                            label="Lastname"
                            type="text"
                            fullWidth
                            value={this.state.lastname}
                            onChange={this.handleChanges.bind(this)}

                        />
                        <TextField
                            autoFocus
                            margin="dense"
                            name="location"
                            label="Location"
                            type="text"
                            fullWidth
                            value={this.state.location}
                            onChange={this.handleChanges.bind(this)}

                        />
                        <TextField
                            autoFocus
                            margin="dense"
                            name="email"
                            label="Email"
                            type="text"
                            fullWidth
                            value={this.state.email}
                            onChange={this.handleChanges.bind(this)}

                        />
                        <TextField
                            autoFocus
                            margin="dense"
                            name="status"
                            label="Status"
                            type="text"
                            fullWidth
                            value={this.state.status}
                            onChange={this.handleChanges.bind(this)}

                        />
                        <TextField
                            autoFocus
                            margin="dense"
                            name="role"
                            label="Role"
                            type="text"
                            fullWidth
                            value={this.state.role}
                            onChange={this.handleChanges.bind(this)}

                        />
                    </DialogContent>
                    <DialogActions>
                        <Button variant="raised" onClick={this.handleClose.bind(this)} color="secondary">
                            Cancel
                            <Delete/>
                        </Button>
                        <Button variant="raised" size="small" onClick={this.handleClose.bind(this)} color="primary">
                            Save
                            <Save/>
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}

export default DialogPanel;

