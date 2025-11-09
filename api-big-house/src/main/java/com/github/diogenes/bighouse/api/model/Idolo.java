package com.github.diogenes.bighouse.api.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "tb_idolo")
public class Idolo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String frase;

    @Column(name = "image_url")
    private String imageUrl;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_criacao")
    private LocalDate data = LocalDate.now();

    private Idolo(){}
    public Idolo(String frase, String imageUrl) {
        this.frase = frase;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Idolo idolo = (Idolo) o;
        return Objects.equals(id, idolo.id) && Objects.equals(frase, idolo.frase) && Objects.equals(imageUrl, idolo.imageUrl) && Objects.equals(data, idolo.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(frase);
        result = 31 * result + Objects.hashCode(imageUrl);
        result = 31 * result + Objects.hashCode(data);
        return result;
    }

    public static class builder {

        private String frase;
        private String image;
        private LocalDate data = LocalDate.now();


        public Idolo.builder setFrase (String frase) {
           this.frase =  frase;
           return this;
        }

        public Idolo.builder setImage(String image) {
            this.image = image;
            return this;
        }

        public Idolo build(){
            return new Idolo(this.frase , this.image);
        }

    }



}
