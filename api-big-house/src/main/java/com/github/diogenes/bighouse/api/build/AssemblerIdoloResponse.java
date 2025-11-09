package com.github.diogenes.bighouse.api.build;

import com.github.diogenes.bighouse.api.controller.response.IdoloResponse;
import com.github.diogenes.bighouse.api.model.Idolo;
import com.github.diogenes.bighouse.api.repository.IdoloRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class AssemblerIdoloResponse {




    public static IdoloResponse modelToResponse(Idolo idolo) {
        return new IdoloResponse(idolo.getFrase(), idolo.getImageUrl(), formatarData(idolo.getData()));
    }

    public static List<IdoloResponse> listModelToListResponse(List<Idolo> idolos) {
        List<IdoloResponse> idoloResponses = idolos.stream()
                .map(idolo -> modelToResponse(idolo))
                .toList();
        return idoloResponses;
    }




    /**
     * @param data sendo recebida a data como parâmetro
     * @return data formatada em dia/mes/ano
     */
    private static String formatarData(LocalDate data) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = data.format(fmt);
        return dataFormatada;
    }

}
