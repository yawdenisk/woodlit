package org.yawdenisk.woodlit.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
@Configuration
public class S3Configuration {
    String accessKey = "AKIAQXUIXKGABJPDMUPV";
    String secretKey = "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";
    AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
    String regionName = "eu-north-1";
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(regionName))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
