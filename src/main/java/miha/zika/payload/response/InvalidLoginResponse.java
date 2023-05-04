/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse(String username, String password) {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }

    public InvalidLoginResponse() {
    }
    
    
}
