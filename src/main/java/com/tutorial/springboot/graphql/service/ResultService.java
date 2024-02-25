package com.tutorial.springboot.graphql.service;

import com.tutorial.springboot.graphql.entity.Result;
import com.tutorial.springboot.graphql.entity.Subject;
import com.tutorial.springboot.graphql.repository.ResultRepository;
import com.tutorial.springboot.graphql.repository.SubjectRepository;
import com.tutorial.springboot.graphql.response.MemberResponse;
import com.tutorial.springboot.graphql.response.MemberSearchResult;
import com.tutorial.springboot.graphql.response.StudentSubjectResponse;
import com.tutorial.springboot.graphql.response.TeacherSubjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {
    private final ResultRepository resultRepository;
    private final SubjectRepository subjectRepository;

    public Map<MemberResponse, List<?>> getMemberResponseForAllStudents(List<MemberResponse> memberResponses) {
        log.debug("fetching all student responses");
        List<Result> results = StreamSupport.stream(resultRepository.findAll().spliterator(), false).toList();
        return memberResponses.stream()
                .collect(Collectors.toMap(memberResponse ->  memberResponse, memberResponse -> createStudentSubjectResponses(results, memberResponse.getId())));
    }

    public Map<MemberSearchResult, List<?>> getMemberSearchResultsForAllStudents(List<MemberSearchResult> studentResponses) {
        log.debug("fetching all student search results");
        List<Result> results = StreamSupport.stream(resultRepository.findAll().spliterator(), false).toList();

        return studentResponses.stream()
                .collect(Collectors.toMap(studentResponse -> studentResponse, studentResponse -> createStudentSubjectResponses(results, studentResponse.getId())));
    }

    public Map<MemberSearchResult, List<?>> getMemberSearchResultsForAllTeachers(List<MemberSearchResult> teacherResponses) {
        log.debug("fetching all student search results");
        List<Subject> results = StreamSupport.stream(subjectRepository.findAll().spliterator(), false).toList();
        Map<MemberSearchResult, List<?>> batchMap = new LinkedHashMap<>();

        teacherResponses.forEach(teacherResponse -> {
            List<TeacherSubjectResponse> teacherSubjectResponses = results.stream().filter(result -> teacherResponse.getId() == result.getTeacher().getId()).map(result -> {
                TeacherSubjectResponse teacherSubjectResponse = new TeacherSubjectResponse();
                teacherSubjectResponse.setSubjectName(result.getSubjectName());
                teacherSubjectResponse.setExperience(result.getExperience());
                return teacherSubjectResponse;
            }).toList();

            batchMap.put(teacherResponse, teacherSubjectResponses);
        });
        return batchMap;
    }

    public Map<MemberResponse, List<?>> getMemberResponseForAllTeachers(List<MemberResponse> memberResponses) {
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

    private static List<StudentSubjectResponse> createStudentSubjectResponses(List<Result> results, int memberResponseId) {
        return results.stream().filter(result -> memberResponseId == result.getStudent().getId()).map(result -> {
            StudentSubjectResponse studentSubjectResponse = new StudentSubjectResponse();
            studentSubjectResponse.setMarks(result.getMarks());
            studentSubjectResponse.setSubjectName(result.getSubject().getSubjectName());
            return studentSubjectResponse;
        }).toList();
    }
}
