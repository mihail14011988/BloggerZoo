/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.payload.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LoginReguest {
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    
}
