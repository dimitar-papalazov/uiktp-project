package mk.ukim.finki.wp.project.epetshop.demo.service.impl;


import mk.ukim.finki.wp.project.epetshop.demo.model.ProductType;
import mk.ukim.finki.wp.project.epetshop.demo.repository.TypeRepo;
import mk.ukim.finki.wp.project.epetshop.demo.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepo typeRepo;

    public TypeServiceImpl(TypeRepo typeRepo) {
        this.typeRepo = typeRepo;
    }

    @Override
    public List<ProductType> findAllTypes() {
        return this.typeRepo.findAll();
    }

    @Override
    public ProductType addProductType(String type) {
        return this.typeRepo.save(new ProductType(type));
    }
}
