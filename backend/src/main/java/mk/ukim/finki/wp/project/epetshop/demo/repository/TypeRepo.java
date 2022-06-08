package mk.ukim.finki.wp.project.epetshop.demo.repository;


import mk.ukim.finki.wp.project.epetshop.demo.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepo extends JpaRepository<ProductType, Long> {
}
