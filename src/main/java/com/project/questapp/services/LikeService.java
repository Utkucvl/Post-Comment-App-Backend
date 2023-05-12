package com.project.questapp.services;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repository.LikeRepository;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponses;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;
    private CommentService commentService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService, CommentService commentService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }


    public List<LikeResponses> getAllLikes(Optional<Long> userId,Optional<Long> postId) {
        List<Like> list;
        if(postId.isPresent() && userId.isPresent()){
            list= likeRepository.findByPostIdAndUserId(postId,userId);
        }
        else if (postId.isPresent()){
            list= likeRepository.findByPostId(postId);
        }
        else if (userId.isPresent()){
            list= likeRepository.findByUserId(userId);
        }
        else {
            list= likeRepository.findAll();
        }
        return list.stream().map(like -> new LikeResponses(like)).collect(Collectors.toList());
    }

    public Like getOneLike(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest createLike) {
        User user = userService.getOneUserById(createLike.getUserId());
        Post post = postService.getOnePostById(createLike.getPostId());
        if(user!=null && post!= null ){
            Like newLike = new Like();
            newLike.setId(createLike.getId());
            newLike.setUser(user);
            newLike.setPost(post);
            return likeRepository.save(newLike);
        }
        else
            return null;

    }

    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
