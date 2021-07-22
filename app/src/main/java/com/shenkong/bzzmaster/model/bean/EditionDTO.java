package com.shenkong.bzzmaster.model.bean;

public class EditionDTO {
    private Integer edition;
    private String firm;

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    @Override
    public String toString() {
        return "EditionDTO{" +
                "edition=" + edition +
                ", firm='" + firm + '\'' +
                '}';
    }
}
