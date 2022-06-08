package mk.ukim.finki.wp.project.epetshop.demo.service.impl;


import mk.ukim.finki.wp.project.epetshop.demo.model.Product;
import mk.ukim.finki.wp.project.epetshop.demo.model.ProductType;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.ProductDto;
import mk.ukim.finki.wp.project.epetshop.demo.model.exceptions.InvalidProduct;
import mk.ukim.finki.wp.project.epetshop.demo.model.exceptions.InvalidType;
import mk.ukim.finki.wp.project.epetshop.demo.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.wp.project.epetshop.demo.repository.ProductRepo;
import mk.ukim.finki.wp.project.epetshop.demo.repository.TypeRepo;
import mk.ukim.finki.wp.project.epetshop.demo.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final TypeRepo typeRepo;

    public ProductServiceImpl(ProductRepo productRepo, TypeRepo typeRepo) {
        this.productRepo = productRepo;
        this.typeRepo = typeRepo;
    }

    @Override
    public List<Product> findAllProducts() {
        return this.productRepo.findAll();
    }

    @Override
    public Product findProduct(Long id) {
        return this.productRepo.findById(id).orElseThrow(InvalidProduct::new);
    }

    @Override
    public List<Product> findMostSellingProducts() {
        List<Product> products=productRepo.findAll(Sort.by(Sort.Direction.DESC,
                "sold"));
        if(products.size()<=6){
            return products;
        }
        else{
            List<Product> firstSix=new ArrayList<>();
            for(int i=0;i<6;i++){
                firstSix.add(products.get(i));
            }
            return firstSix;
        }
    }

    @Override
    public List<Product> findSimilarProducts(Long id) {
        Product product=this.findProduct(id);
        ProductType type=product.getType();
        List<Product> products=this.productRepo.findAllByTypeLike(type);
        products.sort(Comparator.comparing(Product::getSold).reversed());
        List<Product> firstFour=new ArrayList<>();
        int i = 0;
        for (Product p: products) {
            if (i == 4)
                break;
            if (p.getId().equals(product.getId()))
                continue;
            firstFour.add(p);
            i++;
        }
        return firstFour;
    }

    @Override
    public Product deleteById(Long id) {
        Product product=this.findProduct(id);
        this.productRepo.delete(product);
        return product;
    }

    @Override
    public List<Product> findAllByTypeLike(Long typeid) {
        ProductType type = this.typeRepo.findById(typeid).orElse(null);
        if(type==null){
            throw new InvalidType();
        }
        else
            return this.productRepo.findAllByTypeLike(type);
    }

    @Override
    public Optional<Product> addProduct(ProductDto productDto) {
        ProductType type = this.typeRepo.findById(productDto.getType())
                .orElseThrow(() -> new InvalidType());
        this.productRepo.deleteByName(productDto.getName());
        return Optional.of(this.productRepo.save(new Product(type, productDto.getImageUrl(), productDto.getName(), productDto.getPrice(), productDto.getQuantity(), productDto.getSale(), productDto.getSold())));
    }

    @Override
    public Optional<Product> updateProduct(Long id, ProductDto productDto) {
        Product product = this.productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        ProductType type = this.typeRepo.findById(productDto.getType())
                .orElseThrow(() -> new InvalidType());
        product.setType(type);
        product.setSold(productDto.getSold());
        product.setSale(productDto.getSale());
        product.setImageUrl(productDto.getImageUrl());

        return Optional.of(this.productRepo.save(product));
    }
}
