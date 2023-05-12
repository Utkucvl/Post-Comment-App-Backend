package com.project.questapp.responses;

import com.project.questapp.entities.Like;
import lombok.Data;

@Data
public class LikeResponses {
        Long id ;
        Long userId;
        Long postId;
        public LikeResponses(Like entity){
            this.id=entity.getId();
            this.userId=entity.getUser().getId();
            this.postId=entity.getPost().getId();

        }
}
