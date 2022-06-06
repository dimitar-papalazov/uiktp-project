package mk.ukim.finki.wp.project.epetshop.demo.service;

import mk.ukim.finki.wp.project.epetshop.demo.model.Member;

public interface AuthService {
    Member login(String username, String password);
}
