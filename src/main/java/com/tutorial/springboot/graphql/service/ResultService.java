package com.tutorial.springboot.graphql.service;

import com.tutorial.springboot.graphql.entity.Result;
import com.tutorial.springboot.graphql.repository.ResultRepository;
import com.tutorial.springboot.graphql.repository.SubjectRepository;
import com.tutorial.springboot.graphql.response.StudentResponse;
import com.tutorial.springboot.graphql.response.StudentSubjectResponse;
import com.tutorial.springboot.graphql.response.TeacherSubjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {
    private final ResultRepository resultRepository;
    private final SubjectRepository subjectRepository;

    public List<StudentSubjectResponse> getResultForStudent(int studentId) {
        log.debug("In Result service fetching student grades");
        return resultRepository.findByStudentId(studentId).stream()
                .map(result -> StudentSubjectResponse.builder()
                        .subjectName(result.getSubject().getSubjectName())
                        .marks(result.getMarks())
                        .build())
                .toList();
    }

    public List<TeacherSubjectResponse> getResultForTeacher(int teacherId) {
        log.debug("In Result service fetching teacher subjects");
        return subjectRepository.findSubjectsByTeacher_Id(teacherId).stream()
                .map(result -> TeacherSubjectResponse.builder()
                        .subjectName(result.getSubjectName())
                        .experience(result.getExperience())
                        .build())
                .toList();
    }

    public Map<StudentResponse, List<StudentSubjectResponse>> getResultForAllStudents(List<StudentResponse> studentResponses) {
        log.debug("fetching all responses");
        List<Result> results = StreamSupport.stream(resultRepository.findAll().spliterator(), false).toList();
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
