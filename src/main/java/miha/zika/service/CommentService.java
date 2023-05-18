/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import miha.zika.DTO.CommentDto;
import miha.zika.repo.CommentRepo;
import miha.zika.entity.Comment;
import miha.zika.entity.UserBlogger;
import miha.zika.entity.UserPost;
import miha.zika.mainException.PostNotFoundException;
import miha.zika.repo.PostRepo;
import miha.zika.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public final static Logger LOG = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepo commentRepo;
   private final PostRepo postRepo;
    private final UserRepo userRepo;
 

  @Autowired
    public CommentService(PostRepo postRepo, UserRepo userRepo, CommentRepo commentRepo) {
    this.postRepo = postRepo;
    this.userRepo = userRepo;
    this.commentRepo = commentRepo;
    }

    public Comment save(Long postId, CommentDto dto, Principal principal){
        UserBlogger blogerBlogger = getUserByPrincipal(principal);
            UserPost post = postRepo.findById(postId).orElseThrow(()-> new PostNotFoundException("Post not found"));
            Comment comment = new Comment();
            comment.setUserPost(post);
            comment.setUserId(blogerBlogger.getId());
            comment.setUserName(blogerBlogger.getUsername());
            comment.setMessage(dto.getMessage());
    LOG.info("Saving comment for Post " + post.getId());
    return commentRepo.save(comment);
    }
    
    public List<Comment> getAllComments(Long postId){
    UserPost post = postRepo.findById(postId).orElseThrow(()-> new PostNotFoundException("Post not found"));
    List <Comment> comments = commentRepo.findAllByuserPost(post);
     return comments;
    }
    
    public void deleteComment (Long commentId){
     Optional <Comment> comments = commentRepo.findById(commentId);
     comments.ifPresent(commentRepo::delete);
     }
    
    
    
      private UserBlogger getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepo.findUserBloggerByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }
}
