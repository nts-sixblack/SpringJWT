package nts.sixblack.checkjwt.controller;

import nts.sixblack.checkjwt.form.LoginForm;
import nts.sixblack.checkjwt.jwt.JwtTokenProvider;
import nts.sixblack.checkjwt.util.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class HomeController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("login")
    public String login(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       loginForm.getUsername(), loginForm.getPassword()
               )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
        return token;
    }

    @GetMapping("home")
    public String home(){
        return "home";
    }

    @GetMapping("index")
    public String index(){
        return "index";
    }
}
