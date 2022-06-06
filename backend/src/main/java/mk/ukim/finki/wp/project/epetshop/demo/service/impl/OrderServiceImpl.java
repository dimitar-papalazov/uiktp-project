package mk.ukim.finki.wp.project.epetshop.demo.service.impl;

import mk.ukim.finki.wp.project.epetshop.demo.model.Member;
import mk.ukim.finki.wp.project.epetshop.demo.model.Order;
import mk.ukim.finki.wp.project.epetshop.demo.model.Product;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.OrderDto;
import mk.ukim.finki.wp.project.epetshop.demo.model.exceptions.InvalidOrder;
import mk.ukim.finki.wp.project.epetshop.demo.model.exceptions.InvalidUsernameException;
import mk.ukim.finki.wp.project.epetshop.demo.repository.MemberRepo;
import mk.ukim.finki.wp.project.epetshop.demo.repository.OrderRepo;
import mk.ukim.finki.wp.project.epetshop.demo.repository.ProductRepo;
import mk.ukim.finki.wp.project.epetshop.demo.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final MemberRepo memberRepo;
    private final ProductRepo productRepo;

    public OrderServiceImpl(OrderRepo orderRepo, MemberRepo memberRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.memberRepo = memberRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<Order> findAllOrders() {
        return this.orderRepo.findAll();
    }

    @Override
    public List<Order> findAllOrdersByMember(String username) {
        return this.orderRepo.findAllByMember_Username(username);
    }


    @Override
    public Order editTrackingNumber(Long id, Long number) {
        Order order=this.findOrder(id);

        order.setTrackingNumber(number);

        return this.orderRepo.save(order);
    }

    @Override
    public Order findOrder(Long id) {
        return this.orderRepo.findById(id).orElseThrow(InvalidOrder::new);
    }

    @Override
    public Optional<Order> addOrder(OrderDto orderDto) {
        Member member = this.memberRepo.findById(orderDto.getUsername())
                .orElseThrow(() -> new InvalidUsernameException());
        List<Product> products = new ArrayList<>();
        for (Long id: orderDto.getProductIds()) {
            Product p = this.productRepo.findById(id).get();
            products.add(p);
            p.setQuantity(p.getQuantity() - 1);
            p.setSold(p.getSold() + 1);
            this.productRepo.save(p);
        }
        return Optional.of(this.orderRepo.save(new Order(member,
                orderDto.getPhoneNumber(), orderDto.getAddress(),
                orderDto.getCity(), orderDto.getPostalCode(),
                orderDto.getToDoor(), products)));
    }
}
