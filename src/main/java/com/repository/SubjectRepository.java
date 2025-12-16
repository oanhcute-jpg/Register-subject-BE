package com.repository;

import com.entity.SubjectEntity;
import com.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    @Query("SELECT s FROM SubjectEntity s order by id desc limit 8")
    List<SubjectEntity> findAllSubjects();

    @Query(value = "SELECT * FROM subject ORDER BY id DESC",
            countQuery = "SELECT count(*) FROM subject",
            nativeQuery = true)
    Page<SubjectEntity> findAllSubjects(Pageable pageable);

    //    @Query("SELECT s FROM SubjectEntity s where order by id desc")
    List<SubjectEntity> findAllByDayOfWeekAndLessonStart(String dayOfWeek, Integer lessonStart);
    List<SubjectEntity> findAllByDayOfWeekAndLessonStartAndIsRegister(String dayOfWeek, Integer lessonStart,Boolean isRegister);
    public void deleteById(Long id);
}
