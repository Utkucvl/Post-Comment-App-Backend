package com.project.questapp.controllers;

import com.project.questapp.entities.Comment;
import com.project.questapp.exceptoins.CommentNotCreatedException;
import com.project.questapp.exceptoins.CommentNotFoundException;
import com.project.questapp.exceptoins.UserCanNotBeCreated;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;
import com.project.questapp.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping
    public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId , @RequestParam Optional<Long> postId){
        return commentService.getAllCommentsByParam(userId,postId);
    }
    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        Comment comment = commentService.getOneComment(commentId);
        if(comment == null)
            throw new CommentNotFoundException();
        return commentService.getOneComment(commentId);
    }
    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest newComment){
        if(newComment.getText().isEmpty())
            throw new CommentNotCreatedException();
        return commentService.createOneComment(newComment);
    }
    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId , @RequestBody CommentUpdateRequest updateComment){
        Comment comment = commentService.getOneComment(commentId);
        if(comment == null)
            throw new CommentNotFoundException();
        if(updateComment.getText().isEmpty())
            throw new CommentNotCreatedException();
        return commentService.updateOneComment(commentId,updateComment);
    }
    @DeleteMapping("/{commentId}")
    public void deleteOneCommentById(@PathVariable Long commentId){
        Comment comment = commentService.getOneComment(commentId);
        if(comment == null)
            throw new CommentNotFoundException();
         commentService.deleteOneCommentById(commentId);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    private void handleCommentNotFoundException() {

    }
    @ExceptionHandler(CommentNotCreatedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    private void handleCommentNotCreatedException() {

    }



}
