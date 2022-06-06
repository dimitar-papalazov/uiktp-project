package mk.ukim.finki.wp.project.epetshop.demo.service;

import mk.ukim.finki.wp.project.epetshop.demo.model.Order;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAllOrders();

    List<Order> findAllOrdersByMember(String username);

    Optional<Order> addOrder(OrderDto order);

    Order editTrackingNumber(Long id, Long number);

    Order findOrder(Long id);
}
