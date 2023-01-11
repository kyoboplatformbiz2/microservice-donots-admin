package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.model.dto.request.FaqPostRequest;
import com.kyobo.platform.donots.model.dto.response.FaqListResponse;
import com.kyobo.platform.donots.model.dto.response.FaqResponse;
import com.kyobo.platform.donots.model.dto.response.NoticeResponse;
import com.kyobo.platform.donots.model.entity.FaqPost;
import com.kyobo.platform.donots.service.FaqPostService;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
@Slf4j
public class FaqPostController {
    private final FaqPostService faqPostService;

    @GetMapping("/v1/faq-posts")
    @Operation(summary = "FAQ 리스트 조회 ", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = FaqListResponse.class))),
    })
    public ResponseEntity<?> findAllFaqPostSummaries() {
        return new ResponseEntity(new FaqListResponse(faqPostService.findAllFaqPostSummaries()), HttpStatus.OK);
    }

    @GetMapping("/v1/faq-posts/{key}")
    @Operation(summary = "FAQ  상세 조회 ", description = "")
    @Parameter(name = "key", description = "게시물 번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                content = @Content(schema = @Schema(implementation = FaqResponse.class))),
    })
    public ResponseEntity<?> findFaqPostDetailsByKey(@PathVariable Long key) {
        FaqResponse result = faqPostService.findFaqPostDetailsByKey(key);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/v1/faq-posts")
    @Operation(summary = "FAQ 등록 ", description = "")
    @Parameter(name = "faqCategory", description = "카테고리")
    @Parameter(name = "question", description = "질문")
    @Parameter(name = "answer", description = "답변")
    @Parameter(name = "representativeImgUrl", description = "이미지 url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<?> registerFaqPost(@RequestBody FaqPostRequest faqPostRequest) {
        Long registeredFaqPost = faqPostService.registerFaqPost(faqPostRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{key}")
                .buildAndExpand(registeredFaqPost)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/v1/faq-posts/{key}")
    @Operation(summary = "FAQ 업데이트 ", description = "")
    @Parameter(name = "faqCategory", description = "카테고리")
    @Parameter(name = "question", description = "질문")
    @Parameter(name = "answer", description = "답변")
    @Parameter(name = "representativeImgUrl", description = "이미지 url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<?> modifyFaqPost(@PathVariable Long key, @RequestBody FaqPostRequest faqPostRequest) {
        faqPostService.modifyFaqPost(key, faqPostRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/faq-posts/{key}")
    @Operation(summary = "FAQ 삭제 ", description = "")
    @Parameter(name = "key", description = "게시물 번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<?> deleteFaqPost(@PathVariable Long key) {
        faqPostService.deleteFaqPost(key);
        return ResponseEntity.ok().build();
    }
}
