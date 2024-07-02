package com.rahgozin.appointment.application.config;


import com.rahgozin.appointment.application.model.LoginRequest;
import com.rahgozin.appointment.application.model.LoginResponse;
import com.rahgozin.appointment.application.model.RefreshTokenRequest;
import com.rahgozin.appointment.application.model.RefreshTokenResponse;
import com.rahgozin.appointment.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    public LoginResponse authenticate(LoginRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            var jwtToken = jwtService.generateJwtToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            log.info("USER WITH USER NAME = {}", user.getUsername() + " LOGGED IN SUCCESSFULLY");
            return LoginResponse.builder()
                    .jwtToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }catch (Exception e){
            log.error("ERRORRRRRRRR");
            throw e;
        }

    }

    public RefreshTokenResponse newJwtToken(RefreshTokenRequest request) throws Exception {
        String userName;
        if (jwtService.isTokenValid(request.getToken().substring(7))) {
            userName = jwtService.extractUsername(request.getToken());
            var user = userRepository.findByUsername(userName).orElseThrow();
            var refreshToken = jwtService.generateJwtToken(user);
            return RefreshTokenResponse.builder()
                    .jwtToken(refreshToken)
                    .build();
        }else {
            throw new Exception();
        }
    }
}
