package com.github.diogenes.bighouse.api.controller;


import com.github.diogenes.bighouse.api.build.AssemblerIdoloResponse;
import com.github.diogenes.bighouse.api.controller.response.IdoloResponse;
import com.github.diogenes.bighouse.api.model.Idolo;
import com.github.diogenes.bighouse.api.service.IdoloService;
import com.github.diogenes.bighouse.api.service.storage.S3StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * @author DiogenesSantos
 * Classe controller para requisições da api-rest Cliente-serve.
 *
 */



@Slf4j
@RestController
public class IdoloController {

    private IdoloService idoloService;
    private S3StorageService storageService;

    public IdoloController(@Autowired IdoloService idoloService, @Autowired S3StorageService storageService) {
        this.idoloService = idoloService;
        this.storageService = storageService;
    }



    @GetMapping
    public ResponseEntity<List<IdoloResponse>> buscarTodos() {
        List<Idolo> idolos = idoloService.buscarTodos();
        List<IdoloResponse> idoloResponses = AssemblerIdoloResponse.listModelToListResponse(idolos);
        return ResponseEntity.ok().body(idoloResponses);
    }



    @GetMapping(value = "/{id}")
    public ResponseEntity<IdoloResponse> buscarFoto(@PathVariable ("id") Long id) {
        Idolo idoloLocalizado = idoloService.buscarPorId(id);
        IdoloResponse idoloResponse = AssemblerIdoloResponse.modelToResponse(idoloLocalizado);
        return ResponseEntity.status(HttpStatus.OK)
                .body(idoloResponse);
    }



    @PostMapping
    public ResponseEntity<IdoloResponse> salvar(@RequestParam(value = "frase" , required = true) String frase ,
                                    @RequestParam(value = "foto", required = true) MultipartFile foto) throws IOException {
        var urlPublicFoto = storageService.armazenar(foto);
        var idolo = instancieIdolo(frase , urlPublicFoto);
        var idoloSalvoNoBD = idoloService.salvar(idolo);
        var IdoloResponse = AssemblerIdoloResponse.modelToResponse(idoloSalvoNoBD);

        return ResponseEntity.ok().body(IdoloResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<IdoloResponse> atualizar(@PathVariable("id") Long id , @RequestParam(value = "fotoNova" ,
            required = true) MultipartFile fotoNova) throws IOException {

        Idolo idoloLocaliado = idoloService.buscarPorId(id);
        storageService.atualizar(idoloLocaliado , fotoNova);
        idoloService.atualizar(idoloLocaliado);

        IdoloResponse idoloResponse = AssemblerIdoloResponse.modelToResponse(idoloLocaliado);


        return ResponseEntity.ok().body(idoloResponse);
    }






    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id){
        Idolo idoloLocalizado = idoloService.buscarPorId(id);
        storageService.deletar(idoloLocalizado.retornaNomeImage());
        idoloService.deletar(idoloLocalizado);

        return ResponseEntity.noContent().build();
    }






    /**
     *
     * @param frase frase impactante do idolo para ser persistida no banco de dados
     * @return retornado um objeto de idolo
     * @throws IOException ao trabalhar com Input e Output somos obrigados a tratar exception.
     */
    private static Idolo instancieIdolo(String frase, String caminho) throws IOException {
        return new Idolo.builder().setFrase(frase)
                .setImage(caminho)
                .build();
    }


}
