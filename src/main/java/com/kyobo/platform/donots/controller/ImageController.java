package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.common.exception.RequestBodyEmptyException;
import com.kyobo.platform.donots.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final S3ImageService s3ImageService;

    @PutMapping("/v1/admin-user/{adminId}")
    @Operation(summary = "admin 첨부 이미지 > 덮어쓰기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<?> replaceParentProfilePicture(@PathVariable String adminId, @RequestBody MultipartFile multipartFile) throws IOException, DecoderException {
        if (multipartFile == null)
            throw new RequestBodyEmptyException();
        s3ImageService.uploadAdminImage(adminId, multipartFile);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "admin 첨부 이미지 > 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @DeleteMapping("/v1/admin-user/{adminId}")
    public ResponseEntity<?> deleteParentProfilePicture(@PathVariable String adminId) throws IOException, DecoderException {
        s3ImageService.deleteAdminImage(adminId);
     return ResponseEntity.ok().build();
    }

}
