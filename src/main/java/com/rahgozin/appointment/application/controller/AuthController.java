package com.rahgozin.appointment.application.controller;

import com.rahgozin.appointment.application.config.AuthenticationService;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.exception.ErrorsModel;
import com.rahgozin.appointment.application.model.*;
import com.rahgozin.appointment.application.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "AUTHENTICATE REST APIs",
        description = "FOR USE THIS APPLICATION"
)
@RestController
@RequestMapping(value = "v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AuthController{

    private final AuthenticationService service;
    @Operation(
            summary = "login REST API",
            description = "with this service you can login and get authorization tokens"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "login",
                    content = @Content(
                            schema = @Schema(implementation = LoginResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorsModel.class)
                    )
            )
    }
    )

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        try {
            return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
        }catch (Exception e){
            log.error("ERROR", e.toString(), e.getMessage());
            throw e;
        }

    }

    @Operation(
            summary = "refresh-token REST API",
            description = "with this service you can take refresh token, in body send refresh token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "register",
                    content = @Content(
                            schema = @Schema(implementation = RefreshTokenResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorsModel.class)
                    )
            )
    }
    )

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest request) throws Exception {
        try {
            return new ResponseEntity<>(service.newJwtToken(request), HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }
}
