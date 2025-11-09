package com.github.diogenes.bighouse.api.service.storage;

import com.github.diogenes.bighouse.api.core.storage.StorageProperties;
import com.github.diogenes.bighouse.api.model.Idolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class S3StorageService {

    @Value("${URL_PUBLICA}")
    private String caminhoUrlPublica;


    private final S3Client s3Client;
    private final StorageProperties storageProperties;


    public S3StorageService(@Autowired S3Client s3Client, @Autowired StorageProperties storageProperties) {
        this.s3Client = s3Client;
        this.storageProperties = storageProperties;
    }

    public String armazenar(MultipartFile file) throws IOException {
        Foto foto  = criarFoto(file);
        String CaminhoAbsoluto = getCaminhoAbsoluto(foto.getNome());


        PutObjectRequest putObject = PutObjectRequest.builder()
                .bucket(storageProperties.getS3Amazon().getBuckets())
                .key(CaminhoAbsoluto)
                .contentType(foto.getContentType())
                .build();

        try (InputStream in = foto.getInputStream()) {
            s3Client.putObject(putObject, RequestBody.fromInputStream(in, foto.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler stream do arquivo", e);
        }

        return urlPublica(foto);
    }

    private String urlPublica(Foto foto) {
        return caminhoUrlPublica + foto.getNome();
    }


    public void deletar(String nomeFoto) {
        String caminhoAbsoluto = getCaminhoAbsoluto(nomeFoto);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(storageProperties.getS3Amazon().getBuckets())
                .key(caminhoAbsoluto)
                .build();

        s3Client.deleteObject(deleteObjectRequest);

    }


    public void atualizar(Idolo idolo , MultipartFile fotoNova) throws IOException {
        deletar(idolo.retornaNomeImage());
        String fotoArmazenada = armazenar(fotoNova);
        idolo.setImageUrl(fotoArmazenada);

    }





    private String getCaminhoAbsoluto(String nome) {
        return storageProperties.getS3Amazon().getDiretorio() +"/"+ nome;
    }

    private Foto criarFoto(MultipartFile file) throws IOException {
        return Foto.builder()
                .nome(UUID.randomUUID().toString()+"_"+file.getOriginalFilename())
                .contentType(file.getContentType())
                .inputStream(file.getInputStream())
                .size(file.getSize())
                .build();
    }
}
