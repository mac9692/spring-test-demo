package me.jinseong.springtestdemo.study;


import me.jinseong.springtestdemo.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
