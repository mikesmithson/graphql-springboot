package com.tutorial.springboot.graphql.repository;

import com.tutorial.springboot.graphql.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    List<Member> findByType(String type);
}
