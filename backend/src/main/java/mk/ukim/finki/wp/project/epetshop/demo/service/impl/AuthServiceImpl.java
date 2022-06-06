package mk.ukim.finki.wp.project.epetshop.demo.service.impl;

import mk.ukim.finki.wp.project.epetshop.demo.model.Member;
import mk.ukim.finki.wp.project.epetshop.demo.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp.project.epetshop.demo.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp.project.epetshop.demo.repository.MemberRepo;
import mk.ukim.finki.wp.project.epetshop.demo.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(MemberRepo memberRepo, PasswordEncoder passwordEncoder) {
        this.memberRepo = memberRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Member login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        Member member = memberRepo.findByUsername(username).get();
        if(member != null) {
            if(passwordEncoder.matches(password, member.getPassword())) {
                return member;
            }
            throw new InvalidUserCredentialsException();
        }
        throw new InvalidUserCredentialsException();
    }

}
