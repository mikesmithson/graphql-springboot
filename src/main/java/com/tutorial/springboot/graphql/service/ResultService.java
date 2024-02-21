package com.tutorial.springboot.graphql.service;

import com.tutorial.springboot.graphql.entity.Result;
import com.tutorial.springboot.graphql.entity.Subject;
import com.tutorial.springboot.graphql.repository.ResultRepository;
import com.tutorial.springboot.graphql.repository.SubjectRepository;
import com.tutorial.springboot.graphql.response.MemberResponse;
import com.tutorial.springboot.graphql.response.StudentSubjectResponse;
import com.tutorial.springboot.graphql.response.TeacherSubjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {
    private final ResultRepository resultRepository;
    private final SubjectRepository subjectRepository;

    public Map<MemberResponse, List<?>> getResultForAllStudents(List<MemberResponse> memberResponses) {
        log.debug("fetching all student responses");
        List<Result> results = StreamSupport.stream(resultRepository.findAll().spliterator(), false).toList();
        Map<MemberResponse, List<?>> batchMap = new LinkedHashMap<>();

        memberResponses.forEach(memberResponse -> {
            List<StudentSubjectResponse> studentSubjectResponses = results.stream().filter(result -> memberResponse.getId() == result.getStudent().getId()).map(result -> {
                StudentSubjectResponse studentSubjectResponse = new StudentSubjectResponse();
                studentSubjectResponse.setMarks(result.getMarks());
                studentSubjectResponse.setSubjectName(result.getSubject().getSubjectName());
                return studentSubjectResponse;
            }).toList();

            batchMap.put(memberResponse, studentSubjectResponses);
        });
        return batchMap;
    }

    public Map<MemberResponse, List<?>> getresultsForAllTeachers(List<MemberResponse> memberResponses) {
        log.debug("fetching all teachers");
        List<Subject> subjects = StreamSupport.stream(subjectRepository.findAll().spliterator(), false).toList();
        Map<MemberResponse, List<?>> batchMap = new LinkedHashMap<>();

        memberResponses.forEach(memberResponse -> {
            List<TeacherSubjectResponse> teacherSubjectResponses = subjects.stream().filter(subject -> memberResponse.getId() == subject.getTeacher().getId()).map(subject -> {
                TeacherSubjectResponse response = new TeacherSubjectResponse();
                response.setExperience(subject.getExperience());
                response.setSubjectName(subject.getSubjectName());
                return response;
            }).toList();
            batchMap.put(memberResponse, teacherSubjectResponses);
        });
        return batchMap;
    }
}
