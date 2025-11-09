package com.github.diogenes.bighouse.api.core.storage;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("idolo-api.storage")
public class StorageProperties {

    private S3Amazon s3Amazon =  new S3Amazon();

    @Setter
    @Getter
    public  class S3Amazon {
        private  String chaveAcesso;
        private  String chaveSecreta;
        private  String buckets;
        private  String region;
        private  String diretorio;
    }



}
