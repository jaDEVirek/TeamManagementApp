import React, {Component} from 'react';
import TeamAsList from './TeamAsList.js';
import {DragDropContext} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';


{/*
   @author Wiktor Religo
 */
}

class Board extends Component {

    constructor() {
        super()
        this.state = {
            teamStore: [],
            unassignedPersons: [],
            assignedPersons: [],
        }
    }

    componentDidMount() {
        this.fetchTeamsFromBase();
        this.fetchPersonWithTeam();
        this.fetchPersonWithoutTeam();
    }

    fetchTeamsFromBase() {
        fetch('http://localhost:9090/teams')
            .then(response => response.json())
            .then(data => {
                this.setState({teamStore: data});
            });
    }

    fetchPersonWithTeam() {
        fetch('http://localhost:9090/peopel')
            .then(response => response.json())
            .then(data => {
                this.setState({assignedPersons: data});
            });
    }

    fetchPersonWithoutTeam() {
        fetch('http://localhost:9090/people/unassigned')
            .then(response => response.json())
            .then(data => {
                this.setState({unassignedPersons: data});
            });
    }

    render() {
        const style = {
            board: {
                display: "flex",
                justifyContent: "space-around",
                paddingTop: "20px"
            }
        };


        const listOne = [
            {id: 1, teamPosition: "Developer", personName: "Krystian Wolski"},
            {id: 2, teamPosition: "Tester", personName: "Ala Mala"},
            {id: 3, teamPosition: "Grafik", personName: " John Smith"},
            {id: 10, teamPosition: "UIX", personName: " John Smith2"},
            {id: 11, teamPosition: "Motywator", personName: " Tomek Baby"},
        ];

        const listTwo = [
            {id: 4, teamPosition: "Grafik", personName: "Olga Wojga"},
            {id: 5, teamPosition: "Developer", personName: "Tomek Wariat"},
            {id: 6, teamPosition: "Tester", personName: "Wiktor Byc"}
        ];

        const listThree = [
            {id: 7, teamPosition: "Tester", personName: "Maciek Wójcik"},
            {id: 8, teamPosition: "Developer", personName: "Wojtek Tkacz"},
            {id: 9, teamPosition: "Grafik", personName: "Bartek Bóbr"}
        ];

        return (
            <div style={style.board}>
                {this.state.teamStore.map(n => {
                    return (
                        <TeamAsList teamName={"Ninjas"} id={n.id} list={listOne}/>
                    )
                })
                }
                <TeamAsList teamName={"No Membered "} id={(this.state.teamStore.length + 1)} list={listOne}/>
            </div>
        );
    }
}

export default DragDropContext(HTML5Backend)(Board);
