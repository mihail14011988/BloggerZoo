/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.security;


public class SecurityConst {
   
    public static final String SIGN_UP_URL="/api/auth/**";
    public static final String SECRET_CODE="SecretKeyGenJWT";
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
    public static final String CONTENT_TYPE="application/json";
    public static final long EXPIRATION_TIME=600_000;
}
