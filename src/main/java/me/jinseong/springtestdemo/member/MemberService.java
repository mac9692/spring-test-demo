package me.jinseong.springtestdemo.member;

import me.jinseong.springtestdemo.domain.Member;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);
}
