package com.tutorial.springboot.graphql.controller;

import com.tutorial.springboot.graphql.entity.MemberType;
import com.tutorial.springboot.graphql.response.MemberResponse;
import com.tutorial.springboot.graphql.service.MemberService;
import com.tutorial.springboot.graphql.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.LinkedHashMap;
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
    public List<MemberResponse> getMembers(@Argument("filter") MemberType memberType) {
        return memberService.getAllMembers(memberType);
    }

    @BatchMapping(typeName = "MemberResponse", field = "subjectData", maxBatchSize = 10)
    public Map<MemberResponse, List<?>> getSubjectsForMembers(List<MemberResponse> members) {
        List<MemberResponse> studentsResponse = members.stream()
                .filter(response -> response.getType().equals(MemberType.STUDENT)).toList();
        List<MemberResponse> teacherResponse = members.stream()
                .filter(response -> response.getType().equals(MemberType.TEACHER)).toList();
        Map<MemberResponse, List<?>> outputMap = new LinkedHashMap<>();
        if (studentsResponse.isEmpty()) {
            outputMap.putAll(resultService.getresultsForAllTeachers(members));
        } else {
            outputMap.putAll(resultService.getResultForAllStudents(members));
        }
        return outputMap;
    }

}
