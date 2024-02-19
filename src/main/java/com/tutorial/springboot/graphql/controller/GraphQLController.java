package com.tutorial.springboot.graphql.controller;

import com.tutorial.springboot.graphql.response.StudentResponse;
import com.tutorial.springboot.graphql.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLController {
    private final MemberService memberService;

    @QueryMapping
    public String firstQuery() {
        return  "First GQL Query works";
    }

    @QueryMapping
    public String secondQuery(@Argument String firstName, @Argument String lastName) {
        return String.join(" ","Hello there,", firstName,lastName);
    }
    @QueryMapping
    public List<StudentResponse> getAllStudents() {
        return memberService.getAllStudents();
    }
}
