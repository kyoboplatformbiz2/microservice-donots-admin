package com.kyobo.platform.donots.config;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Configuration
@Slf4j
public class AWSConfig {

    public Properties load_property() throws IOException {
        Properties properties = new Properties();
        Resource newResource = new ClassPathResource("awsAuth.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(newResource.getInputStream()));
        properties.load(br);
        return properties;
    }

    //s3 사용을 위한 인증
    public AmazonS3 amazonS3(String region, String env_active) throws IOException {
//        String accessKey = load_property().getProperty("accessKey");
//        String secretKey = load_property().getProperty("secretKey");
//        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        return amazonS3;
    }
}
