package me.jinseong.springtestdemo.study;

import me.jinseong.springtestdemo.domain.Member;
import me.jinseong.springtestdemo.domain.Study;
import me.jinseong.springtestdemo.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;
    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        assert memberService != null;
        assert studyRepository != null;

        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> optionalMember = memberService.findById(memberId);
        study.setOwner(optionalMember.orElseThrow(() -> new IllegalArgumentException("해당 Id 의 멤버가 존재하지 않습니다. : " + memberId)));

        Study newStudy = studyRepository.save(study);
        memberService.notify(newStudy);
        memberService.notify(optionalMember.get());

        return newStudy;
    }
}
