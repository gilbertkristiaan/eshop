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
public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderService orderService;

    Order order;

    Map<String, String> paymentData1;
    Map<String, String> paymentData2;

    List<Payment> payments = new ArrayList<>();

    @BeforeEach
    void setUp(){
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("a1b2c3d4-e5f6-7890-ab12-cd34ef567890");
        product1.setProductName("Shampoo Max Clean");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("f0e1d2c3-b4a5-6789-0abc-def123456789",
                products, 1710000000L, "JohnDoe");

        paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("123e4567-e89b-12d3-a456-426614174000", "VOUCHER", paymentData1);
        payments.add(payment1);

        paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", "ESHOP1234ABC5679");
        Payment payment2 = new Payment("987f6543-21dc-ba98-7654-321fedcba987", "VOUCHER", paymentData2);
        payments.add(payment2);
    }

    @Test
    void testAddPayment(){
        when(paymentRepository.save(eq(order), any(Payment.class))).thenAnswer(invocation -> {
            return invocation.<Payment>getArgument(1);
        });

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData1);

        assertNotNull(result);
        assertEquals("VOUCHER", result.getMethod());
        assertEquals(paymentData1, result.getPaymentData());

        verify(paymentRepository).save(eq(order), any(Payment.class));
    }

    @Test
    void testSetStatusToSuccess(){
        Payment payment = payments.get(0);

        when(paymentRepository.getOrder(payment.getId())).thenReturn(order);
        when(paymentRepository.save(any(Order.class), any(Payment.class))).thenReturn(payment);
        when(orderService.updateStatus(anyString(), anyString())).thenReturn(order);

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());
    }

    @Test
    void testSetStatusToRejected(){
        Payment payment = payments.get(0);

        when(paymentRepository.getOrder(payment.getId())).thenReturn(order);
        when(paymentRepository.save(any(Order.class), any(Payment.class))).thenReturn(payment);
        when(orderService.updateStatus(anyString(), anyString())).thenReturn(order);

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), updatedPayment.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), OrderStatus.FAILED.getValue());
    }

    @Test
    void testSetStatusToInvalidStatus(){
        Payment payment = payments.get(0);
        paymentRepository.save(order, payment);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "INVALID_STATUS");
        });
    }

    @Test
    void testGetPaymentSuccess(){
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result  = paymentService.getPayment(payment.getId());

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIdNotFound(){
        doReturn(null).when(paymentRepository).findById("not_found_id");
        assertNull(paymentService.getPayment("not_found_id"));
    }

    @Test
    void testGetAllPayments(){
        Payment payment = payments.get(1);
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> results = paymentService.getAllPayments();

        assertEquals(2, results.size());
    }
}