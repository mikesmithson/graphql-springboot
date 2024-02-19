package com.tutorial.springboot.graphql.service;

import com.tutorial.springboot.graphql.repository.ResultRepository;
import com.tutorial.springboot.graphql.response.StudentSubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository repository;

    public List<StudentSubjectResponse> getResultForStudent(int studentId) {
        return repository.findByStudentId(studentId).stream()
                .map(result -> StudentSubjectResponse.builder()
                        .subjectName(result.getSubject().getSubjectName())
                        .marks(result.getMarks())
                        .build())
                .toList();
    }
}
