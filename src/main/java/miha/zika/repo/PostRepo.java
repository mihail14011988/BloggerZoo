/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package miha.zika.repo;

import java.util.List;
import java.util.Optional;
import miha.zika.entity.UserBlogger;
import miha.zika.entity.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<UserPost, Long>{
    List<UserPost> findAllByuserbloggerOrderByCreatedDateDesc (UserBlogger blogger);
    List<UserPost> findAllByOrderByCreatedDateDesc();
  Optional<UserPost> findPostByIdAndUserblogger(Long id, UserBlogger blogger);
}
