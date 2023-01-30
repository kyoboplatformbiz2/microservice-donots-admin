package com.kyobo.platform.donots.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

import com.kyobo.platform.donots.common.exception.AdminUserNotFoundException;
import com.kyobo.platform.donots.common.util.S3FileUploadUtil;

import com.kyobo.platform.donots.model.entity.AdminUser;
import com.kyobo.platform.donots.model.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.DecoderException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class S3ImageService {

    private final AdminUserRepository adminUserRepository;
    private final S3FileUploadUtil s3FileUploadUtil;

    @Transactional
    public String uploadAdminImage(String adminId, MultipartFile multipartFile) throws IOException, SdkClientException, DecoderException {

        AdminUser adminUser = adminUserRepository.findByAdminId(adminId);
        if (adminUser == null) {
            throw new AdminUserNotFoundException();
        }

        String domain = "adminUser/" + adminId + "/";
        String attachImageUrl = s3FileUploadUtil.uploadImageToS3AndGetUrl(multipartFile, adminUser.getAttachImageUrl(), domain);

        // 업로드된 이미지 URL을 DB에 저장
        return attachImageUrl;
    }

    @Transactional
    public void deleteAdminImage(String adminId) throws IOException, SdkClientException, DecoderException {

        AdminUser adminUser = adminUserRepository.findByAdminId(adminId);
        if (adminUser == null) {
            throw new AdminUserNotFoundException();
        }

        String domain = "adminUser/" + adminId + "/";

        s3FileUploadUtil.deleteImageFromS3(adminUser.getAttachImageUrl(), domain);
        adminUser.updateAttachImageUrl("");
    }
}
