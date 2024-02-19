package com.send.email.services.models;

import java.util.HashMap;
import java.util.Map;

public class CorreoRequest {
    private String destinatario;
    private String asunto;

    private Map<String, Object> dataBinding = new HashMap<>();
    private String templateEmail;



    public Map<String, Object> getDataBinding() {
        return dataBinding;
    }

    public void setDataBinding(Map<String, Object> dataBinding) {
        this.dataBinding = dataBinding;
    }

    public String getTemplateEmail() {
        return templateEmail;
    }

    public void setTemplateEmail(String templateEmail) {
        this.templateEmail = templateEmail;
    }


    public CorreoRequest() {
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }


}

