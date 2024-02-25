package com.tutorial.springboot.graphql.service;

import com.tutorial.springboot.graphql.entity.Result;
import com.tutorial.springboot.graphql.entity.Subject;
import com.tutorial.springboot.graphql.repository.ResultRepository;
import com.tutorial.springboot.graphql.repository.SubjectRepository;
import com.tutorial.springboot.graphql.response.*;
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
        List<Result> results = fetchAllResults();
        return memberResponses.stream()
                .collect(Collectors.toMap(memberResponse ->  memberResponse, memberResponse -> createStudentSubjectResponses(results, memberResponse.getId())));
    }

    public Map<MemberSearchResult, List<?>> getMemberSearchResultsForAllStudents(List<MemberSearchResult> studentResponses) {
        log.debug("fetching all student search results");
        List<Result> results = fetchAllResults();

        return studentResponses.stream()
                .collect(Collectors.toMap(studentResponse -> studentResponse, studentResponse -> createStudentSubjectResponses(results, studentResponse.getId())));
    }

    public Map<MemberSearchResult, List<?>> getMemberSearchResultsForAllTeachers(List<MemberSearchResult> teacherResponses) {
        log.debug("fetching all student search subjects");
        List<Subject> subjects = fetchAllSubjects();

        return teacherResponses.stream()
                .collect(Collectors
                        .toMap(teacherResponse -> teacherResponse, teacherResponse -> createTeacherSubjectResponses(subjects, teacherResponse.getId())));
    }

    public Map<MemberResponse, List<?>> getMemberResponseForAllTeachers(List<MemberResponse> memberResponses) {
        log.debug("fetching all teachers");
        List<Subject> subjects = fetchAllSubjects();

        return memberResponses.stream()
                .collect(Collectors
                        .toMap(memberResponse-> memberResponse, memberResponse -> createTeacherSubjectResponses(subjects, memberResponse.getId())));
    }

    private List<Subject> fetchAllSubjects() {
        return StreamSupport.stream(subjectRepository.findAll().spliterator(), false).toList();
    }

    private List<Result> fetchAllResults() {
        return StreamSupport.stream(resultRepository.findAll().spliterator(), false).toList();
    }

    private static List<TeacherSubjectResponse> createTeacherSubjectResponses(List<Subject> subjects, Integer memberId) {
        return subjects.stream().filter(subject -> memberId == subject.getTeacher().getId()).map(subject -> {
                TeacherSubjectResponse teacherSubjectResponse = new TeacherSubjectResponse();
                teacherSubjectResponse.setExperience(subject.getExperience());
                teacherSubjectResponse.setSubjectName(subject.getSubjectName());
                return teacherSubjectResponse;
            }).toList();
    }

    private static List<StudentSubjectResponse> createStudentSubjectResponses(List<Result> results, int memberId) {
        return results.stream().filter(result -> memberId == result.getStudent().getId()).map(result -> {
            StudentSubjectResponse studentSubjectResponse = new StudentSubjectResponse();
            studentSubjectResponse.setMarks(result.getMarks());
            studentSubjectResponse.setSubjectName(result.getSubject().getSubjectName());
            return studentSubjectResponse;
        }).toList();
    }
}
