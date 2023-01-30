package com.kyobo.platform.donots.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

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
    public ResponseEntity noticeRegedit (@RequestBody @Valid NoticeRequest noticeRequest) throws IOException {
        Long result = noticeService.noticeRegedit(noticeRequest);
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
    public ResponseEntity deleteNotice(@PathVariable("noticePostKey") Long noticePostKey) {
        noticeService.deleteNotice(noticePostKey);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/v1/notice/post/{noticePostKey}")
    @Operation(summary = "공지사항 수정", description = "관리자 공지사항 게시판 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "500", description = "실패")
    })
    public ResponseEntity updateNotice(@PathVariable("noticePostKey") Long noticePostKey, @RequestBody @Valid NoticeRequest noticeRequest) {
        noticeService.updateNotice(noticePostKey, noticeRequest);
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
    public ResponseEntity getNoticeList () {
        return new ResponseEntity(new NoticeListResponse(noticeService.getNoticeList()), HttpStatus.OK);
    }
}
