package com.tutorial.springboot.graphql.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentSubjectResponse extends SubjectResponse implements SearchResult {
    private double marks;
}
