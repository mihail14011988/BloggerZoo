/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import miha.zika.entity.annotaitions.PassworMatches;
import miha.zika.entity.annotaitions.ValidEmail;

@Data
@PassworMatches
public class SignUpRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is requered")
    @ValidEmail
    private String email;

    @NotEmpty(message = "please enter your name")
    private String firstname;
    @NotEmpty(message = "please enter your lastname")
    private String lastname;
    @NotEmpty(message = "please enter your username")
    private String username;
    @NotEmpty(message = "password is requered")
    @Size(min = 6)
    private String password;
    private String confirmPassword;
}
