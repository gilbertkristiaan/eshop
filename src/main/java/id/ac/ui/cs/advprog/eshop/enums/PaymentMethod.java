package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    VOUCHER("VOUCHER"),
    BANK_TRANSFER("BANK_TRANSFER");
    private final String type;

    private PaymentMethod(String type) {
        this.type = type;
    }

    public static boolean isContain(String input) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.name().equals(input)) {
                return true;
            }
        }
        return false;
    }
}
