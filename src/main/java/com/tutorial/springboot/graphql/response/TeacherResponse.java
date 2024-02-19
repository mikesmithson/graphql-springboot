package com.tutorial.springboot.graphql.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeacherResponse {
    private int id;
    private String name;
    private String contact;
    private List<TeacherSubjectResponse> courses;
}
