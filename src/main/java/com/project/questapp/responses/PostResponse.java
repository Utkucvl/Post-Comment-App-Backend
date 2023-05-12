package com.project.questapp.responses;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    Long id;
    Long userId;
    String userName;
    String title;
    String text;
    List<LikeResponses> postLikes;

    public PostResponse(Post entity ,List<LikeResponses> postLikess ){
        this.id=entity.getId();
        this.userId=entity.getUser().getId();
        this.userName=entity.getUser().getUserName();
        this.title=entity.getTitle();
        this.text=entity.getText();
        this.postLikes=postLikess;
    }
}
