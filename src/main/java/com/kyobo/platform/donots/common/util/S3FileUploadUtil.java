package com.kyobo.platform.donots.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kyobo.platform.donots.common.exception.BusinessException;
import com.kyobo.platform.donots.config.AWSConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.Properties;

@Slf4j
@Component
public class S3FileUploadUtil {

    // FIXME 프로퍼티 읽어오도록
//    @Value("${spring.profiles.active}")
    private String env_active;

//    @Value("${cloud.aws.region.static}")
    private String region;
    private String backendImageBucket;
    private String distributionDomain;

    private AWSConfig awsConfig = new AWSConfig();
    private AmazonS3 amazonS3;

    private void loadAndSetAwsAccessInfo() throws IOException {
        // TODO 임시 하드코딩
        env_active = "dev";
        region = "ap-northeast-2";
        backendImageBucket = loadProperty().getProperty("backendImageBucket");
        distributionDomain = loadProperty().getProperty("distributionDomain");
        amazonS3 = awsConfig.amazonS3(region, env_active);
    }

    public Properties loadProperty() throws IOException {
        Properties properties = new Properties();
        Resource newResource = new ClassPathResource("awsAuth.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(newResource.getInputStream()));
        properties.load(br);

        return properties;
    }

    public String uploadImageToS3AndGetUrl(MultipartFile multipartFile, String asIsImageUrl, String imageDirectoryPathAfterDomain) throws IOException, DecoderException {
        loadAndSetAwsAccessInfo();

        String toBeImageFileName = multipartFile.getOriginalFilename();
        if (!isUrlLengthAvailable(
                buildFullUrlWithEncodedFileName(distributionDomain, imageDirectoryPathAfterDomain, toBeImageFileName))
        ) {
            throw new BusinessException("업로드 하려는 파일명이 너무 깁니다");
        }

        String[] dotSplittedImageFileName = toBeImageFileName.split("\\.");
        String fileExt = dotSplittedImageFileName[dotSplittedImageFileName.length - 1];

        if (!StringUtils.isBlank(asIsImageUrl)) {
            String[] slashSplittedAsIsImageUrl = asIsImageUrl.split("/");
            String asIsImageFileName = slashSplittedAsIsImageUrl[slashSplittedAsIsImageUrl.length - 1];
            URLCodec uc = new URLCodec();
            String decodedAsIsImageFileName = uc.decode(asIsImageFileName, "UTF-8");
            amazonS3.deleteObject(backendImageBucket, imageDirectoryPathAfterDomain + decodedAsIsImageFileName);
        }

        File toBeImageFile = compressImageFile(multipartFile, toBeImageFileName, fileExt);

        // S3 업로드
        amazonS3.putObject(new PutObjectRequest(backendImageBucket, imageDirectoryPathAfterDomain + toBeImageFileName, toBeImageFile));

        // 업로드 후 메모리에 있는 이미지파일 삭제
        toBeImageFile.delete();

        // S3에 업로드한 이미지 URL을 파일명만 URL 인코딩하여 반환
        String encodedToBeProfilePictureUrl = buildFullUrlWithEncodedFileName(distributionDomain, imageDirectoryPathAfterDomain, toBeImageFileName);
        return encodedToBeProfilePictureUrl;
    }

    public void deleteImageFromS3(String asIsImageUrl, String imageDirectoryPathAfterDomain) throws IOException, DecoderException {
        loadAndSetAwsAccessInfo();

        // TODO 삭제 오류시 익셉션 정의
        // 최초 등록이 아닐 경우 기존 이미지를 삭제한다.
        // 엔티티의 profilePictureUrl 필드는 뒤에서 덮어씌울(set) 것이므로 굳이 여기서 삭제하지는 않는다.
        if (!StringUtils.isBlank(asIsImageUrl)) {
            // URL에서 파일명 추출
            String[] slashSplittedAsIsImageUrl = asIsImageUrl.split("/");
            String asIsImageFileName = slashSplittedAsIsImageUrl[slashSplittedAsIsImageUrl.length - 1];

            URLCodec uc = new URLCodec();
            String decodedAsIsImageFileName = uc.decode(asIsImageFileName, "UTF-8");
            amazonS3.deleteObject(backendImageBucket, imageDirectoryPathAfterDomain + decodedAsIsImageFileName);
        }
    }

    public static boolean isUrlLengthAvailable(String fullUrl) {

        // Internet Explorer에서 입력 가능한 최대 URL 길이. (GET Method 사용)
        // 다른 브라우저는 이것보다 더 길기 때문에 가장 짧은 Internet Explorer를 기준으로 했다.
        final int MAX_URL_LENGTH_INTERNET_EXPLORER = 2048;
        if (fullUrl.length() <= MAX_URL_LENGTH_INTERNET_EXPLORER)
            return true;
        else
            return false;
    }

    public static String buildFullUrlWithEncodedFileName(String domain, String fileDirectoryPathAfterDomain, String fileName) throws UnsupportedEncodingException {
        URLCodec uc = new URLCodec();
        String encodedFileName = uc.encode(fileName, "UTF-8");
        return "https://" + domain + "/" + fileDirectoryPathAfterDomain + encodedFileName;
    }

    private static File compressImageFile(MultipartFile multipartFile, String imageFileName, String fileExt) throws IOException {
        File imageFile = new File(imageFileName);
        OutputStream os = new FileOutputStream(imageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(fileExt);
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        writer.write(new IIOImage(bufferedImage, null, null));

        os.close();
        ios.close();
        writer.dispose();
        return imageFile;
    }
}
