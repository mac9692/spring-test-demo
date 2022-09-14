package me.jinseong.springtestdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

// 언더바를 공백으로 치환해주는 설정
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {
    // 모든 테스트 실행 전 단 한번만 실행
    // 반드시 static 사용
    // private 사용 불가 default 사용 가능 , return 불가
    @BeforeAll
    static void beforeAll() {
        System.out.println("StudyTest.beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("StudyTest.afterAll");
    }

    // BeforeEach, AfterEach 각각의 테스트를 시작하기 이전과 이후에 실행 됨
    // static 일 필요는 없음
    @BeforeEach
    void beforeEach() {
        System.out.println("StudyTest.beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("StudyTest.afterEach");
    }



    @Test
    @DisplayName("1. 다중 테스트")
    void create_new_study() {
        Study study = new Study(-10);
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT 여야 합니다."),
                () -> assertTrue(1 < 2),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 합니다.")
        );
        System.out.println("StudyTest.create_new_study");
    }

    @Test
    @DisplayName("2. @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) 테스트")
    void create_new_study_again() {
        System.out.println("StudyTest.create_new_study_again");
    }

    @Test
    @Disabled
    @DisplayName("3. @Disabled 테스트")
    void disabledTest() {
        System.out.println("StudyTest.disabledTest");
    }

    @Test
    @DisplayName("4. 예외 발생 테스트")
    void throws_exception_test() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Study(-10));

        assertEquals("limit은 0보다 커야 합니다.", exception.getMessage());

        System.out.println("StudyTest.throws_exception_test");
    }

    @Test
    @DisplayName("5. 수행 시간 테스트")
    void timeout_test() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
        System.out.println("StudyTest.timeout_test");
    }
}