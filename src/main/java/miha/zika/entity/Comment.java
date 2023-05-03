/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.entity;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.Column;
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
@Table(name="comment")
public class Comment {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@ManyToOne(fetch = FetchType.EAGER)
    private UserPost userPost;
@Column(nullable = false)
    private String userName;
@Column(nullable = false)
    private Long userId;
@Column(columnDefinition = "text", nullable = false)
    private String message;
@Column(updatable = false)
    private LocalDateTime createdComment;

    public Comment() {
    }



    @PrePersist
    protected void onCreate() {
        this.createdComment = LocalDateTime.now();
    }
}
