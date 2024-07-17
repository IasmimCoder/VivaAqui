package com.ifpb.VivaAqui.model;

import org.springframework.stereotype.Component;

@Component
public class Message {

    private String mensagem;

    public Message() {

    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
