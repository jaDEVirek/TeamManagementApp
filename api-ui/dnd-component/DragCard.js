import React, {Component} from 'react';
import {findDOMNode} from 'react-dom';
import {DragSource, DropTarget} from 'react-dnd';
import flow from 'lodash/flow';
import Card from "./Card.jsx";

{/*
   @author Wiktor Religo
 */
}


class DragCard extends Component {

    render() {
        const {teamNm, card, isDragging, connectDragSource, connectDropTarget} = this.props;

        const style = {
            content: {
                padding: '0.5rem 1rem',
                margin: '.5rem',
                cursor: 'move',
                opacity: isDragging ? 0 : 1,
            }
        };

        return connectDragSource(connectDropTarget(
            <div style={style.content}>
                <Card name={card.personName}
                      position={card.teamPosition} teamName={teamNm}/>
            </div>
        ));
    }
}

// setting behaviours to card
export const cardSource = {
    beginDrag(props) {
        return {
            index: props.index,
            listId: props.listId,
            card: props.card
        };
    },
    endDrag(props, monitor) {
        const item = monitor.getItem();
        const dropResult = monitor.getDropResult();

        if (dropResult && dropResult.listId !== item.listId) {
            props.removeCard(item.index);
        }
    }
};


export const cardTarget = {
    hover(props, monitor, component) {
        const dragIndex = monitor.getItem().index;
        const hoverIndex = props.index;
        const sourceListId = monitor.getItem().listId;

        // Don't replace items with themselves
        if (dragIndex === hoverIndex) {
            return;
        }
        // Determine rectangle on screen
        const hoverBoundingRect = findDOMNode(component).getBoundingClientRect();

        const hoverMiddleY = (hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;

        // Determine mouse position
        const clientOffset = monitor.getClientOffset();


        // Get pixels to the top
        const hoverClientY = clientOffset.y - hoverBoundingRect.top;
        // Only perform the move when the mouse has crossed half of the items height
        // When dragging downwards, only move when the cursor is below 50%
        // When dragging upwards, only move when the cursor is above 50%

        // Dragging downwards
        if (dragIndex < hoverIndex && hoverClientY < hoverMiddleY) {
            return;
        }

        // Dragging upwards
        if (dragIndex > hoverIndex && hoverClientY > hoverMiddleY) {
            return;
        }

        // Time to actually perform the action
        if (props.listId === sourceListId) {
            props.moveCard(dragIndex, hoverIndex);
            // Note: we're mutating the monitor item here!
            // Generally it's better to avoid mutations,
            // but it's good here for the sake of performance
            // to avoid expensive index searches.
            monitor.getItem().index = hoverIndex;
        }
    }
};
export default flow(
    DropTarget("DragCard", cardTarget, connect => ({
        connectDropTarget: connect.dropTarget()
    })),
    DragSource("DragCard", cardSource, (connect, monitor) => ({
        connectDragSource: connect.dragSource(),
        isDragging: monitor.isDragging()
    }))
)(DragCard);



