/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.service;

import java.security.Principal;
import miha.zika.DTO.UserDto;
import miha.zika.entity.UserBlogger;
import miha.zika.entity.enums.UserRole;
import miha.zika.mainException.UserExistException;
import miha.zika.payload.request.SignUpRequest;
import miha.zika.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserService {

    public final static Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserBlogger createUser(SignUpRequest request) {
        UserBlogger blogger = new UserBlogger();
        blogger.setEmail(request.getEmail());
        blogger.setName(request.getFirstname());
        blogger.setLastname(request.getLastname());
        blogger.setUsername(request.getUsername());
        blogger.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        blogger.getUserRole().add(UserRole.USER_SINGLE);
        try {
            LOG.info("SAVING USER", request.getEmail());
            return userRepo.save(blogger);
        } catch (Exception e) {
            LOG.error("ERROR SAVE USER", e.getMessage());
            throw new UserExistException("They user is active " + blogger.getUsername() + " is exist");
        }
    }

    public UserBlogger updateBlogger(UserDto dto, Principal principal) {
        UserBlogger blogger = getUserByPrincipal(principal);
        blogger.setName(dto.getFirstname());
        blogger.setLastname(dto.getLastname());
        blogger.setBio(dto.getBio());

        return userRepo.save(blogger);
    }
    
    public UserBlogger getCurrentUserBlogger(Principal principal){
      return getUserByPrincipal(principal);
    }

    private UserBlogger getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepo.findUserBloggerByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }

}
