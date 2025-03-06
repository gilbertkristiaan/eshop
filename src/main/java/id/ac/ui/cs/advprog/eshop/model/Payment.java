package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    String method;
    Map<String, String> paymentData;
    String id;
    String status;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "REJECTED";
    }

    public Payment(String id, String method, Map<String, String> paymentData, String status) {
        this(id, method, paymentData);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (!status.equals("SUCCESS") && !status.equals("REJECTED")) {
            throw new IllegalArgumentException();
        }
        this.status = status;
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.paymentData = paymentData;
        }
    }
}