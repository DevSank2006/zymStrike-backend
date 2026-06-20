package com.sanket.Zyvix.Controller;

import com.sanket.Zyvix.Dto.ApiResponse;
import com.sanket.Zyvix.Dto.UserDto;
import com.sanket.Zyvix.Dto.UserReqDto;
import com.sanket.Zyvix.Entities.UserEntity;
import com.sanket.Zyvix.Repository.UserRepository;
import com.sanket.Zyvix.Security.JwtServiceClass;
import com.sanket.Zyvix.Security.LoginPojo;
import com.sanket.Zyvix.Security.UserLoginResponseDto;
import com.sanket.Zyvix.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {
        "https://zym-strike.vercel.app/",
        "http://localhost:5173"
})
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceClass jwtServiceClass;
    private final UserRepository userRepository;

    public  UserController(UserService userService, AuthenticationManager authenticationManager, JwtServiceClass jwtServiceClass, UserRepository userRepository){
        this.userService=userService;
        this.authenticationManager = authenticationManager;
        this.jwtServiceClass = jwtServiceClass;
        this.userRepository = userRepository;
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>>createUser(@Valid @RequestBody UserReqDto user){
           return userService.createUser(user);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>>getUser(@PathVariable long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>>deleteUser(@PathVariable long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/update/user/{id}")
    public ResponseEntity<ApiResponse<UserDto>>updateUser(@PathVariable long id,@RequestBody UserEntity user){
       return userService.updateUser(id,user);
    }
    @GetMapping("/getId/{email}")
    public ResponseEntity<ApiResponse<Long>>getIdByEmail(@PathVariable String email){
        return userService.getIdByEmail(email);
    }
    @GetMapping("/user/{name}")
    public ResponseEntity<ApiResponse<UserDto>>getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPojo loginPojo){
        try{
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginPojo.getUsername(),
                            loginPojo.getPassword()
                    )
            );
            UserEntity user=userRepository.findByEmail(loginPojo.getUsername()).orElseThrow();
            String token=jwtServiceClass.generateToken(loginPojo.getUsername());
            UserLoginResponseDto userLoginResponseDto=UserLoginResponseDto.builder()
                    .id(user.getId())
                    .token(token).build();
                return ResponseEntity.status(HttpStatus.OK).body(userLoginResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("wrong credentials");
        }

    }


}
