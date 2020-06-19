package com.codulate.auth;

import com.codulate.auth.models.User;
import com.codulate.auth.dto.AuthReqDto;
import com.codulate.auth.dto.AuthRespDto;
import com.codulate.auth.dao.UserDao;
import com.codulate.auth.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtTokenUtil;
    private AuthService userDetailsService;
    private UserDao userDao;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil, AuthService userDetailsAuthService, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsAuthService;
        this.userDao = userDao;
    }

    @GetMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthReqDto authReqDto) throws Exception {
         try {
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    authReqDto.getUsername(), authReqDto.getPassword());
            authenticationManager.authenticate(authRequest);
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authReqDto.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthRespDto(jwt));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody User user){
        User userFromDb = userDao.findByUsername(user.getUsername());

        if (userFromDb != null) {
            throw new BadCredentialsException("User already exist");
        }

        return ResponseEntity.ok(userDetailsService.save(user));
    }

}