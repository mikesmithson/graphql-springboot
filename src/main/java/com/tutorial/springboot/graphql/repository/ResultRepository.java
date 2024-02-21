package com.tutorial.springboot.graphql.repository;

import com.tutorial.springboot.graphql.entity.Result;
import com.tutorial.springboot.graphql.entity.ResultID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends CrudRepository<Result, ResultID> {
}
