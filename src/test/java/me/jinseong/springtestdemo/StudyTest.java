package me.jinseong.springtestdemo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

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

    @Test
    @DisplayName("6-1. 상황에 맞는 테스트 실행")
    void assume_test() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);

//        첫 번째 방법
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

//        두 번째 방법
        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {

        });

        assumingThat("TEST".equalsIgnoreCase(test_env), () -> {

        });

        Study actual = new Study(10);
        assertThat(actual.getLimit() < 0);
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    @DisplayName("6-2. @EnabledOnOs OS에 맞는 테스트 실행")
    void os_test() {
        Study actual = new Study(10);
        assertThat(actual.getLimit() < 0);
    }

    @Test
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9})
    @DisplayName("6-2. @EnabledOnJre JRE에 맞는 테스트 실행")
    void jre_test() {
        Study actual = new Study(10);
        assertThat(actual.getLimit() < 0);
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "TEST")
    @DisplayName("6-2. @EnabledIfEnvironmentVariables 환경변수에 맞는 테스트 실행")
    void environment_variables_test() {
        Study actual = new Study(10);
        assertThat(actual.getLimit() < 0);
    }


    //    테스트 태깅
//    테스트 실행 시간이 긴 지, 짧은 지
//    모듈 별로도 할 수 있고
//    단위 테스트인 지, 통합 테스트인 지
    @Test
    @Tag("fast")
    @DisplayName("7-1 @Tag 테스트")
    void tag_test1() {
//        Edit configurations 에서 Hint 타입을 tags 로 변경 후 태그값 설정
        System.out.println("StudyTest.tag_test");
    }

    @Test
    @Tag("slow")
    @DisplayName("7-1 @Tag 테스트")
    void tag_test2() {
//        Edit configurations 에서 Hint 타입을 tags 로 변경 후 태그값 설정
        System.out.println("StudyTest.tag_test2");
    }

//    @Tag 어노테이션을 사용했을 때 리터럴을 사용하기 때문에 type safe 하지 않다.
    @FastTest
    @DisplayName("8-1 커스텀 어노테이션 테스트")
    void custom_annotations_test1() {
        System.out.println("StudyTest.custom_annotations_test1");
    }

    @SlowTest
    @DisplayName("8-1 커스텀 어노테이션 테스트")
    void custom_annotations_test2() {
        System.out.println("StudyTest.custom_annotations_test2");
    }
}