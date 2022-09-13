package me.jinseong.springtestdemo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("StudyTest.create");
    }

    @Test
    void create1() {
        System.out.println("StudyTest.create1");
    }

    @Test
    @Disabled
    void disabledTest() {
        System.out.println("StudyTest.disabledTest");
    }
}