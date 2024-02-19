package com.tutorial.springboot.graphql.controller;

import com.tutorial.springboot.graphql.response.StudentResponse;
import com.tutorial.springboot.graphql.response.StudentSubjectResponse;
import com.tutorial.springboot.graphql.service.MemberService;
import com.tutorial.springboot.graphql.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GraphQLController {
    private final MemberService memberService;
    private final ResultService resultService;

    @QueryMapping
    public String firstQuery() {
        return "First GQL Query works";
    }

    @QueryMapping
    public String secondQuery(@Argument String firstName, @Argument String lastName) {
        return String.join(" ", "Hello there,", firstName, lastName);
    }

    @QueryMapping
    public List<StudentResponse> getAllStudents() {
        return memberService.getAllStudents();
    }

    @BatchMapping(typeName = "StudentResponse", field = "result", maxBatchSize = 10)
    public Map<StudentResponse, List<StudentSubjectResponse>> getResultsAllStudents(List<StudentResponse> studentResponses) {
        return resultService.getResultForAllStudents(studentResponses);
    }
}
