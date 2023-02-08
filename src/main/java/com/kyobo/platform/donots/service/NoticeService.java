package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.common.exception.DataNotFoundException;
import com.kyobo.platform.donots.common.exception.DefaultException;
import com.kyobo.platform.donots.config.HttpConfig;
import com.kyobo.platform.donots.model.dto.request.NoticeRequest;
import com.kyobo.platform.donots.model.dto.response.NoticeListResponse;
import com.kyobo.platform.donots.model.dto.response.NoticeResponse;
import com.kyobo.platform.donots.model.entity.NoticePost;
import com.kyobo.platform.donots.model.repository.NoticePostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    private final NoticePostRepository noticePostRepository;

    // TODO 임시
//    @Value("${spring.profiles.active}")
    String env_active = "dev";

    public Properties loadProperty() throws IOException {
        Properties properties = new Properties();
        Resource newResource = new ClassPathResource("awsAuth-" + env_active + ".properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(newResource.getInputStream()));
        properties.load(br);

        return properties;
    }

    @Transactional
    public Long noticeRegedit(NoticeRequest noticeRequest) throws IOException {
        NoticePost foundNoticePost = noticePostRepository.save(noticePostRegedit(noticeRequest));

        // 홈 > 알림 API 호출 시작
        String recipeurl = loadProperty().getProperty("recipeurl");
        String url = recipeurl + "/v1/recipe/registration/registMemberNoti";

        JSONObject newNoticePostNotifRequest = new JSONObject();
        newNoticePostNotifRequest.put("noti_img_key", "");                                      // 사용되지 않는 필드지만 API 스펙에 따라 셋팅
        newNoticePostNotifRequest.put("noti_recipe_key", foundNoticePost.getNoticePostKey());   // 공지사항키
        newNoticePostNotifRequest.put("noti_subject", foundNoticePost.getTitle());              // 알림 메시지
        newNoticePostNotifRequest.put("noti_type", "공지사항");                                 // 알림 유형 (승급, 공지사항)
        newNoticePostNotifRequest.put("noti_target_user_key", "");                              // 알림 대상 회원키 (공지사항 알림에서는 사용되지 않음)

        JSONObject newNoticePostNotifResponse = new HttpConfig().callApi(newNoticePostNotifRequest, url, HttpMethod.POST.name());
        String newNoticePostNotifResponseValue = (String) newNoticePostNotifResponse.get("databody");
        log.info("newNoticePostNotifResponseValue: "+ newNoticePostNotifResponseValue);
        if (!newNoticePostNotifResponseValue.isEmpty())
            throw new DefaultException("Recipe API 처리시 오류 발생");
        // 홈 > 알림 API 호출 끝

        return foundNoticePost.getNoticePostKey();
    }

    public NoticeListResponse findNoticePostsFiltered(String searchTerm, Pageable pageable) {

        Page<NoticePost> postPage;
        if (StringUtils.hasText(searchTerm))
            postPage = noticePostRepository.findByTitleContainingOrBodyContainingOrderByCreatedDateDesc(searchTerm, searchTerm, pageable);
        else
            postPage = noticePostRepository.findAllByOrderByCreatedDateDesc(pageable);

        List<NoticeResponse> noticeResponseList = postPage.getContent().stream()
                .map(m -> new NoticeResponse(m))
                .collect(Collectors.toList());

        NoticeListResponse response = new NoticeListResponse(noticeResponseList, postPage.getTotalPages(), postPage.getTotalElements());

        return response;
    }

    public NoticeResponse getNoticeDetail(Long noticePostKey) {
        NoticePost noticePost = noticePostRepository.findByNoticePostKey(noticePostKey);
        return new NoticeResponse(noticePost);
    }

    public void deleteNotice(Long noticePostKey) {
        noticePostRepository.deleteById(noticePostKey);
    }

    @Transactional
    public void updateNotice(Long noticePostKey, NoticeRequest noticeRequest) {
        NoticePost noticePost = noticePostRepository.findByNoticePostKey(noticePostKey);
        if(noticePost == null){
            throw new DataNotFoundException();
        }
        noticePost.updateNotice(noticeRequest.getTitle(), noticeRequest.getBody(), noticeRequest.getImgUrl(),
                noticeRequest.getBoardStartDate(), noticeRequest.getBoardEndDate());
    }

    private NoticePost noticePostRegedit(NoticeRequest noticeRequest){
        LocalDateTime now = LocalDateTime.now();
        NoticePost noticePost = NoticePost.builder()
                .title(noticeRequest.getTitle())
                .imageUrl(noticeRequest.getImgUrl())
                .body(noticeRequest.getBody())
                .boardStartDate(noticeRequest.getBoardStartDate())
                .boardEndDate(noticeRequest.getBoardEndDate())
                // TODO [Session] 세션에서 ID 가져와서 저장
                .adminId("dummyAdminId")
                .createdDate(now)
                .lastModifiedDate(now)
                .build();
        return noticePost;
    }
}
