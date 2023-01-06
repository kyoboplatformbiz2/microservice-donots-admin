package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.model.dto.request.ChangePasswordRequest;
import com.kyobo.platform.donots.model.dto.request.CreateAdminUserRequest;
import com.kyobo.platform.donots.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/change/password")
    @Operation(summary = "본인인증 sms 발송 요청 API ", description = "")
    @Parameter(name = "birthday", description = "생년월일 6자리", example = "810624")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        Map<String, Boolean> result = loginService.changePasswordRequest(changePasswordRequest);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}