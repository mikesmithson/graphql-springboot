package com.tutorial.springboot.graphql.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentSubjectResponse {
    private String subjectName;
    private double marks;
}
