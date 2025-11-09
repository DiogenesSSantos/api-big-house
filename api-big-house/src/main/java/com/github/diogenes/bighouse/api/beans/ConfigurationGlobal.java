package com.github.diogenes.bighouse.api.beans;


import com.github.diogenes.bighouse.api.core.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.internal.s3express.DefaultS3ExpressIdentityProvider;

@Configuration
public class ConfigurationGlobal {

    private final StorageProperties storageProperties;

    public ConfigurationGlobal(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    public S3Client s3Client() {
        var s3props = storageProperties.getS3Amazon();

        if (s3props.getChaveAcesso() == null || s3props.getChaveAcesso().isBlank()) {
            throw new IllegalStateException("storage.s3Amazon.chave-acesso não configurada");
        }

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                s3props.getChaveAcesso().trim(),
                s3props.getChaveSecreta().trim()
        );

        return S3Client.builder()
                .region(Region.of(s3props.getRegion() == null ? "us-east-1" : s3props.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }



}
