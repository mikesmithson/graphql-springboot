package com.tutorial.springboot.graphql.service;

import com.tutorial.springboot.graphql.entity.MemberType;
import com.tutorial.springboot.graphql.repository.MemberRepository;
import com.tutorial.springboot.graphql.response.StudentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public List<StudentResponse> getAllStudents() {
        log.debug("In Member service fetching students");
        return memberRepository.findByType(MemberType.STUDENT.toString())
                .stream()
                .map(member -> StudentResponse.builder()
                        .id(member.getId())
                        .name(String.join(" ", member.getFirstName(), member.getLastName()))
                        .contact(member.getContact())
                        .build())
                .toList();

    }
}
