package mk.ukim.finki.wp.project.epetshop.demo.service;

import mk.ukim.finki.wp.project.epetshop.demo.model.Product;
import mk.ukim.finki.wp.project.epetshop.demo.model.ProductType;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.ProductDto;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProducts();

    Product findProduct(Long id);

    List<Product> findMostSellingProducts();

    Optional<Product> updateProduct(Long id, ProductDto newProduct);

    Optional<Product> addProduct(ProductDto productDto);

    List<Product> findSimilarProducts(Long id);

    Product deleteById(Long id);

    List<Product> findAllByTypeLike(Long typeId);
}
