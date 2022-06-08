package mk.ukim.finki.wp.project.epetshop.demo.config;

import mk.ukim.finki.wp.project.epetshop.demo.model.Member;
import mk.ukim.finki.wp.project.epetshop.demo.model.Product;
import mk.ukim.finki.wp.project.epetshop.demo.model.ProductType;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.OrderDto;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.ProductDto;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.MemberRole;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.VerificationStatus;
import mk.ukim.finki.wp.project.epetshop.demo.service.MemberService;
import mk.ukim.finki.wp.project.epetshop.demo.service.OrderService;
import mk.ukim.finki.wp.project.epetshop.demo.service.ProductService;
import mk.ukim.finki.wp.project.epetshop.demo.service.TypeService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {


    private final MemberService userService;
    private final TypeService typeService;
    private final ProductService productService;
    private final OrderService orderService;

    public DataInitializer(MemberService userService, TypeService typeService
            , ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.typeService = typeService;
        this.productService = productService;
        this.orderService = orderService;
    }


    @PostConstruct
    public void initData() {
        Member admin =
                this.userService.register("admin",
                        "admin@email.com", "admin","admin", "admin",
                        "admin",
                        MemberRole.ROLE_ADMIN,
                        VerificationStatus.VERIFIED);
        Member dp = this.userService.register("dp", "d.papalazov@email.com"
                , "dp", "dp", "Dimitar", " Papalazov", MemberRole.ROLE_USER,
                VerificationStatus.VERIFIED);

        ProductType kuchinja = this.typeService.addProductType("Кучиња");
        ProductType machinja = this.typeService.addProductType("Мачиња");
        ProductType glodari = this.typeService.addProductType("Глодари");
        ProductType ptici = this.typeService.addProductType("Птици");
        ProductType akvaristika = this.typeService.addProductType(
                "Акваристика");

        Product dogFood =
                this.productService.addProduct(new ProductDto(kuchinja.getId(),
                        "https://prijateli5.com/wp-content/uploads/2020/11/advance-medium-puppy.jpg",
                        "Храна за куче",
                        340.0,
                        10,
                        0)).get();
        Product dogToy =
                this.productService.addProduct(new ProductDto(kuchinja.getId(),
                        "https://prijateli5.com/wp-content/uploads/2019/03/camon-igracka-2.jpg",
                        "Играчка за куче",
                        200.0,
                        10,
                        0)).get();
        Product dogContainer =
                this.productService.addProduct(new ProductDto(kuchinja.getId(),
                        "https://prijateli5.com/wp-content/uploads/2016/05/trixie-dwie-miski-045l-na-stojaku-24831-.jpg",
                        "Сад за храна за куче",
                        390.0,
                        10,
                        0)).get();
        Product dogStrap =
                this.productService.addProduct(new ProductDto(kuchinja.getId(),
                        "https://prijateli5.com/wp-content/uploads/2016/05/280-1.jpg",
                        "Ремче за куче",
                        490.0,
                        10,
                        0)).get();
        Product catFood =
                this.productService.addProduct(new ProductDto(machinja.getId(),
                        "https://prijateli5.com/wp-content/uploads/2020/03/88000814_2776330202451122_5431583167720980480_n.jpg",
                        "Храна за маче",
                        40.0,
                        10,
                        0)).get();
        Product catToy =
                this.productService.addProduct(new ProductDto(machinja.getId(),
                        "https://prijateli5.com/wp-content/uploads/2020/06/gluvcinja.jpg",
                        "Играчка за маче",
                        170.0,
                        10,
                        0)).get();
        Product catScratcher =
                this.productService.addProduct(new ProductDto(machinja.getId(),
                        "https://prijateli5.com/wp-content/uploads/2021/02/rb-038.jpg",
                        "Гребалка за маче",
                        1200.0,
                        1,
                        18)).get();
        this.productService.addProduct(new ProductDto(glodari.getId(),
                "https://prijateli5.com/wp-content/uploads/2019/04/MANITOBA-MIX-CONIGLIETTO-1-KG-extra-big-9487-547.jpg",
                "Храна за зајак",
                200.0,
                10,
                0)).get();
        this.productService.addProduct(new ProductDto(ptici.getId(),
                "https://prijateli5.com/wp-content/uploads/2019/04/poilka-za-glodari-100ml-sifra-60521-cena-140-den-zemja-germanija.jpg",
                "Поилка за птици",
                140.0,
                10,
                0)).get();
        this.productService.addProduct(new ProductDto(ptici.getId(),
                "https://prijateli5.com/wp-content/uploads/2020/12/zvp2100.jpg",
                "Храна за птици",
                210.0,
                10,
                0)).get();
        this.productService.addProduct(new ProductDto(ptici.getId(),
                "https://prijateli5.com/wp-content/uploads/2019/04/2512-thickbox_default-Kafez-za-ptici-set-Fop-10410011.jpg",
                "Кафез за птици",
                1900.0,
                10,
                0)).get();
        this.productService.addProduct(new ProductDto(ptici.getId(),
                "https://prijateli5.com/wp-content/uploads/2016/05/223.jpg",
                "Сад за храна за птици",
                120.0,
                10,
                0)).get();
        this.productService.addProduct(new ProductDto(akvaristika.getId(),
                "https://prijateli5.com/wp-content/uploads/2019/04/akvarium-2.jpg",
                "Аквариум за риби",
                1500.0,
                10,
                0)).get();
        this.productService.addProduct(new ProductDto(akvaristika.getId(),
                "https://prijateli5.com/wp-content/uploads/2016/05/at-202.jpg",
                "Пумпа за аквариум за риби",
                1090.0,
                10,
                0)).get();

        List<Long> dpProducts = new ArrayList<>();
        dpProducts.add(catScratcher.getId());
        dpProducts.add(catFood.getId());
        dpProducts.add(catToy.getId());

        orderService.addOrder(new OrderDto(dp.getUsername(), "+38975000000",
                "Ulica br.26", "Bitola", "7000", true, dpProducts));
    }
}
