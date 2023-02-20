package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.common.exception.RequestBodyEmptyException;
import com.kyobo.platform.donots.model.dto.request.NoticeRequest;
import com.kyobo.platform.donots.model.dto.response.NoticeListResponse;
import com.kyobo.platform.donots.model.dto.response.NoticeResponse;
import com.kyobo.platform.donots.service.NoticeService;
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

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/noAuth")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/v1/notice/post")
    @Operation(summary = "공지사항 등록", description = "관리자 공지사항 게시판 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "500", description = "실패")
    })
    public ResponseEntity noticeRegedit(NoticeRequest noticeRequest, MultipartFile multipartFile) throws IOException, DecoderException {

        Long result = noticeService.noticeRegedit(noticeRequest, multipartFile);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{key}")
                .buildAndExpand(result)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/v1/notice/post/{noticePostKey}")
    @Operation(summary = "공지사항 삭제", description = "관리자 공지사항 게시판 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "500", description = "실패")
    })
    public ResponseEntity deleteNotice(@PathVariable("noticePostKey") Long noticePostKey) throws IOException {
        noticeService.deleteNotice(noticePostKey);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/v1/notice/post/{noticePostKey}")
    @Operation(summary = "공지사항 수정", description = "관리자 공지사항 게시판 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "500", description = "실패")
    })
    public ResponseEntity updateNotice(@PathVariable("noticePostKey") Long noticePostKey, NoticeRequest noticeRequest, MultipartFile multipartFile) throws DecoderException, IOException {
        noticeService.updateNotice(noticePostKey, noticeRequest, multipartFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/notice/post/{noticePostKey}")
    @Operation(summary = "공지사항 상세 조회", description = "유저/관리자 공지사항 게시판 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = NoticeResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "500", description = "실패")
    })
    public ResponseEntity getNoticeDetail (@PathVariable("noticePostKey") Long noticePostKey) {
        NoticeResponse result = noticeService.getNoticeDetail(noticePostKey);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/v1/notice/post")
    @Operation(summary = "공지사항 리스트 조회", description = "유저/관리자 공지사항 게시판 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = NoticeListResponse.class))),
    })
    public ResponseEntity findNoticePostsFiltered(@RequestParam(required = false) String searchTerm, final Pageable pageable) {
        log.info("NoticeController.getNoticeList()");

        NoticeListResponse response = noticeService.findNoticePostsFiltered(searchTerm, pageable);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/v1/notice/{key}/image")
    @Operation(summary = "공지사항 첨부 이미지 > 덮어쓰기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<?> uploadNoticeImageToS3AndUpdateUrl(@PathVariable Long key, @RequestBody MultipartFile multipartFile) throws IOException, DecoderException {
        if (multipartFile == null)
            throw new RequestBodyEmptyException();

        String imageUrl = noticeService.uploadNoticeImageToS3AndUpdateUrl(key, multipartFile);

        Map<String, String> createdImageUrl = new HashMap<>();
        createdImageUrl.put("imageUrl", imageUrl);
        return new ResponseEntity(createdImageUrl, HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/notice/{key}/image")
    @Operation(summary = "공지사항 첨부 이미지 > 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<?> deleteNoticeImageFromS3AndUpdateUrl(@PathVariable Long key) throws IOException, DecoderException {
        noticeService.deleteNoticeImageFromS3AndUpdateUrl(key);
        return ResponseEntity.ok().build();
    }
}
