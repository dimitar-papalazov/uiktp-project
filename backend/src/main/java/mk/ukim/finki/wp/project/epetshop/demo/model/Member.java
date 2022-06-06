package mk.ukim.finki.wp.project.epetshop.demo.model;

import lombok.Data;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.MemberRole;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.VerificationStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Random;

@Data
@Entity
public class Member implements UserDetails {

    @Id
    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Integer verificationCode;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    @Enumerated(value = EnumType.STRING)
    private VerificationStatus status;

    public Member() {
    }

    public Member(String username, String email, String password, String firstName, String lastName, MemberRole role, VerificationStatus status){
        this.username=username;
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.role=role;
        this.status=status;
        Random random = new Random();
        this.verificationCode = random.nextInt(9999 - 1000 + 1) + 1000;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
