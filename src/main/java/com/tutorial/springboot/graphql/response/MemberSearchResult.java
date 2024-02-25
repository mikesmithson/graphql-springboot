package com.tutorial.springboot.graphql.response;

import com.tutorial.springboot.graphql.entity.MemberType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MemberSearchResult {
    private int id;
    private String name;
    private String contact;
    private MemberType type;
    private List<SearchResult> subjectData;
}
