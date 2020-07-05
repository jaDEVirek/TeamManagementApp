import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import {spacing} from "@material-ui/system";


/*
   @author Wiktor Religo
 */


const tableStyle = {
    root: {
        width: '100%',
        marginTop: spacing * 3,
        overflowX: 'auto',
    },
    table: {
        minWidth: 700,
    },
    head: {
        fontSize: '20px',
    },
    innerRow: {
        fontSize: '15',
        color: 'black',
        textAlign: 'left',
    },
    deleted: {
        textAlign: 'center',
        fontSize: '20px',
    },
    innerLastRow: {
        textAlign: 'center',
    },
    deleteIcon: {
        backgroundColor: '#1e90ff',
        border: 'none',
        color: '#f8f8ff',
        padding: '10px',
        fontSize: '15px',
        cursor: 'pointer',
    },
    anIcon: {
        marginLeft: '10px',
    }
};

let id = 0;

export function createData(name, city, description, headcount) {
    id += 1;
    return {id, name, city, description, headcount};
}

class TeamTable extends React.Component {
    constructor() {
        super();
        this.state = {
            teamData: []
        }
    }

    updateTableWithNewData() {
        this.fetchDataToTable()
    };

    componentDidMount() {
        this.props.onRef(this);
        this.fetchDataToTable();
    }

    fetchDataToTable() {
        fetch('http://localhost:9090/teams')
            .then(response => response.json())
            .then(data => {
                this.setState({teamData: data});
            });
    }

    deleteTeamFromBase(teamId) {
        fetch('http://localhost:9090/teams/' + teamId,
            {method: 'DELETE',})
            .then(
                res => this.fetchDataToTable()
            )
            .catch(err => console.error(err))
    }

    render() {
        return (
            <div>
                <Paper style={tableStyle.root}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell style={tableStyle.head}>ID:</TableCell>
                                <TableCell style={tableStyle.head}> Name:</TableCell>
                                <TableCell style={tableStyle.head}>City:</TableCell>
                                <TableCell style={tableStyle.head}>Description: </TableCell>
                                <TableCell style={tableStyle.head}>Headcount:</TableCell>
                                <TableCell style={tableStyle.deleted}>Delete</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.teamData.map(n => {
                                return (
                                    <TableRow key={n.id}>
                                        <TableCell style={tableStyle.innerRow} component="th" scope="row">
                                            {n.id}
                                        </TableCell>
                                        <TableCell numeric style={tableStyle.innerRow}>{n.name}</TableCell>
                                        <TableCell numeric style={tableStyle.innerRow}>{n.city}</TableCell>
                                        <TableCell numeric style={tableStyle.innerRow}>{n.description}</TableCell>
                                        <TableCell numeric style={tableStyle.innerRow}>{n.headcount}</TableCell>
                                        <TableCell numeric style={tableStyle.innerLastRow}>
                                            <button onClick={e => {
                                                this.deleteTeamFromBase(n.id);
                                            }} style={tableStyle.deleteIcon}>Delete <i className="fa fa-trash"
                                                                                       style={tableStyle.anIcon}
                                            ></i>
                                            </button>
                                        </TableCell>
                                    </TableRow>
                                )
                            })}
                        </TableBody>
                    </Table>
                </Paper>
            </div>
        );
    }
}

export default withStyles(tableStyle)(TeamTable);
