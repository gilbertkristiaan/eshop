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

        if ("VOUCHER".equals(method) && validateVoucherCode()) {
            this.status = "SUCCESS";
        }
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

    private boolean validateVoucherCode() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        String code = voucherCode.substring(5);
        int numericCharCount = 0;
        for (char character : code.toCharArray()) {
            if (Character.isDigit(character)) {
                numericCharCount++;
            }
        }

        return numericCharCount == 8;
    }
}
