package com.tutorial.springboot.graphql.response;

import com.tutorial.springboot.graphql.entity.MemberType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberResponse {
    private int id;
    private String name;
    private String contact;
    private MemberType type;
    private List<SubjectResponse> subjectData;
}
