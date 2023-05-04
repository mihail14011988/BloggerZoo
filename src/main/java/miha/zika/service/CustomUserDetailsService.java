/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.service;

import java.util.List;
import java.util.stream.Collectors;
import miha.zika.entity.UserBlogger;
import miha.zika.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
   
    private final UserRepo repo;
    @Autowired
    public CustomUserDetailsService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
     
    UserBlogger user= repo.findUserBloggerByUsername(username).orElseThrow(()->new UsernameNotFoundException("User не найден"));
        return build(user);

    }
    
    public UserBlogger loadUserById (Long id){
    return repo.findUserBloggerById(id).orElse(null);

    }
    
   public static UserBlogger build(UserBlogger user){
   List<GrantedAuthority> authoritys = user.getUserRole().stream().map(role-> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
   return new UserBlogger(user.getId(), user.getUsername(),user.getEmail(), user.getPassword(), authoritys);
   }

  
    
}
