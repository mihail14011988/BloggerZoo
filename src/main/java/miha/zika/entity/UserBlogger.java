/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import miha.zika.entity.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data
@Entity
@Table(name="userblogger")
public class UserBlogger implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(length = 3000)
    private String password;
    @Column(columnDefinition = "text")
    private String bio;
    
    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name="user_role",joinColumns = @JoinColumn(name="user_id"))
    private Set<UserRole> userRole = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userblogger", orphanRemoval = true)
    private List<UserPost> userPost = new ArrayList<>();
    
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate; 
    
   @Transient
   private Collection<? extends GrantedAuthority> aithorites;

    public UserBlogger() {
    }

    public UserBlogger(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> aithorites) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.aithorites = aithorites;
    }
   
    
   
    
    @PrePersist
    protected void onCreate(){
    this.createdDate=LocalDateTime.now();
    
    }
    @Override
    public String getPassword(){
    return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isAccountNonExpired() {
    return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isCredentialsNonExpired() {
     return true;
    }

    @Override
    public boolean isEnabled() {
     return true;
    }
}
