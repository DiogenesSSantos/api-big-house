package com.github.diogenes.bighouse.api.controller;


import com.github.diogenes.bighouse.api.model.Idolo;
import com.github.diogenes.bighouse.api.service.IdoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.springframework.http.MediaType.IMAGE_PNG;

@RestController
public class IdoloController {

    @Autowired
    private IdoloService service;


    @GetMapping
    public ResponseEntity<?> buscarTodas() throws IOException {


        InputStream in = IdoloController.class.getResourceAsStream("/image/Captura de tela 2025-10-25 230354.png");
        Idolo idolo = new Idolo.builder()
                .setFrase("Dioge")
                .setImage(in != null ? in.readAllBytes() : null)
                .build();

        service.salvar(idolo);

        return ResponseEntity.ok().body("Salvo");
    }


    @GetMapping(value = "/buscar-foto/{id}" , produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> buscarFoto(@PathVariable ("id") Long id) {
        Idolo IdoloLocalizado = service.buscarPorId(id);

        System.out.println(IdoloLocalizado.getFrase());

        return ResponseEntity.ok()
                .contentType(IMAGE_PNG)
                .body(IdoloLocalizado.getImage());
    }



    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Idolo idolo) {
        Idolo idoloSalvo = service.salvar(idolo);
        return ResponseEntity.ok(idoloSalvo);
    }





}
