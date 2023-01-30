package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.common.exception.RequestBodyEmptyException;
import com.kyobo.platform.donots.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final S3ImageService s3ImageService;

    @DeleteMapping("/v1/admin-user/{adminId}")
    @Operation(summary = "admin 첨부 이미지 > 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<?> deleteAdminImage(@PathVariable String adminId) throws IOException, DecoderException {
        s3ImageService.deleteAdminImage(adminId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/v1/admin-user/{adminId}")
    @Operation(summary = "admin 첨부 이미지 > 덮어쓰기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<?> uploadAdminImage(@PathVariable String adminId, @RequestBody MultipartFile multipartFile) throws IOException, DecoderException {
        if (multipartFile == null)
            throw new RequestBodyEmptyException();
        String attachImageUrl = s3ImageService.uploadAdminImage(adminId, multipartFile);
        Map<String, String> result = new HashMap<>();
        result.put("attachImageUrl", attachImageUrl);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }


}
