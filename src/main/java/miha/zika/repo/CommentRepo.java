/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package miha.zika.repo;

import java.util.*;
import miha.zika.entity.Comment;
import miha.zika.entity.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long>{
List<Comment> findAllByuserPost(UserPost user);
Comment findByIdAndUserId(Long commentId, Long userId);
}
