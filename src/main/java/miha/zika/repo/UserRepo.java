/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package miha.zika.repo;

import java.util.Optional;
import miha.zika.entity.UserBlogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserBlogger, Long>{
    Optional<UserBlogger> findUserBloggerByUsername(String username);
    Optional<UserBlogger> findUserBloggerByEmail(String email);
    Optional<UserBlogger> findUserBloggerById(Long id);
           
}
