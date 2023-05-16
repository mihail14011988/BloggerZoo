/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.DTO;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    @NotEmpty
    private String message;
    private String username;
}
