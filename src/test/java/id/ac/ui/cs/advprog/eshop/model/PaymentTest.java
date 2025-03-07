package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
    }

    @Test
    void testPaymentCreationWithDefaultStatus() {
        Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", PaymentMethod.VOUCHER.getValue(), this.paymentData);
        assertEquals("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(this.paymentData, payment.getPaymentData());
    }

    @Test
    void testPaymentCreationWithSuccessStatus() {
        Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", PaymentMethod.VOUCHER.getValue(), this.paymentData, "SUCCESS");
        assertEquals("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(this.paymentData, payment.getPaymentData());
    }

    @Test
    void testInvalidPaymentStatusThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", PaymentMethod.VOUCHER.getValue(), this.paymentData, "INVALID");
        });
    }

    @Test
    void testEmptyPaymentDataThrowsException() {
        Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", PaymentMethod.VOUCHER.getValue(), this.paymentData);
        this.paymentData.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setPaymentData(this.paymentData);
        });
    }

    @Test
    void testSuccessfulPaymentDataUpdate() {
        Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", PaymentMethod.VOUCHER.getValue(), this.paymentData);
        this.paymentData.put("voucherCode", "ESHOP0123ABC4567");
        payment.setPaymentData(this.paymentData);
        assertSame(this.paymentData, payment.getPaymentData());
    }

    @Test
    void testValidVoucher() {
        Payment payment = new Payment("f5a1d2c3-b456-789e-0123-456789abcdef", PaymentMethod.VOUCHER.getValue(),
                paymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidPaymentSubFeature() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("notVoucher", "ISHOP1234ABC5678");
        Payment payment = new Payment("f5a1d2c3-b456-789e-0123-456789abcdef", PaymentMethod.VOUCHER.getValue(),
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherLengthInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "1");
        Payment payment = new Payment("f5a1d2c3-b456-789e-0123-456789abcdef", PaymentMethod.VOUCHER.getValue(),
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherWrongPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ISHOP1234ABC5678");
        Payment payment = new Payment("f5a1d2c3-b456-789e-0123-456789abcdef", PaymentMethod.VOUCHER.getValue(),
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherMissingNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");
        Payment payment = new Payment("f5a1d2c3-b456-789e-0123-456789abcdef", PaymentMethod.VOUCHER.getValue(),
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNullValue() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("f5a1d2c3-b456-789e-0123-456789abcdef", PaymentMethod.VOUCHER.getValue(),
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testValidBankTransferUpdated() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "FREE");
        Payment payment = new Payment("a1b2c3d4-e5f6-7890-abcd-1234567890ef", "BANK_TRANSFER",
                paymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testBankTransferEmptyNameOrRefCodeUpdated() {
        Map<String, String> paymentDataEmptyCode = new HashMap<>();
        paymentDataEmptyCode.put("bankName", "BCA");
        paymentDataEmptyCode.put("referenceCode", "");
        Payment paymentEmptyCode = new Payment("9f8e7d6c-5b4a-3210-fedc-ba9876543210", "BANK_TRANSFER",
                paymentDataEmptyCode);

        Map<String, String> paymentDataEmptyName = new HashMap<>();
        paymentDataEmptyName.put("bankName", "");
        paymentDataEmptyName.put("referenceCode", "FREE");
        Payment paymentEmptyName = new Payment("3e4d5c6b-7a8f-9102-bcda-234567890123", "BANK_TRANSFER",
                paymentDataEmptyName);

        assertEquals(PaymentStatus.REJECTED.getValue(), paymentEmptyCode.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), paymentEmptyName.getStatus());
    }

    @Test
    void testBankTransferNullNameOrRefCodeUpdated() {
        Map<String, String> paymentDataNullCode = new HashMap<>();
        paymentDataNullCode.put("bankName", "BCA");
        paymentDataNullCode.put("referenceCode", null);
        Payment paymentNullCode = new Payment("6a5b4c3d-2e1f-0987-dcba-567890123456", "BANK_TRANSFER",
                paymentDataNullCode);

        Map<String, String> paymentDataNullName = new HashMap<>();
        paymentDataNullName.put("bankName", null);
        paymentDataNullName.put("referenceCode", "FREE");
        Payment paymentNullName = new Payment("1a2b3c4d-5e6f-7890-abcd-0987654321fe", "BANK_TRANSFER",
                paymentDataNullName);

        assertEquals(PaymentStatus.REJECTED.getValue(), paymentNullCode.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), paymentNullName.getStatus());
    }

    @Test
    void testBankTransferInvalidDataUpdated() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP0123ABC4567");
        Payment payment = new Payment("4f3e2d1c-0b9a-8765-fedc-ba1234567890", "BANK_TRANSFER",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testValidCashOnDelivery() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("codConfirmation", "CONFIRMED");
        Payment payment = new Payment("b6f4179f-d120-45ee-9324-157466aec4ff", "CASH_ON_DELIVERY",
                paymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCashOnDeliveryEmptyConfirmation() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("codConfirmation", "");
        Payment payment = new Payment("c9d3179f-e210-45ee-9224-157466dec4ff", "CASH_ON_DELIVERY",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCashOnDeliveryNullConfirmation() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("codConfirmation", null);
        Payment payment = new Payment("a2c3179f-f210-45ee-9224-157466dec4ff", "CASH_ON_DELIVERY",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCashOnDeliveryInvalidData() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("d8e2179f-c210-45ee-9224-157466dec4ff", "CASH_ON_DELIVERY",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

}
