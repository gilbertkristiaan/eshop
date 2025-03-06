package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepo;

    @Mock
    private OrderService orderService;

    private Order sampleOrder;
    private Map<String, String> firstPaymentDetails;
    private Map<String, String> secondPaymentDetails;
    private List<Payment> paymentRecords;

    @BeforeEach
    void init() {
        sampleOrder = new Order("order-123", generateSampleProducts(), 1708560000L, "JohnDoe");

        firstPaymentDetails = new HashMap<>();
        firstPaymentDetails.put("promoCode", "SALE2024XYZ");

        Payment firstPayment = new Payment("payment-001", "DISCOUNT", firstPaymentDetails);

        secondPaymentDetails = new HashMap<>();
        secondPaymentDetails.put("promoCode", "SALE2024ABC");

        Payment secondPayment = new Payment("payment-002", "DISCOUNT", secondPaymentDetails);

        paymentRecords = new ArrayList<>(List.of(firstPayment, secondPayment));
    }

    private List<Product> generateSampleProducts() {
        List<Product> productSamples = new ArrayList<>();
        Product shampoo = new Product();
        shampoo.setProductId("prod-001");
        shampoo.setProductName("Luxury Shampoo");
        shampoo.setProductQuantity(3);
        productSamples.add(shampoo);
        return productSamples;
    }

    @Test
    void shouldAddPaymentSuccessfully() {
        when(paymentRepo.save(eq(sampleOrder), any(Payment.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));

        Payment result = paymentService.addPayment(sampleOrder, "DISCOUNT", firstPaymentDetails);

        assertNotNull(result);
        assertEquals("DISCOUNT", result.getMethod());
        assertEquals(firstPaymentDetails, result.getPaymentData());

        verify(paymentRepo).save(eq(sampleOrder), any(Payment.class));
    }

    @Test
    void shouldUpdatePaymentStatus() {
        Payment selectedPayment = paymentRecords.get(0);
        when(paymentRepo.getOrder(selectedPayment.getId())).thenReturn(sampleOrder);

        paymentService.setStatus(selectedPayment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), selectedPayment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), sampleOrder.getStatus());

        paymentService.setStatus(selectedPayment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), selectedPayment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), sampleOrder.getStatus());

        verify(paymentRepo, times(2)).getOrder(selectedPayment.getId());
    }

    @Test
    void shouldThrowExceptionForInvalidStatus() {
        Payment selectedPayment = paymentRecords.get(0);
        paymentRepo.save(sampleOrder, selectedPayment);

        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(selectedPayment, "INVALID"));
    }

    @Test
    void shouldRetrievePaymentById() {
        Payment selectedPayment = paymentRecords.get(0);
        doReturn(selectedPayment).when(paymentRepo).findById(selectedPayment.getId());

        Payment result = paymentService.getPayment(selectedPayment.getId());

        assertNotNull(result);
        assertEquals(selectedPayment.getId(), result.getId());
    }

    @Test
    void shouldReturnNullForNonexistentPaymentId() {
        doReturn(null).when(paymentRepo).findById("nonexistent-id");
        assertNull(paymentService.getPayment("nonexistent-id"));
    }

    @Test
    void shouldFetchAllPayments() {
        doReturn(paymentRecords).when(paymentRepo).findAll();

        List<Payment> retrievedPayments = paymentService.getAllPayments();

        assertEquals(2, retrievedPayments.size());
    }
}
