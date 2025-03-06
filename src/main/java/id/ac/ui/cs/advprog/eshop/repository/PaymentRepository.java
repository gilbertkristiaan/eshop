package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {

    private List<Payment> payments;
    private Map<String, Order> paymentOrder;

    public Payment save(Order order, Payment payment) {return null;}

    public void update(Payment payment, String status) {

    }

    public Payment findById(String paymentId) {return null;}

    public List<Payment> findAll() {return null;}

    public Order getOrder(String paymentId) {return null;}
}