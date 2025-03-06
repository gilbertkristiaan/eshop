package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
    }

    @Test
    void testPaymentCreationWithDefaultStatus() {
        Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", "VOUCHER", this.paymentData);
        assertEquals("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("REJECTED", payment.getStatus());
        assertSame(this.paymentData, payment.getPaymentData());
    }

    @Test
    void testPaymentCreationWithSuccessStatus() {
        Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", "VOUCHER", this.paymentData, "SUCCESS");
        assertEquals("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(this.paymentData, payment.getPaymentData());
    }

    @Test
    void testInvalidPaymentStatusThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", "VOUCHER", this.paymentData, "INVALID");
        });
    }

    @Test
    void testEmptyPaymentDataThrowsException() {
        Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", "VOUCHER", this.paymentData);
        this.paymentData.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setPaymentData(this.paymentData);
        });
    }

    @Test
    void testSuccessfulPaymentDataUpdate() {
        Payment payment = new Payment("9a3f7d62-5b1d-4c8e-a2e3-7fbd9e14c6a7", "VOUCHER", this.paymentData);
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment.setPaymentData(this.paymentData);
        assertSame(this.paymentData, payment.getPaymentData());
    }
}
