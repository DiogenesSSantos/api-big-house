package com.github.diogenes.bighouse.api.service;


import com.github.diogenes.bighouse.api.model.Idolo;
import com.github.diogenes.bighouse.api.repository.IdoloRepository;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class IdoloService {
    @Autowired
    private IdoloRepository idoloRepository;

    @Transactional
    public Idolo salvar(Idolo idolo) {
        return idoloRepository.save(idolo);
    }

    @Transactional
    public Idolo buscarPorId(Long id) {
        return idoloRepository.findById(id).get();
    }

    public List<Idolo> buscarTodos() {
        return idoloRepository.findAll();
    }



}
