package com.github.diogenes.bighouse.api.model;


import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "tb_idolo")
public class Idolo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String frase;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] image;


    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate data = LocalDate.now();


    private Idolo(){}


    private Idolo(String frase , byte [] image) {
        this.frase = frase;
        this.image = image;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Idolo idolo = (Idolo) o;
        return Objects.equals(id, idolo.id) && Objects.equals(frase, idolo.frase) && Arrays.equals(image, idolo.image) && Objects.equals(data, idolo.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(frase);
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + Objects.hashCode(data);
        return result;
    }


    @Override
    public String toString() {
        return "Idolo{" +
                "id=" + id +
                ", frase='" + frase + '\'' +
                ", image=" + Arrays.toString(image) +
                ", data=" + data +
                '}';
    }


    public Long getId() {
        return id;
    }

    public String getFrase() {
        return frase;
    }

    public byte[] getImage() {
        return image;
    }

    public LocalDate getData() {
        return data;
    }

    public static class builder {

        private String frase;
        private byte[] image;
        private LocalDate data = LocalDate.now();


        public Idolo.builder setFrase (String frase) {
           this.frase =  frase;
           return this;
        }

        public Idolo.builder setImage(byte[] image) {
            this.image = image;
            return this;
        }

        public Idolo build(){
            return new Idolo(this.frase , this.image);
        }

    }



}
