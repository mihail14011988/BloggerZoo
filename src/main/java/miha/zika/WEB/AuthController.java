/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.WEB;

import javax.validation.Valid;
import miha.zika.entity.annotaitions.ValidEmail;
import miha.zika.entity.validation.ResponseErrorValidation;
import miha.zika.payload.request.LoginReguest;
import miha.zika.payload.request.SignUpRequest;
import miha.zika.payload.response.JWTResponseTokenSuccess;
import miha.zika.payload.response.MessageResponse;
import miha.zika.security.JWTtokenProvider;
import miha.zika.security.SecurityConst;
import miha.zika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
   @Autowired
    private JWTtokenProvider provider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidation errorValidation;
    @Autowired
    private UserService service;
    

    @PostMapping("/signin")
    public ResponseEntity<Object> authUser(@Valid @RequestBody LoginReguest loginReguest, BindingResult result){
     ResponseEntity <Object> errors =errorValidation.mapValidResponseEntity(result);
    if(ObjectUtils.isEmpty(errors)) return errors ;
    
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginReguest.getUsername(), loginReguest.getPassword()));       
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken= SecurityConst.TOKEN_PREFIX+provider.generateToken(authentication);
        return ResponseEntity.ok(new JWTResponseTokenSuccess(true,"TOKEN IS OK"));
    };
    
 //api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<Object> registrUser (@Valid @RequestBody SignUpRequest request, BindingResult result){
    ResponseEntity <Object> errors =errorValidation.mapValidResponseEntity(result);
    if(ObjectUtils.isEmpty(errors)) return errors ;
    service.createUser(request);
    return ResponseEntity.ok(new MessageResponse("User registr is ok"));
    };
}