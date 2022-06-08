package mk.ukim.finki.wp.project.epetshop.demo.web;

import mk.ukim.finki.wp.project.epetshop.demo.model.Product;
import mk.ukim.finki.wp.project.epetshop.demo.model.ProductType;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.TypeDto;
import mk.ukim.finki.wp.project.epetshop.demo.service.ProductService;
import mk.ukim.finki.wp.project.epetshop.demo.service.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping("/api/types")
public class TypeRestController {

    private final TypeService typeService;
    private final ProductService productService;

    public TypeRestController(TypeService typeService, ProductService productService) {
        this.typeService = typeService;
        this.productService = productService;
    }

    @GetMapping
    public List<ProductType> findAllTypes(){
        return this.typeService.findAllTypes();
    }

    //Smeni go vo Long typeId
    @GetMapping("/by-type")
    public List<Product> findProductsByType(@RequestBody Long typeId) {
        return this.productService.findAllByTypeLike(typeId);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductType> save(@RequestBody TypeDto type) {
        try {
            ProductType t = this.typeService.addProductType(type.getName());
            return ResponseEntity.ok().body(t);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
