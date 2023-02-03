package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.model.dto.request.TermsOfServiceRequest;
import com.kyobo.platform.donots.model.dto.response.TermsOfServiceListResponse;
import com.kyobo.platform.donots.model.dto.response.TermsOfServiceResponse;
import com.kyobo.platform.donots.service.TermsOfServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TermsOfServiceController {

    private final TermsOfServiceService termsOfServiceService;

    @PostMapping("/v1/terms-of-services")
    @Operation(summary = "서비스약관 > 게시", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "500", description = "실패")
    })
    public ResponseEntity postTermsOfService(@RequestBody @Valid TermsOfServiceRequest termsOfServiceRequest) {
        Long foundTermsOfServiceKey = termsOfServiceService.postTermsOfService(termsOfServiceRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{key}")
                .buildAndExpand(foundTermsOfServiceKey)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/v1/terms-of-services/{key}")
    @Operation(summary = "서비스약관 > 수정", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "500", description = "실패")
    })
    public ResponseEntity modifyTermsOfService(@PathVariable Long key, @RequestBody @Valid TermsOfServiceRequest termsOfServiceRequest) {
        termsOfServiceService.modifyTermsOfService(key, termsOfServiceRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/terms-of-services/{key}")
    @Operation(summary = "서비스약관 > 삭제", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "500", description = "실패")
    })
    public ResponseEntity deleteTermsOfService(@PathVariable Long key) {
        termsOfServiceService.deleteTermsOfService(key);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/terms-of-services/{key}")
    @Operation(summary = "서비스약관 > 조회", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = TermsOfServiceResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "500", description = "실패")
    })
    public ResponseEntity findByKey (@PathVariable Long key) {
        TermsOfServiceResponse termsOfServiceResponse = termsOfServiceService.findByKey(key);
        return new ResponseEntity(termsOfServiceResponse, HttpStatus.OK);
    }

//    @GetMapping("/v1/terms-of-services/by-title/{title}")
//    @Operation(summary = "최신 서비스약관 > 조회", description = "")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공",
//                    content = @Content(schema = @Schema(implementation = TermsOfServiceResponse.class))),
//            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
//            @ApiResponse(responseCode = "500", description = "실패")
//    })
//    public ResponseEntity findMostRecentTermsOfServiceByTitle (@PathVariable String title) {
//        TermsOfServiceResponse termsOfServiceResponse = termsOfServiceService.findMostRecentTermsOfServiceByTitle(title);
//        return new ResponseEntity(termsOfServiceResponse, HttpStatus.OK);
//    }

    @GetMapping("/v1/terms-of-services")
    @Operation(summary = "[Test] 모든 서비스약관 > 조회", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = TermsOfServiceListResponse.class))),
    })
    public ResponseEntity findAllByOrderByCreatedDatetimeDesc () {
        return new ResponseEntity(new TermsOfServiceListResponse(termsOfServiceService.findAllByOrderByCreatedDatetimeDesc()), HttpStatus.OK);
    }

    @GetMapping("/v1/terms-of-services/partitioned-by-title-most-recent")
    @Operation(summary = "제목별 최신 서비스약관 > 조회", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = TermsOfServiceListResponse.class))),
    })
    public ResponseEntity findPartitionedByTitleMostRecent() {
        return new ResponseEntity(new TermsOfServiceListResponse(termsOfServiceService.findPartitionedByTitleMostRecent()), HttpStatus.OK);
    }

    @GetMapping("/v1/terms-of-services/by-title/{title}")
    @Operation(summary = "서비스약관 > 조회 by title", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = TermsOfServiceListResponse.class))),
    })
    public ResponseEntity findByTitle(@PathVariable String title) {
        return new ResponseEntity(new TermsOfServiceListResponse(termsOfServiceService.findByTitle(title)), HttpStatus.OK);
    }
}
