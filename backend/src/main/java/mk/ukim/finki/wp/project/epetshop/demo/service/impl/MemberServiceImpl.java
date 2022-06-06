package mk.ukim.finki.wp.project.epetshop.demo.service.impl;


import mk.ukim.finki.wp.project.epetshop.demo.model.Member;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.MemberRole;
import mk.ukim.finki.wp.project.epetshop.demo.model.enumerations.VerificationStatus;
import mk.ukim.finki.wp.project.epetshop.demo.model.exceptions.*;
import mk.ukim.finki.wp.project.epetshop.demo.repository.MemberRepo;
import mk.ukim.finki.wp.project.epetshop.demo.service.MemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepo memberRepo, PasswordEncoder passwordEncoder) {
        this.memberRepo = memberRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Member register(String username, String email, String password, String repeat, String firstName, String lastName, MemberRole role, VerificationStatus status) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeat))
            throw new PasswordsDoNotMatchException();
        if(this.memberRepo.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        if(this.memberRepo.findByEmail(email).isPresent())
            throw new EmailAlreadyExistsException(email);
        Member member = new Member(username,email,passwordEncoder.encode(password),firstName,lastName,role, status);
        return memberRepo.save(member);
    }

    @Override
    public Member verify(Integer code, String username) {
        Member member = memberRepo.findByUsername(username).get();
//        if (member.getVerificationCode().equals(code)) {
            member.setStatus(VerificationStatus.VERIFIED);
            return memberRepo.save(member);
//        }
//        throw new InvalidVerificationCodeException();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return memberRepo.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }



}
