package com.example.epms.web;
import com.example.epms.domain.model.UserLogin;
import com.example.epms.domain.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  @Value("${jwt.expiration-minutes}")
  private long expirationMinutes;

  private final UsersService userService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UserLogin userLogin){
//    return userService.verify(userLogin, userLogin.getPassword());
    return ResponseEntity.ok(
            Map.of(
                    "token", userService.verify(userLogin, userLogin.getPassword()),
                    "expiresOn", Instant.now().plusSeconds(expirationMinutes * 60).toString()
            )
    );
  }
}