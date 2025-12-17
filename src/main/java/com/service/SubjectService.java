package com.service;

import com.entity.SubjectEntity;
import com.entity.SubjectRegisterEntity;
import com.entity.User;
import com.model.RegisterSubject;
import com.model.SubjectReq;
import com.model.SubjectResp;
import com.repository.SubjectRegisterRepository;
import com.repository.SubjectRepository;
import com.util.Utils;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SubjectRegisterRepository subjectRegisterRepository;

    public SubjectEntity addSubject(SubjectEntity subjectEntity) {
        if (subjectEntity.getId() == null) {
            subjectEntity.setIsRegister(false);
        }
        return subjectRepository.save(subjectEntity);
    }

    public SubjectEntity openRegister(SubjectEntity subjectEntity) {
        if (subjectEntity.getId() != null) {
            com.entity.SubjectEntity subject = subjectRepository.getById(subjectEntity.getId());
            subject.setIsRegister(subjectEntity.getIsRegister());
            return subjectRepository.save(subject);
        }
        return subjectEntity;
    }

    public void registerSubject(SubjectRegisterEntity subjectEntity) {
        subjectRegisterRepository.save(subjectEntity);
    }

    public List<SubjectRegisterEntity> findSubjectRegisterByIdAndUser(String userName, Long subjectId) {
        return subjectRegisterRepository.findByUserCreatedAndSubjectId(userName, subjectId);
    }

    public boolean checkNumberRegister(SubjectRegisterEntity subjectRegisterEntity) {
        Integer registerNumber = subjectRegisterRepository.countBySubjectId(subjectRegisterEntity.getSubjectId());
        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectRegisterEntity.getSubjectId());
        if (subjectEntity.isPresent()) {
            if (registerNumber < subjectEntity.get().getStudentNumberMax()) {
                return true;
            }
        }
        return false;
    }

    public void deleteRegisterSubjectById(Long id) {
        subjectRegisterRepository.deleteById(id);
    }


    public SubjectEntity getById(Long id) {
        return subjectRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(Long id) {
        subjectRegisterRepository.deleteBySubjectId(id);
        subjectRepository.deleteById(id);
    }

    public List<RegisterSubject> getAllEvents(User user, String type) {
        List<SubjectRegisterEntity> subjectRegisterEntities = subjectRegisterRepository.findByUserCreated(user.getUsername());
        List<RegisterSubject> registerSubjects = new ArrayList<>();
        if (subjectRegisterEntities != null) {

            subjectRegisterEntities.forEach(subjectEntity -> {
                if (subjectEntity.getSubjectId() != null) {
                    Optional<SubjectEntity> subjectEntityOne = subjectRepository.findById(subjectEntity.getSubjectId());

                    if (subjectEntityOne.isPresent()) {
                        Integer studentNow = subjectRegisterRepository.countBySubjectId(subjectEntity.getSubjectId());
                        SubjectEntity subjectEntity1 = subjectEntityOne.get();
                        RegisterSubject registerSubject = new RegisterSubject();
                        registerSubject.setStartTime(getTime(subjectEntity1.getLessonStart()));
                        registerSubject.setEndTime(getTimeEnd(subjectEntity1.getLessonEnd()));
                        registerSubject.setCode(subjectEntity1.getCode());
                        registerSubject.setTitle(subjectEntity1.getName());
                        registerSubject.setRoomName(subjectEntity1.getRoomName());
                        registerSubject.setTeacherName(subjectEntity1.getTeacherName());
                        registerSubject.setId(subjectEntity.getId());
                        registerSubject.setDaysOfWeek(dayOfWeek(subjectEntity1.getDayOfWeek()));
                        if (!type.equals("register")) {
                            registerSubject.setEndRecur(Utils.convertDateToString(subjectEntity1.getEndDate(), "yyyy-MM-dd"));
                            registerSubject.setStartRecur(Utils.convertDateToString(subjectEntity1.getStartDate(), "yyyy-MM-dd"));
                        }

                        registerSubject.setMaxStudent(studentNow + "/" + subjectEntity1.getStudentNumberMax());
                        registerSubjects.add(registerSubject);

                    }
                }

            });
        }
        return registerSubjects;
    }

    public Map<String, Object> getAll(SubjectReq subjectReq) {
        int pageSize = subjectRepository.findAll().size() / 8;
        if (subjectRepository.findAll().size() % 8 != 0) {
            pageSize = subjectRepository.findAll().size() / 8 + 1;
        }
        Page<SubjectEntity> subjectEntities = subjectRepository.findAllSubjects(PageRequest.of(subjectReq.getPage(), 8));
        List<SubjectResp> subjectResp = new ArrayList<>();
        subjectEntities.forEach(subjectEntity -> subjectResp.add(SubjectResp.ConvertToSubjectResp(subjectEntity)));
        Map<String, Object> map = new HashMap<>();
        map.put("subjectResponse", subjectResp);
        map.put("pageSize", pageSize);
        return map;

    }

    public List<SubjectResp> getRegisterSubjects(String dayOfWeek, Integer lessonStart) {
        List<SubjectEntity> subjectEntities = subjectRepository.findAllByDayOfWeekAndLessonStartAndIsRegister(dayOfWeek, lessonStart, true);
        List<SubjectResp> subjectResp = new ArrayList<>();
        subjectEntities.forEach(subjectEntity ->{
            Integer registerNumber = subjectRegisterRepository.countBySubjectId(subjectEntity.getId());
            SubjectResp subjectResp1 = SubjectResp.ConvertToSubjectResp(subjectEntity);
            subjectResp1.setStudentNumberNow(registerNumber);
            subjectResp.add(subjectResp1)  ;
        });
        return subjectResp;

    }

    public String getTime(Integer lesson) {
        String time = "";
        switch (lesson) {
            case 1:
                time = "07:00:00";
                break;
            case 2:
                time = "08:00:00";
                break;
            case 3:
                time = "09:00:00";
                break;
            case 4:
                time = "10:00:00";

                break;
            case 5:
                time = "11:00:00";
                break;
            case 6:
                time = "13:00:00";
                break;
            case 7:
                time = "14:00:00";
                break;
            case 8:
                time = "15:00:00";
                break;
            case 9:
                time = "16:00:00";
                break;
            case 10:
                time = "17:00:00";
                break;
        }
        return time;

    }

    public String getTimeEnd(Integer lesson) {
        String time = "";
        switch (lesson) {
//            case 1:
//                time = "07:00:00";
//                break;
            case 1:
                time = "08:00:00";
                break;
            case 2:
                time = "09:00:00";
                break;
            case 3:
                time = "10:00:00";

                break;
            case 4:
                time = "11:00:00";
                break;
            case 5:
                time = "13:00:00";
                break;
            case 6:
                time = "14:00:00";
                break;
            case 7:
                time = "15:00:00";
                break;
            case 8:
                time = "16:00:00";
                break;
            case 9:
                time = "17:00:00";
                break;
            case 10:
                time = "18:00:00";
                break;
        }
        return time;

    }

    public List<Integer> dayOfWeek(String time) {
        List<Integer> integers = new ArrayList<>();
        switch (time) {
            case "Thứ 2":
                integers.add(1);
                break;
            case "Thứ 3":
                integers.add(2);
                break;
            case "Thứ 4":
                integers.add(3);
                break;
            case "Thứ 5":
                integers.add(4);

                break;
            case "Thứ 6":
                integers.add(5);
                break;
            case "Thứ 7":
                integers.add(6);
                break;
            case "CN":
                integers.add(7);
                break;

        }
        return integers;

    }
}
