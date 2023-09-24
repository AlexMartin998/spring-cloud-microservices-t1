package com.alx.auth.auth.controller;

import com.alx.auth.auth.dto.TokenDto;
import com.alx.auth.auth.service.AuthService;
import com.alx.auth.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {

        return new ResponseEntity<>(authService.save(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {

        return ResponseEntity.ok(authService.login(userDto));
    }


    // // Lo recibe x el ReqParam xq
    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token) {

        return ResponseEntity.ok(authService.validate(token));
    }

}
