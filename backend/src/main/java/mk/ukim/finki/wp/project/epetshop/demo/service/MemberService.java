package mk.ukim.finki.wp.project.epetshop.demo.service;


import mk.ukim.finki.wp.project.epetshop.demo.model.Member;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.MemberRole;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.VerificationStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    Member register(String username, String email, String password, String repeat, String firstName, String lastName, MemberRole role, VerificationStatus status);
    Member verify(Integer code, String username);
}
