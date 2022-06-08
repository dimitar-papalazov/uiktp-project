package mk.ukim.finki.wp.project.epetshop.demo.service;

import mk.ukim.finki.wp.project.epetshop.demo.model.ProductType;

import java.util.List;

public interface TypeService {
    List<ProductType> findAllTypes();

    ProductType addProductType(String type);
}
