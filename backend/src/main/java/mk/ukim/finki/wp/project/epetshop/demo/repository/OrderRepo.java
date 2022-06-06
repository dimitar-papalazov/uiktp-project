package mk.ukim.finki.wp.project.epetshop.demo.repository;

import mk.ukim.finki.wp.project.epetshop.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByMember_Username(String username);
}
