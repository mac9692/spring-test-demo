package me.jinseong.springtestdemo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

// 언더바를 공백으로 치환해주는 설정
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

// 테스트 클래스를 매 테스트마다 객체 생성하는게 아니라 한번만 생성함
// 이 상태로는 @BeforeAll 이나 @AfterAll 메소드가 static 메소드일 필요가 없어짐
// 시나리오 테스트를 할 경우는 필요함. 회원가입 -> 로그인 -> 구매 이런 식의 시나리오 테스트
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// 테스트 순서를 설정하는 어노테이션 @Order 의 값이 낮을수록 더 높은 우선순위를 가짐
// 바로 위 @TestInstance 어노테이션과는 연관없음.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @DisplayName("7-1. @Tag 테스트")
    void tag_test1() {
//        Edit configurations 에서 Hint 타입을 tags 로 변경 후 태그값 설정
        System.out.println("StudyTest.tag_test");
    }

    @Test
    @Tag("slow")
    @DisplayName("7-1. @Tag 테스트")
    void tag_test2() {
//        Edit configurations 에서 Hint 타입을 tags 로 변경 후 태그값 설정
        System.out.println("StudyTest.tag_test2");
    }

    //    @Tag 어노테이션을 사용했을 때 리터럴을 사용하기 때문에 type safe 하지 않다.
    @FastTest
    @DisplayName("8-1. 커스텀 어노테이션 테스트")
    void custom_annotations_test1() {
        System.out.println("StudyTest.custom_annotations_test1");
    }

    @SlowTest
    @DisplayName("8-1. 커스텀 어노테이션 테스트")
    void custom_annotations_test2() {
        System.out.println("StudyTest.custom_annotations_test2");
    }

    //    매 번 실행 할 때마다 랜덤 값을 쓴다거나, 테스트를 실행하는 타이밍에 따라 결과가 달라지는 경우
//    테스트를 여러 번 해야 할 경우가 필요하다.
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    @DisplayName("9-1. 반복 테스트")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("StudyTest.repeatTest : " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @ParameterizedTest(name = "{index} : {displayName} message={0}")
    @ValueSource(strings = {"A", "B", "C", "D"})
    @DisplayName("9-2. 입력값이 바뀌는 반복 테스트")
    void parameterized_test(String message) {
        System.out.println(message);
    }

    @ParameterizedTest(name = "{index} : {displayName} message={0}")
    @EmptySource
    @DisplayName("9-3. 입력값이 바뀌는 반복 테스트")
    void parameterized_empty_test(String message) {
        System.out.println(message);
    }

    @ParameterizedTest(name = "{index} : {displayName} message={0}")
    @NullAndEmptySource
    @DisplayName("9-4. 입력값이 바뀌는 반복 테스트")
    void parameterized_null_and_empty_test(String message) {
        System.out.println(message);
    }

    @ParameterizedTest(name = "{index} : {displayName} message={0}")
    @ValueSource(ints = {10, 20, 30})
    @DisplayName("9-5. 입력값이 바뀌는 반복 테스트")
    void parameterized_integer_test(Integer integer) {
        System.out.println(integer);
    }

    @ParameterizedTest(name = "{index} : {displayName} message={0}")
    @ValueSource(ints = {10, 20, 30})
    @DisplayName("9-6. 입력값이 바뀌는 반복 테스트(단일 값)")
    void parameterized_study_test1(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study);
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            assertEquals(Study.class, aClass, "Study 클래스만 변환 가능합니다.");
            return new Study(Integer.parseInt(o.toString()));
        }
    }

    @ParameterizedTest(name = "{index} : {displayName} message={0}, {1}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    @DisplayName("9-7. 입력값이 바뀌는 반복 테스트(다중 값)1")
    void parameterized_study_test2(Integer limit, String name) {
        Study study = new Study(limit, name);
        System.out.println(study);
    }

    @ParameterizedTest(name = "{index} : {displayName} message={0}, {1}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    @DisplayName("9-7. 입력값이 바뀌는 반복 테스트(다중 값)2")
    void parameterized_study_test3(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study);
    }

    @ParameterizedTest(name = "{index} : {displayName} message={0}, {1}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    @DisplayName("9-7. 입력값이 바뀌는 반복 테스트(다중 값)3")
    void parameterized_study_test4(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    //ArgumentsAggregator 구현체는 반드시 static inner 클래스이거나 public 클래스여야 합니다.
    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }

}