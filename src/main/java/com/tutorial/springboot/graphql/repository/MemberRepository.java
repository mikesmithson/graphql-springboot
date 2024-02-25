package com.tutorial.springboot.graphql.repository;

import com.tutorial.springboot.graphql.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {
    List<Member> findByType(String type);
    Optional<List<Member>> findByFirstName(String name);
    Optional<List<Member>> findByFirstNameStartsWith(String name);
}
