package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.model.dto.request.CreateAdminUserRequest;
import com.kyobo.platform.donots.model.dto.request.DeleteAdminUserRequest;
import com.kyobo.platform.donots.model.dto.request.ModifyAdminUserRequest;
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
    @PostMapping("/v1/admin-user")
    @Operation(summary = "관리자 생성", description = "")
    @Parameter(name = "adminId", description = "아이디")
    @Parameter(name = "password", description = "비밀번호")
    @Parameter(name = "adminUserName", description = "어드민 유저 이름")
    @Parameter(name = "adminUserNumber", description = "어드민 사원 번호")
    @Parameter(name = "departmentName", description = "부서 이름")
    @Parameter(name = "phoneNumber", description = "핸드폰 번호")
    @Parameter(name = "email", description = "이메일")
    @Parameter(name = "reasonsForAuthorization", description = "권한부여사유")
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

    @DeleteMapping("/v1/admin-user")
    @Operation(summary = "관리자 ID 삭제", description = "관리자 ID 삭제")
    @Parameter(name = "adminId", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "4000", description = "파라메터 인자값이 정상적이지 않습니다.")
    })
    public ResponseEntity deleteAdminUser (@RequestBody @Valid DeleteAdminUserRequest deleteAdminUserRequest) {
        loginService.deleteAdminUser(deleteAdminUserRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/v1/admin-user")
    @Operation(summary = "관리자 ID 정보 변경  ", description = "관리자 정보 변경")
    @Parameter(name = "adminId", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "4000", description = "파라메터 인자값이 정상적이지 않습니다.")
    })
    public ResponseEntity modifyAdminUser (@RequestBody @Valid ModifyAdminUserRequest modifyAdminUserRequest) {
        UserDetails result = loginService.modifyAdminUser(modifyAdminUserRequest);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/v1/verification/{adminId}")
    @Operation(summary = "관리자 ID 가입확인", description = "관리자 ID 중복확인")
    @Parameter(name = "adminId", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "4000", description = "파라메터 인자값이 정상적이지 않습니다.")
    })
    public ResponseEntity idVerification (@PathVariable("adminId") String adminId) {
        loginService.verification(adminId);
        return ResponseEntity.ok().build();
    }



}
