package mk.ukim.finki.wp.project.epetshop.demo;

import mk.ukim.finki.wp.project.epetshop.demo.model.Member;
import mk.ukim.finki.wp.project.epetshop.demo.model.Order;
import mk.ukim.finki.wp.project.epetshop.demo.model.Product;
import mk.ukim.finki.wp.project.epetshop.demo.model.ProductType;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.OrderDto;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.ProductDto;
import mk.ukim.finki.wp.project.epetshop.demo.model.dto.TypeDto;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.MemberRole;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.VerificationStatus;
import mk.ukim.finki.wp.project.epetshop.demo.model.exceptions.*;
import mk.ukim.finki.wp.project.epetshop.demo.repository.MemberRepo;
import mk.ukim.finki.wp.project.epetshop.demo.repository.OrderRepo;
import mk.ukim.finki.wp.project.epetshop.demo.repository.ProductRepo;
import mk.ukim.finki.wp.project.epetshop.demo.repository.TypeRepo;
import mk.ukim.finki.wp.project.epetshop.demo.service.*;
import mk.ukim.finki.wp.project.epetshop.demo.service.impl.*;
import mk.ukim.finki.wp.project.epetshop.demo.web.MemberRestController;
import mk.ukim.finki.wp.project.epetshop.demo.web.OrderRestController;
import mk.ukim.finki.wp.project.epetshop.demo.web.ProductRestController;
import mk.ukim.finki.wp.project.epetshop.demo.web.TypeRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

class Variables
{
    public Member memberVerified;
    public Member memberPending;
    public Member adminMember;
    public Product firstProduct;
    public Product secondProduct;
    public Order order;

    public Variables()
    {
        ProductType firstType = new ProductType("car");
        ProductType secondType = new ProductType("ball");

        memberVerified = new Member("testVerified", "testingVerified@test.com", "testPassword123", "John", "Doe", MemberRole.ROLE_USER, VerificationStatus.VERIFIED);
        memberPending = new Member("testPending", "testingPending@test.com", "testPassword123", "John", "Doe", MemberRole.ROLE_USER, VerificationStatus.PENDING);
        adminMember = new Member("admin", "admin@test.com", "adminPassword123", "AdminJohn", "Doe", MemberRole.ROLE_ADMIN, VerificationStatus.VERIFIED);
        firstProduct = new Product(firstType, "https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg", "Fico", 1999.99, 5, 1, 2);
        secondProduct = new Product(secondType, "https://upload.wikimedia.org/wikipedia/commons/1/1d/Football_Pallo_valmiina-cropped.jpg", "Football", 100.0, 30, 5, 12);

        List<Product> products = new ArrayList<>();
        products.add(firstProduct);
        products.add(secondProduct);
        order = new Order(memberVerified,"38971000000","Address","Skopje","1000",true,products);

    }

}



@SpringBootTest
class EPetShopApplicationTests {

    private Variables v;
    public Member memberVerified;
    public Member memberPending;
    public Member adminMember;
    public Product firstProduct;
    public Product secondProduct;
    public Order order;


    @BeforeEach
    void setUp()
    {
        v = new Variables();
        memberVerified = v.memberVerified;
        memberPending = v.memberPending;
        adminMember = v.adminMember;
        firstProduct = v.firstProduct;
        secondProduct = v.secondProduct;
        order = v.order;
    }


    @Test
    @DisplayName("----Model----\nTesting Member Class")
    void testMemberClass()
    {
        Assertions.assertEquals("testVerified",memberVerified.getUsername());
        Assertions.assertEquals("testingVerified@test.com",memberVerified.getEmail());
        Assertions.assertEquals("testPassword123",memberVerified.getPassword());
        Assertions.assertEquals("John",memberVerified.getFirstName());
        Assertions.assertEquals("Doe",memberVerified.getLastName());
        Assertions.assertEquals(MemberRole.ROLE_USER,memberVerified.getRole());
        Assertions.assertEquals(VerificationStatus.VERIFIED,memberVerified.getStatus());

        Assertions.assertEquals("testPending",memberPending.getUsername());
        Assertions.assertEquals("testingPending@test.com",memberPending.getEmail());
        Assertions.assertEquals("testPassword123",memberPending.getPassword());
        Assertions.assertEquals("John",memberPending.getFirstName());
        Assertions.assertEquals("Doe",memberPending.getLastName());
        Assertions.assertEquals(MemberRole.ROLE_USER,memberPending.getRole());
        Assertions.assertEquals(VerificationStatus.PENDING,memberPending.getStatus());

        Assertions.assertEquals("admin",adminMember.getUsername());
        Assertions.assertEquals("admin@test.com",adminMember.getEmail());
        Assertions.assertEquals("adminPassword123",adminMember.getPassword());
        Assertions.assertEquals("AdminJohn",adminMember.getFirstName());
        Assertions.assertEquals("Doe",adminMember.getLastName());
        Assertions.assertEquals(MemberRole.ROLE_ADMIN,adminMember.getRole());
        Assertions.assertEquals(VerificationStatus.VERIFIED,adminMember.getStatus());


        Assertions.assertFalse(memberVerified.isAccountNonExpired());
        Assertions.assertFalse(memberVerified.isAccountNonLocked());
        Assertions.assertFalse(memberVerified.isCredentialsNonExpired());
        Assertions.assertFalse(memberVerified.isEnabled());

        Assertions.assertFalse(memberPending.isAccountNonExpired());
        Assertions.assertFalse(memberPending.isAccountNonLocked());
        Assertions.assertFalse(memberPending.isCredentialsNonExpired());
        Assertions.assertFalse(memberPending.isEnabled());

    }

    @Test
    @DisplayName("----Model----\nTesting Product Class")
    void testProductClass(){
        ProductType testFirstType = new ProductType("car");

        Assertions.assertEquals(testFirstType,firstProduct.getType());
        Assertions.assertEquals("Fico",firstProduct.getName());
        Assertions.assertEquals("https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg",firstProduct.getImageUrl());
        Assertions.assertEquals(1999.99,firstProduct.getPrice());
        Assertions.assertEquals(5,firstProduct.getQuantity());
        Assertions.assertEquals(1,firstProduct.getSale());
        Assertions.assertEquals(2,firstProduct.getSold());

        ProductType testSecondType = new ProductType("ball");

        Assertions.assertEquals(testSecondType,secondProduct.getType());
        Assertions.assertEquals("Football",secondProduct.getName());
        Assertions.assertEquals("https://upload.wikimedia.org/wikipedia/commons/1/1d/Football_Pallo_valmiina-cropped.jpg",secondProduct.getImageUrl());
        Assertions.assertEquals(100.00,secondProduct.getPrice());
        Assertions.assertEquals(30,secondProduct.getQuantity());
        Assertions.assertEquals(5,secondProduct.getSale());
        Assertions.assertEquals(12,secondProduct.getSold());

    }

    @Test
    @DisplayName("----Model----\nTesting Order Class")
    void testOrderClass(){
        Assertions.assertEquals(memberVerified,order.getMember());
        Assertions.assertEquals("38971000000",order.getPhoneNumber());
        Assertions.assertEquals("Address",order.getAddress());
        Assertions.assertEquals("Skopje",order.getCity());
        Assertions.assertEquals("1000",order.getPostalCode());
        Assertions.assertTrue(order.getToDoor());

        List<Product> products = new ArrayList<>();
        products.add(firstProduct);
        products.add(secondProduct);

        List<Product> orderProducts = order.getProducts();

        for(int i=0;i<products.size();i++) {
            Assertions.assertEquals(products.get(i), orderProducts.get(i));
        }
        order.setTrackingNumber(Long.MAX_VALUE);
        Assertions.assertEquals(Long.MAX_VALUE,order.getTrackingNumber());
    }


}

@DataJpaTest
class RepositoryTests
{
    Variables v;
    public Member memberVerified;
    public Member memberPending;
    public Member adminMember;
    public Product firstProduct;
    public Product secondProduct;
    public Order order;

    @Autowired private EntityManager entityManager;
    @Autowired private MemberRepo memberRepo;
    @Autowired private ProductRepo productRepo;
    @Autowired private OrderRepo orderRepo;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp()
    {
        v = new Variables();
        memberVerified = v.memberVerified;
        memberPending = v.memberPending;
        adminMember = v.adminMember;
        firstProduct = v.firstProduct;
        secondProduct = v.secondProduct;
        order = v.order;

    }

    @Test
    @DisplayName("----Repository----\nTesting injection components")
    void injectedComponentsAreNotNull()
    {
        assertThat(entityManager).isNotNull();
        assertThat(memberRepo).isNotNull();
        assertThat(productRepo).isNotNull();
        assertThat(orderRepo).isNotNull();
    }

    @Test
    @DisplayName("----Repository----\nTesting MemberRepo Interface")
    void testMemberRepoInterface() {
        memberRepo.save(memberVerified);
        memberRepo.save(memberPending);
        memberRepo.save(adminMember);

        Assertions.assertEquals("testVerified",memberRepo.findByUsername("testVerified").get().getUsername());
        Assertions.assertEquals("testPending",memberRepo.findByUsername("testPending").get().getUsername());
        Assertions.assertEquals("admin",memberRepo.findByUsername("admin").get().getUsername());

        //Testing exception if username can't be found
        Throwable exceptionMemberRepoFindUsername = Assertions.assertThrows(NoSuchElementException.class, () -> memberRepo.findByUsername("ado").get());
        Assertions.assertEquals("No value present",exceptionMemberRepoFindUsername.getMessage());

        Assertions.assertEquals("testingVerified@test.com",memberRepo.findByEmail("testingVerified@test.com").get().getEmail());
        Assertions.assertEquals("testingPending@test.com",memberRepo.findByEmail("testingPending@test.com").get().getEmail());
        Assertions.assertEquals("admin@test.com",memberRepo.findByEmail("admin@test.com").get().getEmail());

        //Testing exception if email can't be found
        Throwable exceptionMemberRepoFindEmail= Assertions.assertThrows(NoSuchElementException.class, () -> memberRepo.findByEmail("test@google.com").get());
        Assertions.assertEquals("No value present",exceptionMemberRepoFindEmail.getMessage());

        //Testing if all data from find is good
        Assertions.assertEquals("admin@test.com",memberRepo.findByUsername("admin").get().getEmail());
        Assertions.assertEquals("adminPassword123",memberRepo.findByUsername("admin").get().getPassword());
        Assertions.assertEquals("AdminJohn",memberRepo.findByUsername("admin").get().getFirstName());
        Assertions.assertEquals("Doe",memberRepo.findByUsername("admin").get().getLastName());
        Assertions.assertEquals(MemberRole.ROLE_ADMIN,memberRepo.findByUsername("admin").get().getRole());
        Assertions.assertEquals(VerificationStatus.VERIFIED,memberRepo.findByUsername("admin").get().getStatus());

    }


}

@SpringBootTest
class ServiceTests
{
    Variables v;
    public Member memberVerified;
    public Member memberPending;
    public Member adminMember;
    public Product firstProduct;
    public Product secondProduct;
    public Order order;

    @Autowired private EntityManager entityManager;
    @Autowired private MemberRepo memberRepo;
    @Autowired private ProductRepo productRepo;
    @Autowired private OrderRepo orderRepo;
    @Autowired private TypeRepo typeRepo;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp()
    {
        v = new Variables();
        memberVerified = v.memberVerified;
        memberPending = v.memberPending;
        adminMember = v.adminMember;
        firstProduct = v.firstProduct;
        secondProduct = v.secondProduct;
        order = v.order;
        passwordEncoder = new BCryptPasswordEncoder();

        memberRepo.save(memberVerified);
        memberRepo.save(memberPending);
        memberRepo.save(adminMember);
    }

    @Test
    @DisplayName("----Service----\nTesting injection components")
    void injectedComponentsAreNotNull()
    {
        assertThat(entityManager).isNotNull();
        assertThat(memberRepo).isNotNull();
        assertThat(productRepo).isNotNull();
        assertThat(orderRepo).isNotNull();
        assertThat(typeRepo).isNotNull();
    }

    @Test
    @DisplayName("----Service----\nTesting MemberServiceImpl register()")
    void testingMemberServiceImplRegister(){

        MemberServiceImpl msi = new MemberServiceImpl(memberRepo,passwordEncoder);


        Member test = new Member("newTest","newTest@testing.com","testTest123!","Jane","Doe",MemberRole.ROLE_USER,VerificationStatus.PENDING);
        Member registerTest = msi.register("newTest","newTest@testing.com","testTest123!","testTest123!","Jane","Doe",MemberRole.ROLE_USER,VerificationStatus.PENDING);

        Assertions.assertEquals(test.getUsername(),registerTest.getUsername());
        Assertions.assertEquals(test.getEmail(),registerTest.getEmail());
        Assertions.assertEquals(test.getFirstName(),registerTest.getFirstName());
        Assertions.assertEquals(test.getLastName(),registerTest.getLastName());
        Assertions.assertEquals(test.getRole(),registerTest.getRole());
        Assertions.assertEquals(test.getStatus(),registerTest.getStatus());

        //Testing MemberServiceImpl Exceptions
        Throwable exceptionNullUsername= Assertions.assertThrows(InvalidUsernameOrPasswordException.class, () -> msi.register(null,"newTest@testing.com","testTest123!","testTest123!","Jane","Doe",MemberRole.ROLE_USER,VerificationStatus.PENDING));
        Assertions.assertNull(exceptionNullUsername.getMessage());

        Throwable exceptionEmptyUsername= Assertions.assertThrows(InvalidUsernameOrPasswordException.class, () -> msi.register("","newTest@testing.com","testTest123!","testTest123!","Jane","Doe",MemberRole.ROLE_USER,VerificationStatus.PENDING));
        Assertions.assertNull(exceptionEmptyUsername.getMessage());

        Throwable exceptionNullPassword= Assertions.assertThrows(InvalidUsernameOrPasswordException.class, () -> msi.register("newTest","newTest@testing.com",null,"testTest123!","Jane","Doe",MemberRole.ROLE_USER,VerificationStatus.PENDING));
        Assertions.assertNull(exceptionNullPassword.getMessage());

        Throwable exceptionEmptyPassword= Assertions.assertThrows(InvalidUsernameOrPasswordException.class, () -> msi.register("newTest","newTest@testing.com","","testTest123!","Jane","Doe",MemberRole.ROLE_USER,VerificationStatus.PENDING));
        Assertions.assertNull(exceptionEmptyPassword.getMessage());

        Throwable exceptionPasswordsDoNotMatch= Assertions.assertThrows(PasswordsDoNotMatchException.class, () -> msi.register("newTest","newTest@testing.com","testT3st123!","testTest123!","Jane","Doe",MemberRole.ROLE_USER,VerificationStatus.PENDING));
        Assertions.assertEquals("Passwords do not match exception.",exceptionPasswordsDoNotMatch.getMessage());

        Throwable exceptionUsernameAlreadyExists= Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> msi.register("newTest","newTest@testing.com","testTest123!","testTest123!","Jane","Doe",MemberRole.ROLE_USER,VerificationStatus.PENDING));
        Assertions.assertEquals("Member with username: newTest already exists",exceptionUsernameAlreadyExists.getMessage());

        Throwable exceptionEmailAlreadyExists= Assertions.assertThrows(EmailAlreadyExistsException.class, () -> msi.register("newEmailTest","newTest@testing.com","testTest123!","testTest123!","Jane","Doe",MemberRole.ROLE_USER,VerificationStatus.PENDING));
        Assertions.assertEquals("Email newTest@testing.com does not exists",exceptionEmailAlreadyExists.getMessage());

    }

    @Test
    @DisplayName("----Service----\nTesting MemberServiceImpl verify()")
    void testingMemberServiceImplVerify(){

        MemberServiceImpl msi = new MemberServiceImpl(memberRepo,passwordEncoder);

        Member testVerify = msi.verify(1000,"testVerified");
        Member testPending = msi.verify(1000,"testPending");

        Assertions.assertEquals(memberVerified.getUsername(),testVerify.getUsername());
        Assertions.assertEquals(memberVerified.getEmail(),testVerify.getEmail());
        Assertions.assertEquals(memberVerified.getFirstName(),testVerify.getFirstName());
        Assertions.assertEquals(memberVerified.getLastName(),testVerify.getLastName());
        Assertions.assertEquals(memberVerified.getRole(),testVerify.getRole());
        Assertions.assertEquals(memberVerified.getStatus(),testVerify.getStatus());

        Assertions.assertEquals(memberPending.getUsername(),testPending.getUsername());
        Assertions.assertEquals(memberPending.getEmail(),testPending.getEmail());
        Assertions.assertEquals(memberPending.getFirstName(),testPending.getFirstName());
        Assertions.assertEquals(memberPending.getLastName(),testPending.getLastName());
        Assertions.assertEquals(memberPending.getRole(),testPending.getRole());
        //This is not equals because after verify it changes status
        Assertions.assertNotEquals(memberPending.getStatus(),testPending.getStatus());
    }

    @Test
    @DisplayName("----Service----\nTesting MemberServiceImpl loadByUsername()")
    void testingMemberServiceImplLoadByUsername() {
        MemberServiceImpl msi = new MemberServiceImpl(memberRepo, passwordEncoder);

        Assertions.assertEquals(memberVerified.getUsername(), msi.loadUserByUsername("testVerified").getUsername());
        Assertions.assertEquals(memberPending.getUsername(), msi.loadUserByUsername("testPending").getUsername());
        Assertions.assertEquals(adminMember.getUsername(), msi.loadUserByUsername("admin").getUsername());
    }
    @Test
    @DisplayName("----Service----\nTesting AuthServiceImpl login")
    void testingAuthServiceImplLogin() {
        AuthServiceImpl asi = new AuthServiceImpl(memberRepo,passwordEncoder);

//        Member testLogin = asi.login(memberVerified.getUsername(),memberVerified.getPassword());
//        Assertions.assertEquals(memberVerified.getUsername(),testLogin.getUsername());
//        Assertions.assertEquals(memberVerified.getPassword(),testLogin.getPassword());
//        Assertions.assertEquals(memberVerified.getFirstName(),testLogin.getFirstName());
//        Assertions.assertEquals(memberVerified.getLastName(),testLogin.getLastName());
//
//        testLogin = asi.login(memberPending.getUsername(),memberPending.getPassword());
//        Assertions.assertEquals(memberPending.getUsername(),testLogin.getUsername());
//        Assertions.assertEquals(memberPending.getPassword(),testLogin.getPassword());
//        Assertions.assertEquals(memberPending.getFirstName(),testLogin.getFirstName());
//        Assertions.assertEquals(memberPending.getLastName(),testLogin.getLastName());
//
//        testLogin = asi.login(adminMember.getUsername(),adminMember.getPassword());
//        Assertions.assertEquals(adminMember.getUsername(),testLogin.getUsername());
//        Assertions.assertEquals(adminMember.getPassword(),testLogin.getPassword());
//        Assertions.assertEquals(adminMember.getFirstName(),testLogin.getFirstName());
//        Assertions.assertEquals(adminMember.getLastName(),testLogin.getLastName());

        Throwable exceptionNullUsername = Assertions.assertThrows(InvalidArgumentsException.class, () -> asi.login(null,"testTest123!"));
        Assertions.assertEquals("Invalid arguments exception",exceptionNullUsername.getMessage());

        Throwable exceptionEmptyUsername = Assertions.assertThrows(InvalidArgumentsException.class, () -> asi.login("","testTest123!"));
        Assertions.assertEquals("Invalid arguments exception",exceptionEmptyUsername.getMessage());

        Throwable exceptionNullPassword = Assertions.assertThrows(InvalidArgumentsException.class, () -> asi.login("newTest",null));
        Assertions.assertEquals("Invalid arguments exception",exceptionNullPassword.getMessage());

        Throwable exceptionEmptyPassword = Assertions.assertThrows(InvalidArgumentsException.class, () -> asi.login("newTest",""));
        Assertions.assertEquals("Invalid arguments exception",exceptionEmptyPassword.getMessage());

        Throwable exceptionUsernamesDoNotMatch= Assertions.assertThrows(NoSuchElementException.class, () -> asi.login("newT3st","testT3st123!"));
        Assertions.assertEquals("No value present",exceptionUsernamesDoNotMatch.getMessage());

    }
    @Test
    @DisplayName("----Service----\nTesting ProductServiceImpl findAllProducts")
    void testingProductServiceImplFindAllProducts() {
        ProductServiceImpl psi = new ProductServiceImpl(productRepo,typeRepo);

        List<Product> products = psi.findAllProducts();

        Assertions.assertEquals(14,products.size());

        Assertions.assertEquals(1,products.get(0).getId());
        Assertions.assertEquals(1,products.get(0).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(0).getType().getName());
        Assertions.assertEquals("Храна за куче",products.get(0).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2020/11/advance-medium-puppy.jpg",products.get(0).getImageUrl());
        Assertions.assertEquals(340.0,products.get(0).getPrice());
        Assertions.assertEquals(10,products.get(0).getQuantity());
        Assertions.assertEquals(0,products.get(0).getSale());
        Assertions.assertEquals(0,products.get(0).getSold());

        Assertions.assertEquals(2,products.get(1).getId());
        Assertions.assertEquals(1,products.get(1).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(1).getType().getName());
        Assertions.assertEquals("Играчка за куче",products.get(1).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2019/03/camon-igracka-2.jpg",products.get(1).getImageUrl());
        Assertions.assertEquals(200.0,products.get(1).getPrice());
        Assertions.assertEquals(10,products.get(1).getQuantity());
        Assertions.assertEquals(0,products.get(1).getSale());
        Assertions.assertEquals(0,products.get(1).getSold());

        Assertions.assertEquals(3,products.get(2).getId());
        Assertions.assertEquals(1,products.get(2).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(2).getType().getName());
        Assertions.assertEquals("Сад за храна за куче",products.get(2).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2016/05/trixie-dwie-miski-045l-na-stojaku-24831-.jpg",products.get(2).getImageUrl());
        Assertions.assertEquals(390.0,products.get(2).getPrice());
        Assertions.assertEquals(10,products.get(2).getQuantity());
        Assertions.assertEquals(0,products.get(2).getSale());
        Assertions.assertEquals(0,products.get(2).getSold());

        Assertions.assertEquals(4,products.get(3).getId());
        Assertions.assertEquals(1,products.get(3).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(3).getType().getName());
        Assertions.assertEquals("Ремче за куче",products.get(3).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2016/05/280-1.jpg",products.get(3).getImageUrl());
        Assertions.assertEquals(490.0,products.get(3).getPrice());
        Assertions.assertEquals(10,products.get(3).getQuantity());
        Assertions.assertEquals(0,products.get(3).getSale());
        Assertions.assertEquals(0,products.get(3).getSold());

        Assertions.assertEquals(5,products.get(4).getId());
        Assertions.assertEquals(2,products.get(4).getType().getId());
        Assertions.assertEquals("Мачиња",products.get(4).getType().getName());
        Assertions.assertEquals("Храна за маче",products.get(4).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2020/03/88000814_2776330202451122_5431583167720980480_n.jpg",products.get(4).getImageUrl());
        Assertions.assertEquals(40.0,products.get(4).getPrice());
        Assertions.assertEquals(9,products.get(4).getQuantity());
        Assertions.assertEquals(0,products.get(4).getSale());
        Assertions.assertEquals(1,products.get(4).getSold());

    }
    @Test
    @DisplayName("----Service----\nTesting ProductServiceImpl findProduct")
    void testingProductServiceImplFindProduct() {
        ProductServiceImpl psi = new ProductServiceImpl(productRepo, typeRepo);

        Product testProduct = psi.findProduct(2L);

        Assertions.assertEquals(2,testProduct.getId());
        Assertions.assertEquals(1,testProduct.getType().getId());
        Assertions.assertEquals("Кучиња",testProduct.getType().getName());
        Assertions.assertEquals("Играчка за куче",testProduct.getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2019/03/camon-igracka-2.jpg",testProduct.getImageUrl());
        Assertions.assertEquals(200.0,testProduct.getPrice());
        Assertions.assertEquals(10,testProduct.getQuantity());
        Assertions.assertEquals(0,testProduct.getSale());
        Assertions.assertEquals(0,testProduct.getSold());

        Throwable exceptionInvalidProduct= Assertions.assertThrows(InvalidProduct.class, () -> psi.findProduct(25L));
        Assertions.assertNull(exceptionInvalidProduct.getMessage());
    }

    @Test
    @DisplayName("----Service----\nTesting ProductServiceImpl findMostSellingProducts")
    void testingProductServiceImplFindMostSellingProducts() {
        ProductServiceImpl psi = new ProductServiceImpl(productRepo, typeRepo);

        List<Product> products = psi.findMostSellingProducts();

        System.out.println(products);

        Assertions.assertEquals(5,products.get(0).getId());
        Assertions.assertEquals(2,products.get(0).getType().getId());
        Assertions.assertEquals("Мачиња",products.get(0).getType().getName());
        Assertions.assertEquals("Храна за маче",products.get(0).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2020/03/88000814_2776330202451122_5431583167720980480_n.jpg",products.get(0).getImageUrl());
        Assertions.assertEquals(40.0,products.get(0).getPrice());
        Assertions.assertEquals(9,products.get(0).getQuantity());
        Assertions.assertEquals(0,products.get(0).getSale());
        Assertions.assertEquals(1,products.get(0).getSold());

        Assertions.assertEquals(6,products.get(1).getId());
        Assertions.assertEquals(2,products.get(1).getType().getId());
        Assertions.assertEquals("Мачиња",products.get(1).getType().getName());
        Assertions.assertEquals("Играчка за маче",products.get(1).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2020/06/gluvcinja.jpg",products.get(1).getImageUrl());
        Assertions.assertEquals(170.0,products.get(1).getPrice());
        Assertions.assertEquals(9,products.get(1).getQuantity());
        Assertions.assertEquals(0,products.get(1).getSale());
        Assertions.assertEquals(1,products.get(1).getSold());

        Assertions.assertEquals(7,products.get(2).getId());
        Assertions.assertEquals(2,products.get(2).getType().getId());
        Assertions.assertEquals("Мачиња",products.get(2).getType().getName());
        Assertions.assertEquals("Гребалка за маче",products.get(2).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2021/02/rb-038.jpg",products.get(2).getImageUrl());
        Assertions.assertEquals(1200.0,products.get(2).getPrice());
        Assertions.assertEquals(0,products.get(2).getQuantity());
        Assertions.assertEquals(18,products.get(2).getSale());
        Assertions.assertEquals(1,products.get(2).getSold());

        Assertions.assertEquals(1,products.get(3).getId());
        Assertions.assertEquals(1,products.get(3).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(3).getType().getName());
        Assertions.assertEquals("Храна за куче",products.get(3).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2020/11/advance-medium-puppy.jpg",products.get(3).getImageUrl());
        Assertions.assertEquals(340.0,products.get(3).getPrice());
        Assertions.assertEquals(10,products.get(3).getQuantity());
        Assertions.assertEquals(0,products.get(3).getSale());
        Assertions.assertEquals(0,products.get(3).getSold());

        Assertions.assertEquals(2,products.get(4).getId());
        Assertions.assertEquals(1,products.get(4).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(4).getType().getName());
        Assertions.assertEquals("Играчка за куче",products.get(4).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2019/03/camon-igracka-2.jpg",products.get(4).getImageUrl());
        Assertions.assertEquals(200.0,products.get(4).getPrice());
        Assertions.assertEquals(10,products.get(4).getQuantity());
        Assertions.assertEquals(0,products.get(4).getSale());
        Assertions.assertEquals(0,products.get(4).getSold());

        Assertions.assertEquals(3,products.get(5).getId());
        Assertions.assertEquals(1,products.get(5).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(5).getType().getName());
        Assertions.assertEquals("Сад за храна за куче",products.get(5).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2016/05/trixie-dwie-miski-045l-na-stojaku-24831-.jpg",products.get(5).getImageUrl());
        Assertions.assertEquals(390.0,products.get(5).getPrice());
        Assertions.assertEquals(10,products.get(5).getQuantity());
        Assertions.assertEquals(0,products.get(5).getSale());
        Assertions.assertEquals(0,products.get(5).getSold());
    }

    @Test
    @DisplayName("----Service----\nTesting ProductServiceImpl findSimilarProducts")
    void testingProductServiceImplFindSimilarProducts() {
        ProductServiceImpl psi = new ProductServiceImpl(productRepo, typeRepo);

        List<Product> products = psi.findSimilarProducts(1L);
        System.out.println(products);

        Assertions.assertEquals(3,products.size());

        Assertions.assertEquals(2,products.get(0).getId());
        Assertions.assertEquals(1,products.get(0).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(0).getType().getName());
        Assertions.assertEquals("Играчка за куче",products.get(0).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2019/03/camon-igracka-2.jpg",products.get(0).getImageUrl());
        Assertions.assertEquals(200.0,products.get(0).getPrice());
        Assertions.assertEquals(10,products.get(0).getQuantity());
        Assertions.assertEquals(0,products.get(0).getSale());
        Assertions.assertEquals(0,products.get(0).getSold());

    }
    @Test
    @DisplayName("----Service----\nTesting ProductServiceImpl deleteById")
    void testingProductServiceImplDeleteById() {
        ProductServiceImpl psi = new ProductServiceImpl(productRepo, typeRepo);

        Product deletedProduct = psi.deleteById(2L);

        Assertions.assertEquals(2,deletedProduct.getId());
        Assertions.assertEquals(1,deletedProduct.getType().getId());
        Assertions.assertEquals("Кучиња",deletedProduct.getType().getName());
        Assertions.assertEquals("Играчка за куче",deletedProduct.getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2019/03/camon-igracka-2.jpg",deletedProduct.getImageUrl());
        Assertions.assertEquals(200.0,deletedProduct.getPrice());
        Assertions.assertEquals(10,deletedProduct.getQuantity());
        Assertions.assertEquals(0,deletedProduct.getSale());
        Assertions.assertEquals(0,deletedProduct.getSold());

        List<Product> products = psi.findAllProducts();
        Assertions.assertEquals(13,products.size());
    }
    @Test
    @DisplayName("----Service----\nTesting ProductServiceImpl findAllByTypeLike")
    void testingProductServiceImplFindAllByTypeLike() {
        ProductServiceImpl psi = new ProductServiceImpl(productRepo, typeRepo);

        List<Product> products = psi.findAllByTypeLike(1L);

        Assertions.assertEquals(4,products.size());

        Assertions.assertEquals(1,products.get(0).getId());
        Assertions.assertEquals(1,products.get(0).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(0).getType().getName());
        Assertions.assertEquals("Храна за куче",products.get(0).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2020/11/advance-medium-puppy.jpg",products.get(0).getImageUrl());
        Assertions.assertEquals(340.0,products.get(0).getPrice());
        Assertions.assertEquals(10,products.get(0).getQuantity());
        Assertions.assertEquals(0,products.get(0).getSale());
        Assertions.assertEquals(0,products.get(0).getSold());
    }
    @Test
    @DisplayName("----Service----\nTesting ProductServiceImpl addProduct")
    void testingProductServiceImplAddProduct() {
        ProductServiceImpl psi = new ProductServiceImpl(productRepo, typeRepo);

        ProductDto productDto = new ProductDto(1L,"https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg","Fico", 12000.0,5, 2);
        Optional<Product> product = psi.addProduct(productDto);

        Assertions.assertEquals(15,product.get().getId());
        Assertions.assertEquals(1,product.get().getType().getId());
        Assertions.assertEquals("Кучиња",product.get().getType().getName());
        Assertions.assertEquals("Fico",product.get().getName());
        Assertions.assertEquals("https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg",product.get().getImageUrl());
        Assertions.assertEquals(12000.0,product.get().getPrice());
        Assertions.assertEquals(5,product.get().getQuantity());
        Assertions.assertEquals(2,product.get().getSale());
        Assertions.assertEquals(0,product.get().getSold());

        ProductDto productDtoException = new ProductDto(120L,"https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg","Fico", 12000.0,5, 2);
        Throwable exceptionInvalidType= Assertions.assertThrows(InvalidType.class, () -> psi.addProduct(productDtoException));
        Assertions.assertNull(exceptionInvalidType.getMessage());
    }
    @Test
    @DisplayName("----Service----\nTesting ProductServiceImpl updateProduct")
    void testingProductServiceImplUpdateProduct() {
        ProductServiceImpl psi = new ProductServiceImpl(productRepo, typeRepo);
        ProductDto productDto = new ProductDto(1L,"https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg","Fico", 12000.0,5, 2);

        Optional<Product> product = psi.updateProduct(1L,productDto);

        Assertions.assertEquals(1,product.get().getId());
        Assertions.assertEquals(1,product.get().getType().getId());
        Assertions.assertEquals("Кучиња",product.get().getType().getName());
        Assertions.assertEquals("Fico",product.get().getName());
        Assertions.assertEquals("https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg",product.get().getImageUrl());
        Assertions.assertEquals(12000.0,product.get().getPrice());
        Assertions.assertEquals(5,product.get().getQuantity());
        Assertions.assertEquals(2,product.get().getSale());
        Assertions.assertEquals(0,product.get().getSold());

        Product updatedProduct = productRepo.findById(1L).get();

        Assertions.assertEquals(1,updatedProduct.getId());
        Assertions.assertEquals(1,updatedProduct.getType().getId());
        Assertions.assertEquals("Кучиња",updatedProduct.getType().getName());
        Assertions.assertEquals("Fico",updatedProduct.getName());
        Assertions.assertEquals("https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg",updatedProduct.getImageUrl());
        Assertions.assertEquals(12000.0,updatedProduct.getPrice());
        Assertions.assertEquals(5,updatedProduct.getQuantity());
        Assertions.assertEquals(2,updatedProduct.getSale());
        Assertions.assertEquals(0,updatedProduct.getSold());

        ProductDto productDtoException = new ProductDto(120L,"https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg","Fico", 12000.0,5, 2);

        Throwable exceptionProductNotFound= Assertions.assertThrows(ProductNotFoundException.class, () -> psi.updateProduct(120L,productDtoException));
        Assertions.assertEquals("Product with id: 120 was not found",exceptionProductNotFound.getMessage());

        Throwable exceptionInvalidType= Assertions.assertThrows(InvalidType.class, () -> psi.updateProduct(1L,productDtoException));
        Assertions.assertNull(exceptionInvalidType.getMessage());
    }
    @Test
    @DisplayName("----Service----\nTesting TypeServiceImpl findAllTypes")
    void testingTypeServiceImplFindAllTypes() {
        TypeServiceImpl tsi = new TypeServiceImpl(typeRepo);

        List<ProductType> productTypes = tsi.findAllTypes();

        Assertions.assertEquals(1,productTypes.get(0).getId());
        Assertions.assertEquals("Кучиња",productTypes.get(0).getName());

        Assertions.assertEquals(2,productTypes.get(1).getId());
        Assertions.assertEquals("Мачиња",productTypes.get(1).getName());

        Assertions.assertEquals(3,productTypes.get(2).getId());
        Assertions.assertEquals("Глодари",productTypes.get(2).getName());

        Assertions.assertEquals(4,productTypes.get(3).getId());
        Assertions.assertEquals("Птици",productTypes.get(3).getName());

        Assertions.assertEquals(5,productTypes.get(4).getId());
        Assertions.assertEquals("Акваристика",productTypes.get(4).getName());
    }
    @Test
    @DisplayName("----Service----\nTesting TypeServiceImpl addProductType")
    void testingTypeServiceImplAddProductType() {
        TypeServiceImpl tsi = new TypeServiceImpl(typeRepo);

        ProductType pt = tsi.addProductType("Car");

        Assertions.assertEquals(6,pt.getId());
        Assertions.assertEquals("Car",pt.getName());
    }
    @Test
    @DisplayName("----Service----\nTesting OrderServiceImpl findAllOrders")
    void testingOrderServiceImplFindAllOrders() {
        OrderServiceImpl osi = new OrderServiceImpl(orderRepo,memberRepo,productRepo);

        List<Order> orders = osi.findAllOrders();

        Assertions.assertEquals(1,orders.get(0).getId());
        Assertions.assertEquals("+38975000000",orders.get(0).getPhoneNumber());
        Assertions.assertEquals("Ulica br.26",orders.get(0).getAddress());
        Assertions.assertEquals("Bitola",orders.get(0).getCity());
        Assertions.assertEquals("7000",orders.get(0).getPostalCode());
        Assertions.assertTrue(orders.get(0).getToDoor());

    }

    @Test
    @DisplayName("----Service----\nTesting OrderServiceImpl findAllOrdersByMember")
    void testingOrderServiceImplFindAllOrdersByMember() {
        OrderServiceImpl osi = new OrderServiceImpl(orderRepo, memberRepo, productRepo);

        List<Order> orders = osi.findAllOrdersByMember("dp");
        Assertions.assertEquals(1,orders.get(0).getId());
        Assertions.assertEquals("+38975000000",orders.get(0).getPhoneNumber());
        Assertions.assertEquals("Ulica br.26",orders.get(0).getAddress());
        Assertions.assertEquals("Bitola",orders.get(0).getCity());
        Assertions.assertEquals("7000",orders.get(0).getPostalCode());
        Assertions.assertTrue(orders.get(0).getToDoor());
    }
    @Test
    @DisplayName("----Service----\nTesting OrderServiceImpl editTrackingNumber")
    void testingOrderServiceImplEditTrackingNumber() {
        OrderServiceImpl osi = new OrderServiceImpl(orderRepo, memberRepo, productRepo);

        Order orderEdited = osi.editTrackingNumber(1L,25001L);

        Assertions.assertEquals(1,orderEdited.getId());
        Assertions.assertEquals("+38975000000",orderEdited.getPhoneNumber());
        Assertions.assertEquals("Ulica br.26",orderEdited.getAddress());
        Assertions.assertEquals("Bitola",orderEdited.getCity());
        Assertions.assertEquals("7000",orderEdited.getPostalCode());
        Assertions.assertEquals(25001L,orderEdited.getTrackingNumber());
        Assertions.assertTrue(orderEdited.getToDoor());
    }
    @Test
    @DisplayName("----Service----\nTesting OrderServiceImpl findOrder")
    void testingOrderServiceImplFindOrder() {
        OrderServiceImpl osi = new OrderServiceImpl(orderRepo, memberRepo, productRepo);

        Order findOrder = osi.findOrder(1L);

        Assertions.assertEquals(1,findOrder.getId());
        Assertions.assertEquals("+38975000000",findOrder.getPhoneNumber());
        Assertions.assertEquals("Ulica br.26",findOrder.getAddress());
        Assertions.assertEquals("Bitola",findOrder.getCity());
        Assertions.assertEquals("7000",findOrder.getPostalCode());
        Assertions.assertTrue(findOrder.getToDoor());
    }

    @Test
    @DisplayName("----Service----\nTesting OrderServiceImpl addOrder")
    void testingOrderServiceAddOrder() {
        OrderServiceImpl osi = new OrderServiceImpl(orderRepo, memberRepo, productRepo);

        OrderDto orderDto = new OrderDto("dp", "+38971123123","Simeon Kavrakirov 2",
                "Skopje", "1000", false,
                List.of(1L,3L,4L));

        Optional<Order> orderAdded = osi.addOrder(orderDto);

        Assertions.assertEquals(2,orderAdded.get().getId());
        Assertions.assertEquals("+38971123123",orderAdded.get().getPhoneNumber());
        Assertions.assertEquals("Simeon Kavrakirov 2",orderAdded.get().getAddress());
        Assertions.assertEquals("Skopje",orderAdded.get().getCity());
        Assertions.assertEquals("1000",orderAdded.get().getPostalCode());
        Assertions.assertFalse(orderAdded.get().getToDoor());

        OrderDto orderDtoException = new OrderDto("kaja", "+38971123123","Simeon Kavrakirov 2",
                "Skopje", "1000", false,
                List.of(1L,3L,4L));
        Throwable exceptionInvalidUsername= Assertions.assertThrows(InvalidUsernameException.class, () -> osi.addOrder(orderDtoException));
        Assertions.assertEquals("Invalid username exception",exceptionInvalidUsername.getMessage());
    }

}

@SpringBootTest
class WebTests
{
    Variables v;
    public Member memberVerified;
    public Member memberPending;
    public Member adminMember;
    public Product firstProduct;
    public Product secondProduct;
    public Order order;
    @Autowired private EntityManager entityManager;
    @Autowired private AuthService authService;
    @Autowired private MemberService memberService;
    @Autowired private TypeService typeService;
    @Autowired private ProductService productService;
    @Autowired private OrderService orderService;

    @BeforeEach
    void setUp()
    {
        v = new Variables();
        memberVerified = v.memberVerified;
        memberPending = v.memberPending;
        adminMember = v.adminMember;
        firstProduct = v.firstProduct;
        secondProduct = v.secondProduct;
        order = v.order;

    }

    @Test
    @DisplayName("----Repository----\nTesting injection components")
    void injectedComponentsAreNotNull()
    {
        assertThat(entityManager).isNotNull();
        assertThat(memberService).isNotNull();
        assertThat(authService).isNotNull();
        assertThat(typeService).isNotNull();
        assertThat(productService).isNotNull();
        assertThat(orderService).isNotNull();
    }

    @Test
    @DisplayName("----Web----\nTesting MemberRestController registration")
    void testMemberRestControllerRegistration()
    {
        MemberRestController mrc = new MemberRestController(authService,memberService);
        ResponseEntity<Member> register = mrc.registration("testUsername",
                "testEmail@test.com",
                "testPassword21!",
                "testPassword21!",
                "Ivan",
                "Markovski");

        Assertions.assertEquals(HttpStatus.OK,register.getStatusCode());

        ResponseEntity<Member> registerFail = mrc.registration("testUsername",
                "testEmail@test.com",
                "testPassword21!",
                "",
                "Ivan",
                "Markovski");

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,registerFail.getStatusCode());

        registerFail = mrc.registration("",
                "testEmail@test.com",
                "testPassword21!",
                "testPassword21!",
                "Ivan",
                "Markovski");

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,registerFail.getStatusCode());

        registerFail = mrc.registration("testUsername",
                "testEmail@test.com",
                "",
                "testPassword21!",
                "Ivan",
                "Markovski");

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,registerFail.getStatusCode());
    }
    @Test
    @DisplayName("----Web----\nTesting MemberRestController verify")
    void testMemberRestControllerVerify() {
        MemberRestController mrc = new MemberRestController(authService, memberService);

        ResponseEntity<Member> register = mrc.registration("testUsername",
                "testEmail@test.com",
                "testPassword21!",
                "testPassword21!",
                "Ivan",
                "Markovski");

        Assertions.assertEquals(HttpStatus.OK,register.getStatusCode());

        Assertions.assertEquals(VerificationStatus.VERIFIED,mrc.verify("100","testUsername").getStatus());
    }
    @Test
    @DisplayName("----Web----\nTesting MemberRestController login")
    void testMemberRestControllerLogin() {
        MemberRestController mrc = new MemberRestController(authService, memberService);

        ResponseEntity<Member> register = mrc.registration("testUsername",
                "testEmail@test.com",
                "testPassword21!",
                "testPassword21!",
                "Ivan",
                "Markovski");

        Assertions.assertEquals(HttpStatus.OK,register.getStatusCode());

        Member loggedIn = mrc.login("testUsername","testPassword21!");

        Assertions.assertEquals("testUsername",loggedIn.getUsername());
        Assertions.assertEquals("testEmail@test.com",loggedIn.getEmail());
        Assertions.assertEquals("Ivan",loggedIn.getFirstName());
        Assertions.assertEquals("Markovski",loggedIn.getLastName());
    }
    @Test
    @DisplayName("----Web----\nTesting TypeRestController findAllTypes")
    void testTypeRestControllerFindAllTypes() {
        TypeRestController trc = new TypeRestController(typeService,productService);

        List<ProductType> productTypes = trc.findAllTypes();

        Assertions.assertEquals(1,productTypes.get(0).getId());
        Assertions.assertEquals("Кучиња",productTypes.get(0).getName());

        Assertions.assertEquals(2,productTypes.get(1).getId());
        Assertions.assertEquals("Мачиња",productTypes.get(1).getName());

        Assertions.assertEquals(3,productTypes.get(2).getId());
        Assertions.assertEquals("Глодари",productTypes.get(2).getName());

        Assertions.assertEquals(4,productTypes.get(3).getId());
        Assertions.assertEquals("Птици",productTypes.get(3).getName());

        Assertions.assertEquals(5,productTypes.get(4).getId());
        Assertions.assertEquals("Акваристика",productTypes.get(4).getName());
    }
    @Test
    @DisplayName("----Web----\nTesting TypeRestController findProductsByType")
    void testTypeRestControllerFindProductsByType() {
        TypeRestController trc = new TypeRestController(typeService,productService);

        List<Product> products = trc.findProductsByType(1L);

        Assertions.assertEquals(4,products.size());

        Assertions.assertEquals(1,products.get(0).getId());
        Assertions.assertEquals(1,products.get(0).getType().getId());
        Assertions.assertEquals("Кучиња",products.get(0).getType().getName());
        Assertions.assertEquals("Храна за куче",products.get(0).getName());
        Assertions.assertEquals("https://prijateli5.com/wp-content/uploads/2020/11/advance-medium-puppy.jpg",products.get(0).getImageUrl());
        Assertions.assertEquals(340.0,products.get(0).getPrice());
        Assertions.assertEquals(10,products.get(0).getQuantity());
        Assertions.assertEquals(0,products.get(0).getSale());
        Assertions.assertEquals(0,products.get(0).getSold());
    }
    @Test
    @DisplayName("----Web----\nTesting TypeRestController save")
    void testTypeRestControllerSave() {
        TypeRestController trc = new TypeRestController(typeService,productService);

        TypeDto typeDto = new TypeDto("Car");

        Assertions.assertEquals(HttpStatus.OK,trc.save(typeDto).getStatusCode());
    }
    @Test
    @DisplayName("----Web----\nTesting ProductRestController findById")
    void testProductRestControllerFindById() {
        ProductRestController prc = new ProductRestController(productService);

        Assertions.assertEquals(HttpStatus.OK,prc.findById(1L).getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND,prc.findById(50L).getStatusCode());
    }
    @Test
    @DisplayName("----Web----\nTesting ProductRestController findSimilarProducts")
    void testProductRestControllerFindSimilarProducts() {
        ProductRestController prc = new ProductRestController(productService);

        Assertions.assertEquals(HttpStatus.OK,prc.findSimilarProducts(1L).getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND,prc.findSimilarProducts(50L).getStatusCode());
    }
    @Test
    @DisplayName("----Web----\nTesting ProductRestController save")
    void testProductRestControllerSave() {
        ProductRestController prc = new ProductRestController(productService);

        ProductDto productDto = new ProductDto(1L,"https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg","Fico", 12000.0,5, 2);
        ProductDto productDtoFail = new ProductDto(100L,"https://upload.wikimedia.org/wikipedia/commons/9/9e/Zastava_850_f.jpg","Fico", 12000.0,5, 2);

        Assertions.assertEquals(HttpStatus.OK,prc.save(productDto).getStatusCode());

        Assertions.assertEquals(HttpStatus.OK,prc.save(1L,productDto).getStatusCode());
    }
    @Test
    @DisplayName("----Web----\nTesting ProductRestController deleteById")
    void testProductRestControllerDeleteById() {
        ProductRestController prc = new ProductRestController(productService);

        Assertions.assertEquals(HttpStatus.OK,prc.deleteById(1L).getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,prc.deleteById(100L).getStatusCode());
    }

    @Test
    @DisplayName("----Web----\nTesting OrderRestController save")
    void testOrderRestControllerSave() {
        OrderRestController orc = new OrderRestController(orderService);
        OrderDto orderDto = new OrderDto("dp", "+38971123123","Simeon Kavrakirov 2",
                "Skopje", "1000", false,
                List.of(1L,3L,4L));

        Assertions.assertEquals(HttpStatus.OK,orc.save(orderDto).getStatusCode());
        Assertions.assertEquals(HttpStatus.OK,orc.save(2L,1005483758431L).getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,orc.save(3L,1005483758431L).getStatusCode());
    }

}
