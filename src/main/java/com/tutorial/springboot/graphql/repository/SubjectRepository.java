package com.tutorial.springboot.graphql.repository;

import com.tutorial.springboot.graphql.entity.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Integer> {
}

