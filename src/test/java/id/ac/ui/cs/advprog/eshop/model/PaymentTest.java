package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private String validPaymentId;
    private Map<String, String> validVoucherData;
    private Map<String, String> validCodData;

    @BeforeEach
    void setUp() {
        validPaymentId = "gk-123";
        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP12345678AB");
        validCodData = new HashMap<>();
        validCodData.put("address", "Jl. Margonda Raya No. 100");
        validCodData.put("deliveryFee", "15000");
    }

    @Test
    void testCreatePaymentWithValidVoucher() {
        validVoucherData.put("voucherCode", "ESHOP12345678ABC");
        Payment payment = new Payment(validPaymentId, PaymentMethod.VOUCHER.getValue(), validVoucherData);
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals(validPaymentId, payment.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithInvalidVoucher() {
        Map<String, String> invalidVoucher = new HashMap<>();
        invalidVoucher.put("voucherCode", "INVALID123");

        Payment payment = new Payment(validPaymentId, PaymentMethod.VOUCHER.getValue(), invalidVoucher);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithValidCashOnDelivery() {
        Payment payment = new Payment(validPaymentId, PaymentMethod.CASH_ON_DELIVERY.getValue(), validCodData);
        assertEquals(PaymentMethod.CASH_ON_DELIVERY.getValue(), payment.getMethod());
        assertEquals(validPaymentId, payment.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithEmptyAddress() {
        Map<String, String> emptyAddressData = new HashMap<>();
        emptyAddressData.put("address", "");
        emptyAddressData.put("deliveryFee", "15000");
        Payment payment = new Payment(validPaymentId, PaymentMethod.CASH_ON_DELIVERY.getValue(), emptyAddressData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithEmptyDeliveryFee() {
        Map<String, String> emptyFeeData = new HashMap<>();
        emptyFeeData.put("address", "Jl. Margonda Raya No. 100");
        emptyFeeData.put("deliveryFee", "");
        Payment payment = new Payment(validPaymentId, PaymentMethod.CASH_ON_DELIVERY.getValue(), emptyFeeData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithMissingAddress() {
        Map<String, String> missingAddressData = new HashMap<>();
        missingAddressData.put("deliveryFee", "15000");
        Payment payment = new Payment(validPaymentId, PaymentMethod.CASH_ON_DELIVERY.getValue(), missingAddressData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithMissingDeliveryFee() {
        Map<String, String> missingFeeData = new HashMap<>();
        missingFeeData.put("address", "Jl. Margonda Raya No. 100");
        Payment payment = new Payment(validPaymentId, PaymentMethod.CASH_ON_DELIVERY.getValue(), missingFeeData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithNonexistentMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(validPaymentId, "CREDIT_CARD", validCodData);
        });
    }

    @Test
    void testCreatePaymentWithEmptyData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(validPaymentId, PaymentMethod.CASH_ON_DELIVERY.getValue(), new HashMap<>());
        });
    }

    @Test
    void testSetPaymentStatus() {
        Payment payment = new Payment(validPaymentId, PaymentMethod.VOUCHER.getValue(), validVoucherData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetInvalidPaymentStatus() {
        Payment payment = new Payment(validPaymentId, PaymentMethod.VOUCHER.getValue(), validVoucherData);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("PENDING");
        });
    }

    @Test
    void testSetPaymentData() {
        Payment payment = new Payment(validPaymentId, PaymentMethod.CASH_ON_DELIVERY.getValue(), validCodData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        Map<String, String> invalidData = new HashMap<>();
        invalidData.put("address", "");
        invalidData.put("deliveryFee", "15000");
        payment.setPaymentData(invalidData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        payment.setPaymentData(validCodData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSecondConstructor() {
        Payment payment = new Payment(validPaymentId, PaymentMethod.CASH_ON_DELIVERY.getValue(), validCodData, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        Payment rejectedPayment = new Payment(validPaymentId, PaymentMethod.CASH_ON_DELIVERY.getValue(), validCodData, PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), rejectedPayment.getStatus());
    }
}