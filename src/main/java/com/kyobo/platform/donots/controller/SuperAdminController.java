package com.kyobo.platform.donots.controller;

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
@RequestMapping("/super/admin")
@RequiredArgsConstructor
@RestController
public class SuperAdminController {

    private final LoginService loginService;
    @PostMapping("/create/admin-user")
    @Operation(summary = "관리자 생성", description = "")
    @Parameter(name = "birthday", description = "생년월일 6자리", example = "900625")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = UserDetails.class))),
            @ApiResponse(responseCode = "1000", description = "이미 가입된 아이디입니다."),
            @ApiResponse(responseCode = "4000", description = "파라메터 인자값이 정상적이지 않습니다.")
    })
    public ResponseEntity createAdminUser(@RequestBody @Valid CreateAdminUserRequest createAdminUserRequest) {

        UserDetails userDetails = loginService.createAdminUser(createAdminUserRequest);

        return new ResponseEntity(userDetails, HttpStatus.CREATED);
    }

    @GetMapping("/verification/{adminId}")
    @Operation(summary = "ADMIN ID 가입확인", description = "로그인 전 비밀번호 찾기를 위한 ADMIN ID 가입확인")
    @Parameter(name = "adminId", description = "아이디(영문)", example = "kyobo1004")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity idVerification (@PathVariable("adminId") String adminId) {
        Map<String, Boolean> result = loginService.verification(adminId);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
