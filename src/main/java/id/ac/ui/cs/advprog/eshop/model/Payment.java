package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
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
        this.setMethod(method);
        this.paymentData = paymentData;
        this.status = "REJECTED";
        validateData();
    }

    private void setMethod(String method) {
        if (PaymentMethod.isContain(method)) {
            this.method = method;
        } else {
            throw new IllegalArgumentException();
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

    private void validateData() {
        boolean isValid = false;
        if ("VOUCHER".equals(this.method)) {
            isValid = validateVoucherCode();
        } else if ("CASH_ON_DELIVERY".equals(this.method)) {
            isValid = true;
        }

        this.status = isValid ? "SUCCESS" : "REJECTED";
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.paymentData = paymentData;
        }
    }
}
