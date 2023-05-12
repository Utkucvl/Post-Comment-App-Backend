package com.project.questapp.services;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repository.CommentRepository;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public List<CommentResponse> getAllCommentsByParam(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> comments ;
        if(userId.isPresent() && postId.isPresent()){
            comments= commentRepository.findByUserIdAndPostId(userId,postId);
        }
        else if (userId.isPresent()){
            comments= commentRepository.findByUserId(userId);
        }
        else if (postId.isPresent()){
            comments= commentRepository.findByPostId(postId);
        }
        else {
            comments= commentRepository.findAll();
        }
        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }

    public Comment getOneComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest newComment) {
        User user = userService.getOneUserById(newComment.getUserId());
        Post post = postService.getOnePostById(newComment.getPostId());
        if(user!=null && post != null ){
            Comment toSave = new Comment();
            toSave.setUser(user);
            toSave.setPost(post);
            toSave.setText(newComment.getText());
            toSave.setId(newComment.getId());
            toSave.setCreateDate(new Date());
            return commentRepository.save(toSave);

        }
        else
            return null ;
    }


    public Comment updateOneComment(Long commentId, CommentUpdateRequest updateComment) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment!=null){
            comment.setText(updateComment.getText());
            return commentRepository.save(comment);
        }
        else
            return null;
    }

    public void deleteOneCommentById(Long commentId) {
         commentRepository.deleteById(commentId);
    }
}
