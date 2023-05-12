package com.project.questapp.controllers;

import com.project.questapp.entities.Like;
import com.project.questapp.exceptoins.LikeNotFoundException;
import com.project.questapp.exceptoins.UserCanNotBeCreated;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponses;
import com.project.questapp.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponses> getAllLikes(@RequestParam Optional<Long> postId , @RequestParam Optional<Long> userId){
        return likeService.getAllLikes(postId,userId);
    }
    @GetMapping("/{likeId}")
    public Like getOneLike(@PathVariable Long likeId){
        Like like = likeService.getOneLike(likeId);
        if(like == null)
            throw new LikeNotFoundException();
        return likeService.getOneLike(likeId);
    }
    @PostMapping
    public Like createOneLike(@RequestBody LikeCreateRequest createLike){
        return likeService.createOneLike(createLike);
    }
    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId){
        Like like = likeService.getOneLike(likeId);
        if(like == null)
            throw new LikeNotFoundException();
        likeService.deleteLike(likeId);
    }


    @ExceptionHandler(LikeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    private void handleLikeNotFoundException() {

    }

}
