package com.example.be.DTO.payment;

import java.io.Serializable;

public class PaymentResDTO implements Serializable {
    public String status;
    public String msg;
    public String url;

    public PaymentResDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}