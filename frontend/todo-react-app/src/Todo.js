import React from 'react';
import {ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction, IconButton} from "@material-ui/core";
import DeleteOutlined from "@material-ui/icons/DeleteOutlined";

class Todo extends React.Component{
    constructor(props){
        super(props);
        this.state = {item: props.item };
    }

    render(){
        const item = this.state.item;
        return (
            <ListItem>
                <Checkbox checked={item.done} disableRipple />
                <ListItemText>
                    <InputBase
                        inputProps={{"aria-label" : "naked"}}
                        type="text"
                        id={item.id} // 각 리스트를 연결하려고 id를 연결
                        name={item.id} // 각 리스트를 구분하려고 id를 연결
                        value={item.title}
                        multiline={true}
                        fullWidth={true}
                        />
                </ListItemText>

                <ListItemSecondaryAction>
                    <IconButton aria-label="Delete Todo">
                        <DeleteOutlined  />
                    </IconButton>
                </ListItemSecondaryAction>
            </ListItem> 
        );
    } 
}

export default Todo;