package com.tutorial.springboot.graphql.service;

import com.tutorial.springboot.graphql.entity.MemberType;
import com.tutorial.springboot.graphql.repository.MemberRepository;
import com.tutorial.springboot.graphql.response.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ResultService resultService;


    public List<StudentResponse> getAllStudents() {
        return memberRepository.findByType(MemberType.STUDENT.toString())
                .stream()
                .map(member -> StudentResponse.builder()
                        .id(member.getId())
                        .name(String.join(" ", member.getFirstName(), member.getLastName()))
                        .contact(member.getContact())
                        .result(resultService.getResultForStudent(member.getId()))
                        .build())
                .toList();

    }
}
