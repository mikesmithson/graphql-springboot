package com.tutorial.springboot.graphql.service;

import com.tutorial.springboot.graphql.entity.Member;
import com.tutorial.springboot.graphql.entity.MemberType;
import com.tutorial.springboot.graphql.repository.MemberRepository;
import com.tutorial.springboot.graphql.response.MemberResponse;
import com.tutorial.springboot.graphql.response.MemberSearchResult;
import graphql.com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberResponse> getAllMembers(MemberType memberType) {
        log.debug("In Member service fetching members");
        List<Member> members;
        if (memberType.equals(MemberType.ALL)) {
            members = StreamSupport.stream(memberRepository.findAll().spliterator(), true).toList();
        } else {
            members = memberRepository.findByType(memberType.toString());
        }
        return members.stream()
                .map(member -> MemberResponse.builder()
                        .id(member.getId())
                        .name(String.join(" ", member.getFirstName(), member.getLastName()))
                        .contact(member.getContact())
                        .type(MemberType.valueOf(member.getType()))
                        .build())
                .toList();
    }

    public List<MemberSearchResult> getMemberByFirstName(String name) {
        return memberRepository.findByFirstNameStartsWith(name)
                .orElse(Lists.newArrayList()).stream().map(member ->
                MemberSearchResult.builder()
                        .id(member.getId())
                        .type(MemberType.valueOf(member.getType()))
                        .name(String.join(" ",member.getFirstName(),member.getLastName()))
                        .contact(member.getContact())
                        .build()).toList();
    }
}
