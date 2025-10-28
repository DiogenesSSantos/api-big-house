package com.github.diogenes.bighouse.api.controller;


import com.github.diogenes.bighouse.api.model.Idolo;
import com.github.diogenes.bighouse.api.service.IdoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.MediaType.IMAGE_PNG;

@RestController
public class IdoloController {

    @Autowired
    private IdoloService service;


    @GetMapping
    public ResponseEntity<?> buscarTodas() throws IOException {


        return ResponseEntity.ok().body("Salvo");
    }


    @GetMapping(value = "/buscar-foto/{id}" , produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> buscarFoto(@PathVariable ("id") Long id) {
        Idolo IdoloLocalizado = service.buscarPorId(id);

        return ResponseEntity.ok()
                .contentType(IMAGE_PNG)
                .body(IdoloLocalizado.getImage());
    }



    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Idolo idolo) {
        Idolo idoloSalvo = service.salvar(idolo);
        return ResponseEntity.ok(idoloSalvo);
    }


    @PostMapping("/salva-postman")
    public ResponseEntity<?> salvarPostman(String frase ,
                                           @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        var idolo = instancieIdolo(frase, file);
        service.salvar(idolo);

        return ResponseEntity.ok().body(idolo);
    }


    /**
     *
     * @param frase frase impactante do idolo para ser persistida no banco de dados
     * @param file é imagen, não e obrigatório
     * @return retornado um objeto de idolo
     * @throws IOException ao trabalhar com Input e Output somos obrigados a tratar exception.
     */
    private static Idolo instancieIdolo(String frase, MultipartFile file) throws IOException {
        return new Idolo.builder().setFrase(frase)
                .setImage(file == null ? null : file.getBytes())
                .build();
    }


}
