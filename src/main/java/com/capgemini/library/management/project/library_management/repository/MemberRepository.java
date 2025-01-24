package com.capgemini.library.management.project.library_management.repository;

import com.capgemini.library.management.project.library_management.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    List<Member> findByStatus(Member.MemberStatus status);
    Optional<Member> findById(Long id);
}
