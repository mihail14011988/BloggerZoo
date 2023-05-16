/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.service;

import java.security.Principal;
import miha.zika.DTO.PostDto;
import miha.zika.entity.UserBlogger;
import miha.zika.entity.UserPost;
import miha.zika.repo.ImageRepo;
import miha.zika.repo.PostRepo;
import miha.zika.repo.UserRepo;
import java.util.*;
import miha.zika.entity.ImageComm;
import miha.zika.mainException.PostNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    public final static Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;

    @Autowired
    public PostService(PostRepo postRepo, UserRepo userRepo, ImageRepo imageRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
    }

    public UserPost createdPost(PostDto dto, Principal principal) {
        UserBlogger blogger = getUserByPrincipal(principal);
        UserPost post = new UserPost();
        post.setUserblogger(blogger);
        post.setTitle(dto.getTitle());
        post.setLocation(dto.getLocation());
        post.setDescription(dto.getDescription());
        post.setLikes(0);

        LOG.info("Create new Post, user " + blogger.getEmail());
        return postRepo.save(post);
    }

    public List<UserPost> getPosts() {

        return postRepo.findAllByOrderByCreatedDateDesc();
    }
    public List<UserPost> getPostsForUser(Principal principal)
    {UserBlogger blogger = getUserByPrincipal(principal);
    return postRepo.findAllByuserbloggerOrderByCreatedDateDesc(blogger);
    
    }
    public UserPost likePost(Long id, String username){
    UserPost post = postRepo.findById(id).orElseThrow(()-> new PostNotFoundException("Post cannot not found"));
    Optional<String> userLiked = post.getUserLiked().stream().filter(u->u.equals(username)).findAny();
    if(userLiked.isPresent()){
    post.setLikes(post.getLikes()-1);
    post.getUserLiked().remove(username);
    } else post.setLikes(post.getLikes()+1);
    post.getUserLiked().add(username);
    return postRepo.save(post);
    }
    
    public void deletePost(Long id, Principal principal){
    UserPost post = getPostById(id, principal);
    Optional<ImageComm> imageComm = imageRepo.findByPostId(post.getId());
    postRepo.delete(post);
    imageComm.ifPresent(imageRepo::delete);
    
    }
    
    
    public UserPost getPostById(Long id,Principal principal){
    UserBlogger blogger = getUserByPrincipal(principal);
    return postRepo.findPostByIdAndUserblogger(id, blogger).orElseThrow(()-> new PostNotFoundException("Post cannot not found for user " +blogger.getEmail()));
    
    }

    private UserBlogger getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepo.findUserBloggerByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }

}
