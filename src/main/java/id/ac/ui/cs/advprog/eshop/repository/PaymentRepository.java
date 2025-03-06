package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private List<Payment> paymentList = new ArrayList<>();
    private Map<String, Order> paymentOrderMap = new HashMap<>();

    public Payment save(Order order, Payment payment) {
        paymentList.add(payment);
        paymentOrderMap.put(payment.getId(), order);
        if (payment.getStatus().equals("SUCCESS")) {
            order.setStatus("SUCCESS");
        } else if (payment.getStatus().equals("REJECTED")) {
            order.setStatus("FAILED");
        } else {
            throw new IllegalArgumentException();
        }

        return payment;
    }

    public Payment findById(String paymentId) {
        for (Payment storedPayment : paymentList) {
            if (storedPayment.getId().equals(paymentId)) {
                return storedPayment;
            }
        }
        return null;
    }

    public Order getOrder(String paymentId) {
        return paymentOrderMap.get(paymentId);
    }

    public List<Payment> findAll() {
        List<Payment> allPayments = new ArrayList<>();
        for (Payment payment : paymentList) {
            allPayments.add(payment);
        }
        return allPayments;
    }

    public void update(Payment payment, String newStatus) {
        Order order = this.getOrder(payment.getId());
        payment.setStatus(newStatus);
        if (newStatus.equals("SUCCESS")) {
            order.setStatus("SUCCESS");
        } else if (newStatus.equals("REJECTED")) {
            order.setStatus("FAILED");
        } else {
            throw new IllegalArgumentException();
        }
    }
}
