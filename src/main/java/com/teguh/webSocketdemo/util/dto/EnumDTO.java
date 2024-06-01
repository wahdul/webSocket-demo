package com.teguh.webSocketdemo.util.dto;

public class EnumDTO {
    public String val;
    public String text;

    public EnumDTO(String val, String text) {
        this.val = val;
        this.text = text;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
