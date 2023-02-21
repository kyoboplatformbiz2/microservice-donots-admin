package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.common.exception.DefaultException;
import com.kyobo.platform.donots.common.util.S3FileUploadUtil;
import com.kyobo.platform.donots.model.dto.request.FaqPostRequest;
import com.kyobo.platform.donots.model.dto.response.FaqPostListResponse;
import com.kyobo.platform.donots.model.dto.response.FaqPostResponse;
import com.kyobo.platform.donots.model.entity.FaqPost;
import com.kyobo.platform.donots.model.repository.FaqPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FaqPostService {
    private final FaqPostRepository faqPostRepository;
    private final S3FileUploadUtil s3FileUploadUtil;

    public FaqPostListResponse findFaqPostsFiltered(String searchTerm, Pageable pageable) {

        Page<FaqPost> pageFaqPost;
        if (StringUtils.hasText(searchTerm))
            pageFaqPost = faqPostRepository.findByQuestionContainingOrAnswerContainingOrderByCreatedDatetimeDesc(searchTerm, searchTerm, pageable);
        else
            pageFaqPost = faqPostRepository.findAllByOrderByCreatedDatetimeDesc(pageable);

        List<FaqPostResponse> faqPostResponseList = pageFaqPost.getContent().stream()
                .map(m -> new FaqPostResponse(m))
                .collect(Collectors.toList());

        FaqPostListResponse response = new FaqPostListResponse(faqPostResponseList, pageFaqPost.getTotalPages(), pageFaqPost.getTotalElements());

        return response;
    }

    public FaqPostResponse findFaqPostDetailsByKey(Long key) {
        FaqPost faqPost = faqPostRepository.findById(key).get();
        return new FaqPostResponse(faqPost);
    }

    @Transactional
    public Long registerFaqPost(FaqPostRequest faqPostRequest, MultipartFile multipartFile) throws DecoderException, IOException {
        LocalDateTime now = LocalDateTime.now();
        FaqPost faqPost = FaqPost.builder()
                .faqCategory(faqPostRequest.getFaqCategory())
                .question(faqPostRequest.getQuestion())
                .answer(faqPostRequest.getAnswer())
                // TODO [Session] 세션 연동 필요
                .adminId("dummyAdminId")
                .boardStartDatetime(faqPostRequest.getBoardStartDatetime())
                .boardEndDatetime(faqPostRequest.getBoardEndDatetime())
                .createdDatetime(now)
                .lastModifiedDatetime(now)
                .build();

        FaqPost foundFaqPost = faqPostRepository.saveAndFlush(faqPost);

        // FIXME [TEST] S3 연동 후 주석 제거 및 이미지 업로드, 삭제 테스트
        if (multipartFile != null) {    // 첨부된 파일이 있으면 업로드
            String uploadedRepresentativeImgUrl = uploadRepresentativeImgToS3AndUpdateUrl(foundFaqPost.getKey(), multipartFile);
            foundFaqPost.updateRepresentativeImgUrl(uploadedRepresentativeImgUrl);
        }

        return foundFaqPost.getKey();
    }

    @Transactional
    public void modifyFaqPost(Long key, FaqPostRequest faqPostRequest, MultipartFile multipartFile) throws DecoderException, IOException {
        FaqPost foundFaqPost = faqPostRepository.findById(key).get();
        foundFaqPost.updateFaqPost(faqPostRequest);

        // FIXME [TEST] S3 연동 후 주석 제거 및 이미지 업로드, 삭제 테스트
        if (faqPostRequest.getIsAttachedImageFileChanged()) {    // 클라이언트에서 첨부파일이 변경된 적이 있고,
            if (multipartFile != null) {    // 첨부된 파일이 있으면 업로드(교체)
                String uploadedImageUrl = uploadRepresentativeImgToS3AndUpdateUrl(foundFaqPost.getKey(), multipartFile);
                foundFaqPost.updateRepresentativeImgUrl(uploadedImageUrl);
            }
            // TODO 클라이언트에서 null을 제대로 넘겨주는지 확인 필요. 필드를 아예 안 넣는 것과 비교가 되어야 할 듯
            else {  // 첨부된 파일이 null이면 삭제
                deleteRepresentativeImgToS3AndUpdateUrl(foundFaqPost.getKey());
                foundFaqPost.updateRepresentativeImgUrl("");
            }
        }
    }

    @Transactional
    public void deleteFaqPost(Long key) {
        faqPostRepository.deleteById(key);
    }

    @Transactional
    public String uploadRepresentativeImgToS3AndUpdateUrl(Long key, MultipartFile multipartFile) throws DecoderException, IOException {

        FaqPost foundFaqPost = faqPostRepository.findById(key)
                                                .orElseThrow(() -> new DefaultException("요청된 FAQ가 없습니다."));

        String foundRepresentativeImgUrl = foundFaqPost.getRepresentativeImgUrl();
        String imageDirectoryPathAfterDomain = "faq-posts/" + key + "/";
        String uploadedRepresentativeImageUrl = s3FileUploadUtil.uploadImageToS3AndGetUrl(multipartFile, foundRepresentativeImgUrl, imageDirectoryPathAfterDomain);

        foundFaqPost.updateRepresentativeImgUrl(uploadedRepresentativeImageUrl);
        return uploadedRepresentativeImageUrl;
    }

    @Transactional
    public void deleteRepresentativeImgToS3AndUpdateUrl(Long key) throws DecoderException, IOException {

        FaqPost foundFaqPost = faqPostRepository.findById(key)
                                                .orElseThrow(() -> new DefaultException("요청된 FAQ가 없습니다."));

        String imageDirectoryPathAfterDomain = "faq-posts/" + key + "/";
        s3FileUploadUtil.deleteImageFromS3(foundFaqPost.getRepresentativeImgUrl(), imageDirectoryPathAfterDomain);
        foundFaqPost.updateRepresentativeImgUrl("");
    }
}
