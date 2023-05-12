package com.project.questapp.responses;

import com.project.questapp.entities.Comment;
import lombok.Data;

@Data
public class CommentResponse {
    Long id ;
    String userName;
    Long userId;
    String text;
    public CommentResponse(Comment entity){
        this.text= entity.getText();
        this.id= entity.getId();
        this.userName=entity.getUser().getUserName();
        this.userId=entity.getUser().getId();
    }
}
