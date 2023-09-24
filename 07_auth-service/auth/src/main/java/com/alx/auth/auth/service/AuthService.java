package com.alx.auth.auth.service;

import com.alx.auth.auth.dto.TokenDto;
import com.alx.auth.auth.jwt.JwtProvider;
import com.alx.auth.user.dto.UserDto;
import com.alx.auth.user.entity.Usuario;
import com.alx.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    public UserDto save(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already registered");
        }

        Usuario user = modelMapper.map(userDto, Usuario.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);


        return modelMapper.map(user, UserDto.class);
    }


    // basic login no real real ;v
    public TokenDto login(UserDto userDto) {
        Usuario user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return TokenDto.builder()
                .token(jwtProvider.createToken(user))
                .build();
    }


    public TokenDto validate(String token) {
        jwtProvider.validate(token);

        Usuario user = userRepository.findByUsername(jwtProvider.extractUsername(token)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token")
        );

        return TokenDto.builder()
                .token(jwtProvider.createToken(user))
                .build();
    }

}
