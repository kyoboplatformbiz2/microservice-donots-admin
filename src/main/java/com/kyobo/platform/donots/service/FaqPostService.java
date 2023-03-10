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
                // TODO [Session] ?????? ?????? ??????
                .adminId("dummyAdminId")
                .boardStartDatetime(faqPostRequest.getBoardStartDatetime())
                .boardEndDatetime(faqPostRequest.getBoardEndDatetime())
                .createdDatetime(now)
                .lastModifiedDatetime(now)
                .build();

        FaqPost foundFaqPost = faqPostRepository.saveAndFlush(faqPost);

        // FIXME [TEST] S3 ?????? ??? ?????? ?????? ??? ????????? ?????????, ?????? ?????????
        if (multipartFile != null) {    // ????????? ????????? ????????? ?????????
            String uploadedRepresentativeImgUrl = uploadRepresentativeImgToS3AndUpdateUrl(foundFaqPost.getKey(), multipartFile);
            foundFaqPost.updateRepresentativeImgUrl(uploadedRepresentativeImgUrl);
        }

        return foundFaqPost.getKey();
    }

    @Transactional
    public void modifyFaqPost(Long key, FaqPostRequest faqPostRequest, MultipartFile multipartFile) throws DecoderException, IOException {
        FaqPost foundFaqPost = faqPostRepository.findById(key).get();
        foundFaqPost.updateFaqPost(faqPostRequest);

        // FIXME [TEST] S3 ?????? ??? ?????? ?????? ??? ????????? ?????????, ?????? ?????????
        if (faqPostRequest.getIsAttachedImageFileChanged()) {    // ????????????????????? ??????????????? ????????? ?????? ??????,
            if (multipartFile != null) {    // ????????? ????????? ????????? ?????????(??????)
                String uploadedImageUrl = uploadRepresentativeImgToS3AndUpdateUrl(foundFaqPost.getKey(), multipartFile);
                foundFaqPost.updateRepresentativeImgUrl(uploadedImageUrl);
            }
            // TODO ????????????????????? null??? ????????? ??????????????? ?????? ??????. ????????? ?????? ??? ?????? ?????? ????????? ????????? ??? ???
            else {  // ????????? ????????? null?????? ??????
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
                                                .orElseThrow(() -> new DefaultException("????????? FAQ??? ????????????."));

        String foundRepresentativeImgUrl = foundFaqPost.getRepresentativeImgUrl();
        String imageDirectoryPathAfterDomain = "faq-posts/" + key + "/";
        String uploadedRepresentativeImageUrl = s3FileUploadUtil.uploadImageToS3AndGetUrl(multipartFile, foundRepresentativeImgUrl, imageDirectoryPathAfterDomain);

        foundFaqPost.updateRepresentativeImgUrl(uploadedRepresentativeImageUrl);
        return uploadedRepresentativeImageUrl;
    }

    @Transactional
    public void deleteRepresentativeImgToS3AndUpdateUrl(Long key) throws DecoderException, IOException {

        FaqPost foundFaqPost = faqPostRepository.findById(key)
                                                .orElseThrow(() -> new DefaultException("????????? FAQ??? ????????????."));

        String imageDirectoryPathAfterDomain = "faq-posts/" + key + "/";
        s3FileUploadUtil.deleteImageFromS3(foundFaqPost.getRepresentativeImgUrl(), imageDirectoryPathAfterDomain);
        foundFaqPost.updateRepresentativeImgUrl("");
    }
}
