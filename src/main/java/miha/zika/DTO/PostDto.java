/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.DTO;

import java.util.Set;
import lombok.Data;

@Data
public class PostDto {
    private Long id;
   private String title;
    private String description;
    private String location;
    private String username;
    private Integer likes; 
    private Set <String> userLikes;
    
}
