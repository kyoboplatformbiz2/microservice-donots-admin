package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.model.dto.request.FaqPostRequest;
import com.kyobo.platform.donots.model.dto.response.FaqPostListResponse;
import com.kyobo.platform.donots.model.dto.response.FaqPostResponse;
import com.kyobo.platform.donots.service.FaqPostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

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
                    content = @Content(schema = @Schema(implementation = FaqPostListResponse.class))),
    })
    public ResponseEntity<?> findFaqPostsFiltered(@RequestParam(required = false) String searchTerm, final Pageable pageable) {
        log.info("findAllFaqPostSummaries");

        FaqPostListResponse response = faqPostService.findFaqPostsFiltered(searchTerm, pageable);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/v1/faq-posts/{key}")
    @Operation(summary = "FAQ  상세 조회 ", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                content = @Content(schema = @Schema(implementation = FaqPostResponse.class))),
    })
    public ResponseEntity<?> findFaqPostDetailsByKey(@PathVariable Long key) {
        FaqPostResponse result = faqPostService.findFaqPostDetailsByKey(key);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/v1/faq-posts")
    @Operation(summary = "FAQ 등록", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<?> registerFaqPost(@Valid FaqPostRequest faqPostRequest, MultipartFile multipartFile) throws DecoderException, IOException {

        Long registeredFaqPost = faqPostService.registerFaqPost(faqPostRequest, multipartFile);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{key}")
                .buildAndExpand(registeredFaqPost)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/v1/faq-posts/{key}")
    @Operation(summary = "FAQ 업데이트 ", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<?> modifyFaqPost(@PathVariable Long key, @Valid FaqPostRequest faqPostRequest, MultipartFile multipartFile) throws DecoderException, IOException {
        faqPostService.modifyFaqPost(key, faqPostRequest, multipartFile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/faq-posts/{key}")
    @Operation(summary = "FAQ 삭제 ", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    public ResponseEntity<?> deleteFaqPost(@PathVariable Long key) {
        faqPostService.deleteFaqPost(key);
        return ResponseEntity.ok().build();
    }
}
