package me.jinseong.springtestdemo.member;

import me.jinseong.springtestdemo.domain.Member;
import me.jinseong.springtestdemo.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
