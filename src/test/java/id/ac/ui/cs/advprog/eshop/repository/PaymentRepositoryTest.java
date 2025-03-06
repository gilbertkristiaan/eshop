package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> paymentList;
    Order orderInstance;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentList = new ArrayList<>();
        Map<String, String> voucherInfo1 = new HashMap<>();
        voucherInfo1.put("voucherCode", "ESHOP5678XYZ1234");
        Payment firstPayment = new Payment("a1b2c3d4-e5f6-7890-abcd-ef1234567890", "VOUCHER", voucherInfo1);
        paymentList.add(firstPayment);
        Map<String, String> voucherInfo2 = new HashMap<>();
        voucherInfo2.put("voucherCode", "ESHOP5678XYZ1235");
        Payment secondPayment = new Payment("0987abcd-6543-21ef-ba98-fedcba987654", "VOUCHER", voucherInfo2);
        paymentList.add(secondPayment);
        orderInstance = new Order("123e4567-e89b-12d3-a456-426614174000",
                null, 1708560000L, "User123");
    }

    @Test
    void testSaveNewPayment() {
        Payment payment = paymentList.get(0);
        Payment storedPayment = paymentRepository.save(orderInstance, payment);
        Payment retrievedPayment = paymentRepository.findById(paymentList.get(0).getId());
        assertEquals(payment.getId(), storedPayment.getId());
        assertEquals(payment.getStatus(), retrievedPayment.getStatus());
        assertEquals(payment.getMethod(), retrievedPayment.getMethod());
        assertSame(payment.getPaymentData(), retrievedPayment.getPaymentData());
    }

    @Test
    void testUpdatePaymentStatus() {
        Payment payment = paymentList.get(0);
        Payment savedPayment = paymentRepository.save(orderInstance, payment);
        assertEquals("SUCCESS", savedPayment.getStatus());
        assertEquals("SUCCESS", orderInstance.getStatus());
        paymentRepository.update(payment, "REJECTED");
        Payment updatedPayment = paymentRepository.findById(paymentList.get(0).getId());
        Order updatedOrder = paymentRepository.getOrder(updatedPayment.getId());
        assertEquals("REJECTED", updatedPayment.getStatus());
        assertEquals("FAILED", updatedOrder.getStatus());
    }

    @Test
    void testInvalidStatusUpdate() {
        Payment payment = paymentList.get(0);
        paymentRepository.save(orderInstance, payment);
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.update(payment, "INVALID_STATUS");
        });
    }

    @Test
    void testFindPaymentByIdIfExists() {
        for (Payment payment : paymentList) {
            paymentRepository.save(orderInstance, payment);
        }
        Payment foundPayment = paymentRepository.findById(paymentList.get(0).getId());
        assertEquals(paymentList.get(0).getId(), foundPayment.getId());
        assertEquals(paymentList.get(0).getMethod(), foundPayment.getMethod());
        assertEquals(paymentList.get(0).getStatus(), foundPayment.getStatus());
        assertSame(paymentList.get(0).getPaymentData(), foundPayment.getPaymentData());
    }

    @Test
    void testFindPaymentByIdIfNotExists() {
        for (Payment payment : paymentList) {
            paymentRepository.save(orderInstance, payment);
        }
        Payment nonexistentPayment = paymentRepository.findById("nonexistent-id");
        assertNull(nonexistentPayment);
    }

    @Test
    void testRetrieveAllPayments() {
        for (Payment payment : paymentList) {
            paymentRepository.save(orderInstance, payment);
        List<Payment> retrievedPayments = paymentRepository.findAll();
        assertEquals(2, retrievedPayments.size());
    }

    @Test
    void testFindOrderByPaymentIfExists() {
        Payment payment = paymentList.get(0);
        paymentRepository.save(orderInstance, payment);
        Payment storedPayment = paymentRepository.findById(paymentList.get(0).getId());
        Order associatedOrder = paymentRepository.getOrder(storedPayment.getId());
        assertEquals(orderInstance.getId(), associatedOrder.getId());
        assertEquals(orderInstance.getStatus(), associatedOrder.getStatus());
        assertEquals(orderInstance.getAuthor(), associatedOrder.getAuthor());
        assertEquals(orderInstance.getOrderTime(), associatedOrder.getOrderTime());
        assertEquals(orderInstance.getProducts(), associatedOrder.getProducts());
    }

    @Test
    void testFindOrderByPaymentIfNotExists() {
        Payment payment = paymentList.get(0);
        paymentRepository.save(orderInstance, payment);
        Payment retrievedPayment = paymentRepository.findById(paymentList.get(0).getId());
        Order nonexistentOrder = paymentRepository.getOrder(retrievedPayment.getId());
        assertNull(nonexistentOrder);
    }
}
