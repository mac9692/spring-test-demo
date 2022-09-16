package me.jinseong.springtestdemo.study;

import me.jinseong.springtestdemo.domain.Member;
import me.jinseong.springtestdemo.domain.Study;
import me.jinseong.springtestdemo.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService() {
//        MemberService memberService = Mockito.mock(MemberService.class);
//        StudyRepository studyRepository = Mockito.mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

//    Mock 객체는 파라미터를 이용해서 생성할 수 있습니다.
    @Test
    void createNewStudy(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("p.naver.com");

//        Return 값이 있는 메소드, 연속적인 stubbing
        Mockito.when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

//        메소드 리턴값이 void 일 경우
        Mockito.doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertAll(
                () -> assertEquals("p.naver.com", memberService.findById(1L).get().getEmail()),
                () -> assertThrows(RuntimeException.class, () -> memberService.findById(2L)),
                () -> assertEquals(Optional.empty(), memberService.findById(3L)),
                () -> assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L))
        );
    }


    @Test
    void mockStudyTest(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("p.naver.com");

        Study study = new Study(10, "java study");

        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Mockito.when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);
        assertEquals(member, study.getOwner());

        Mockito.verify(memberService, Mockito.times(1)).notify(study);
        Mockito.verify(memberService, Mockito.times(1)).notify(member);
        Mockito.verifyNoMoreInteractions(memberService);
        Mockito.verify(memberService, Mockito.never()).validate(any());

        InOrder inOrder = Mockito.inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
    }
}