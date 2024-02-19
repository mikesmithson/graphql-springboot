package com.tutorial.springboot.graphql.service;

import com.tutorial.springboot.graphql.entity.Result;
import com.tutorial.springboot.graphql.repository.ResultRepository;
import com.tutorial.springboot.graphql.response.StudentResponse;
import com.tutorial.springboot.graphql.response.StudentSubjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {
    private final ResultRepository repository;

    public List<StudentSubjectResponse> getResultForStudent(int studentId) {
        log.debug("In Result service fetching student grades");
        return repository.findByStudentId(studentId).stream()
                .map(result -> StudentSubjectResponse.builder()
                        .subjectName(result.getSubject().getSubjectName())
                        .marks(result.getMarks())
                        .build())
                .toList();
    }

    public Map<StudentResponse, List<StudentSubjectResponse>> getResultForAllStudents(List<StudentResponse> studentResponses) {
        log.debug("fetching all responses");
        List<Result> results = StreamSupport.stream(repository.findAll().spliterator(), false).toList();
        Map<StudentResponse, List<StudentSubjectResponse>> batchMap = new LinkedHashMap<>();

        studentResponses.forEach(studentResponse -> {
                    List<StudentSubjectResponse> studentSubjectResponses = results.stream()
                            .filter(result -> studentResponse.getId() == result.getStudent().getId())
                            .map(result -> StudentSubjectResponse.builder()
                                    .marks(result.getMarks())
                                    .subjectName(result.getSubject().getSubjectName())
                                    .build()).toList();
                    batchMap.put(studentResponse, studentSubjectResponses);
                }
        );
        return batchMap;
    }
}
