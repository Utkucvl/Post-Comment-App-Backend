package com.project.questapp.controllers;

import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.exceptoins.PostNotCreatedException;
import com.project.questapp.exceptoins.PostNotFoundException;
import com.project.questapp.exceptoins.PostNotUpdatedException;
import com.project.questapp.exceptoins.UserNotFoundException;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.PostResponse;
import com.project.questapp.services.PostService;
import com.project.questapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;
    private UserService userService;

    public PostController(PostService postService,UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }
    @GetMapping("/{postId}")
    public PostResponse getOnePost(@PathVariable Long postId){
        Post post = postService.getOnePostById(postId);
        if(post == null) {
            throw new PostNotFoundException();
        }
        return postService.getOnePostByIdWithLikes(postId);
    }
    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest){
        if(newPostRequest.getTitle().isEmpty() || newPostRequest.getText().isEmpty())
            throw new PostNotCreatedException();
        return postService.createOnePost(newPostRequest);
    }
    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId){
        Post post = postService.getOnePostById(postId);
        if(post == null)
            throw new PostNotFoundException();
         postService.deletePostById(postId);
    }
    @PutMapping("/{postId}")
    public Post updatePostById(@PathVariable Long postId , @RequestBody PostUpdateRequest updatePost){
        Post post = postService.getOnePostById(postId);
        if(post == null)
            throw new PostNotFoundException();
        if(updatePost.getText().isEmpty() || updatePost.getTitle().isEmpty())
            throw new PostNotUpdatedException();
        return postService.updatePostById(postId,updatePost);
    }
    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handlePostNotFoundExcetion() {

    }
    @ExceptionHandler(PostNotCreatedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handlePostNotCreatedException() {

    }
    @ExceptionHandler(PostNotUpdatedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handlePostNotUpdatedException() {

    }
}
