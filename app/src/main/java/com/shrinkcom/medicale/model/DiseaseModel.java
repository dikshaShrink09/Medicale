package com.shrinkcom.medicale.model;

public class DiseaseModel {
    private String disease_id, disease_name,disease_icon;

    public DiseaseModel() {
    }

    public String getDisease_id() {
        return disease_id;
    }

    public void setDisease_id(String disease_id) {
        this.disease_id = disease_id;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getDisease_icon() {
        return disease_icon;
    }

    public void setDisease_icon(String disease_icon) {
        this.disease_icon = disease_icon;
    }
}
