package com.ifpb.VivaAqui.controller;

public class UpdateStatusRequest {
    private String status;
    private String cpf;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}