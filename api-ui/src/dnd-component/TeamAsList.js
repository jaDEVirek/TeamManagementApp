import React, {Component} from 'react';
import {DropTarget} from 'react-dnd';
import DragCard from "./DragCard";

const update = require('immutability-helper')

/*
   @author Wiktor Religo
 */


export class TeamAsList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            cards: props.list,
        };
    }

    pushCard(card) {
        this.setState(update(this.state, {
            cards: {
                $push: [card]
            }
        }));
    }

    removeCard(index) {
        this.setState(update(this.state, {
            cards: {
                $splice: [
                    [index, 1]
                ]
            }
        }));
    }

    moveCard(dragIndex, hoverIndex) {
        const {cards} = this.state;
        const dragCard = cards[dragIndex];

        this.setState(update(this.state, {
            cards: {
                $splice: [
                    [dragIndex, 1],
                    [hoverIndex, 0, dragCard]
                ]
            }
        }));
    }

    render() {
        const {cards} = this.state;
        const {canDrop, isOver, connectDropTarget} = this.props;
        const isActive = canDrop && isOver;
        const style = {
            list: {
                margin: '15px',
                width: '390px',
                minHeight: '900px',
                backgroundColor: isActive ? 'lightgreen' : '#fafafa',
                overflowY: 'auto',
                boxShadow: '0px 2px 4px -1px rgba(0, 0, 0, 0.2), 0px 4px 5px 0px rgba(0, 0, 0, 0.14), 0px 1px 10px 0px rgba(0, 0, 0, 0.12)',
                fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif'
            }
        };


        return connectDropTarget(
            <div style={style.list}>
                <h1>Team: <span style={{
                    color: 'red',
                    fontSize: "23px",
                    padding: 5,
                    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif'
                }}>{this.props.teamName}</span></h1>
                {cards.map((card, i) => {
                    return (
                        <DragCard
                            key={card.id}
                            index={i}
                            teamNm={this.props.teamName}
                            listId={this.props.id}
                            card={card}
                            removeCard={this.removeCard.bind(this)}
                            moveCard={this.moveCard.bind(this)}
                            isDragging={true}/>
                    );
                })}
            </div>
        );
    }

}

// wrap class to objects
const cardTarget = {
    drop(props, monitor, component) {
        const {id} = props;
        const sourceObj = monitor.getItem();
        if (id !== sourceObj.listId) component.pushCard(sourceObj.card);
        return {
            listId: id
        };
    }
};

export default DropTarget("DragCard", cardTarget, (connect, monitor) => ({
    connectDropTarget: connect.dropTarget(),
    isOver: monitor.isOver(),
    canDrop: monitor.canDrop()
}))(TeamAsList);
