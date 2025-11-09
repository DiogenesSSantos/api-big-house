package com.github.diogenes.bighouse.api.service.storage;

import lombok.Getter;

import java.io.InputStream;

@Getter
public class Foto {

    private final String nome;
    private final String contentType;
    private final InputStream inputStream;
    private final long size;

    private Foto(String nome, String contentType, InputStream inputStream, long size) {
        this.nome = nome;
        this.contentType = contentType;
        this.inputStream = inputStream;
        this.size = size;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String nome;
        private String contentType;
        private InputStream inputStream;
        private long size;

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Builder size(long size) {
            this.size = size;
            return this;
        }

        public Foto build() {
            if (nome == null || nome.isBlank()) throw new IllegalStateException("nome é obrigatório");
            if (inputStream == null) throw new IllegalStateException("inputStream é obrigatório");
            if (size <= 0) throw new IllegalStateException("size inválido");
            return new Foto(nome, contentType, inputStream, size);
        }
    }
}
