package com.project.questapp.services;

import com.project.questapp.entities.User;
import com.project.questapp.repository.CommentRepository;
import com.project.questapp.repository.LikeRepository;
import com.project.questapp.repository.PostRepository;
import com.project.questapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private LikeRepository likeRepository;
    private CommentRepository commentRepository;
    private PostRepository postRepository;


    public UserService(UserRepository userRepository , LikeRepository likeRepository , CommentRepository commentRepository, PostRepository postRepository ) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundedUser = user.get();
            if(newUser.getUserName() != null  && newUser.getPassword()!= null){
                foundedUser.setUserName(newUser.getUserName());
                foundedUser.setPassword(newUser.getPassword());
                foundedUser.setAvatar(newUser.getAvatar());
            }
            else if (newUser.getUserName() == null && newUser.getPassword() != null){
                foundedUser.setPassword(newUser.getPassword());
                foundedUser.setAvatar(newUser.getAvatar());
            }
            else if (newUser.getUserName() == null && newUser.getPassword() == null){
                foundedUser.setAvatar(newUser.getAvatar());
            }

            userRepository.save(foundedUser);
            return foundedUser;

        }
        else {
            return null;
        }
    }

    public void deleteOneUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName) {
        return (userRepository.findByUserName(userName));
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if(postIds.isEmpty())
            return null;
        List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
        List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
    }
}
