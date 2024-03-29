package com.tutorial.springboot.graphql.repository;

import com.tutorial.springboot.graphql.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Integer> {
    List<Subject> findSubjectsByTeacher_Id(int teacherId);
}

