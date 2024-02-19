package com.tutorial.springboot.graphql.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherSubjectResponse {
    private String subjectName;
    private Integer experience;
}
