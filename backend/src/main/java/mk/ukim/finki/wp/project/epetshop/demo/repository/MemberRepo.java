package mk.ukim.finki.wp.project.epetshop.demo.repository;

import mk.ukim.finki.wp.project.epetshop.demo.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.OptionalInt;

@Repository
public interface MemberRepo extends JpaRepository<Member, String> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);

}
