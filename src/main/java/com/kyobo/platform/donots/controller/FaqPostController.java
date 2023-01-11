package com.kyobo.platform.donots.controller;

import com.kyobo.platform.donots.model.dto.request.FaqPostRequest;
import com.kyobo.platform.donots.model.entity.FaqPost;
import com.kyobo.platform.donots.service.FaqPostService;

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
    public ResponseEntity<?> findAllFaqPostSummaries() {
        log.info("FaqPostController.findAllFaqPostSummaries");

        List<FaqPost> faqPosts = faqPostService.findAllFaqPostSummaries();

        return new ResponseEntity(faqPosts, HttpStatus.OK);
    }

    @GetMapping("/v1/faq-posts/{key}")
    public ResponseEntity<?> findFaqPostDetailsByKey(@PathVariable Long key) {
        log.info("FaqPostController.findFaqPostDetailsByKey");

        FaqPost faqPosts = faqPostService.findFaqPostDetailsByKey(key);

        return new ResponseEntity(faqPosts, HttpStatus.OK);
    }

    @PostMapping("/v1/faq-posts")
    public ResponseEntity<?> registerFaqPost(@RequestBody FaqPostRequest faqPostRequest) {
        Long registeredFaqPost = faqPostService.registerFaqPost(faqPostRequest.toEntity());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{key}")
                .buildAndExpand(registeredFaqPost)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/v1/faq-posts/{key}")
    public ResponseEntity<?> modifyFaqPost(@PathVariable Long key, @RequestBody FaqPostRequest faqPostRequest) {
        faqPostService.modifyFaqPost(key, faqPostRequest.toEntity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/faq-posts/{key}")
    public ResponseEntity<?> deleteFaqPost(@PathVariable Long key) {
        faqPostService.deleteFaqPost(key);
        return ResponseEntity.ok().build();
    }
}
