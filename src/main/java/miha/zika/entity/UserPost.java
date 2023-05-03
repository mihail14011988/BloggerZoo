/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.entity;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="post")
public class UserPost {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String location;
    private Integer likes;   
    
       
    @Column
    @ElementCollection(targetClass = String.class)
    private Set <String> userLiked = new HashSet<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    private UserBlogger userblogger;
    
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "userPost", orphanRemoval = true)
    private List <Comment> comments;
    @Column(updatable = false)
    private LocalDateTime createdDate;
    
    
    public UserPost() {
       
    }
    
    @PrePersist
    protected void onCreatePost(){
    this.createdDate=LocalDateTime.now();
    }
}
