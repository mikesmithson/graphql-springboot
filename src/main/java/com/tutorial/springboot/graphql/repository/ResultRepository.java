package com.tutorial.springboot.graphql.repository;

import com.tutorial.springboot.graphql.entity.Result;
import com.tutorial.springboot.graphql.entity.ResultID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, ResultID> {
    List<Result> findByStudentId(int studentId);
}
