package com.repository;

import com.entity.SubjectEntity;
import com.entity.SubjectRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRegisterRepository extends JpaRepository<SubjectRegisterEntity, Long> {
    public void deleteById(Long id);
    public List<SubjectRegisterEntity> findByUserCreated(String userCreated);
    public void deleteBySubjectId(Long id);
    public Integer countBySubjectId(Long id);

}
