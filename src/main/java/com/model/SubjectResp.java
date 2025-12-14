package com.model;

import com.util.Utils;
import com.entity.SubjectEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
public class SubjectResp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private Integer credits;
    private String department;
    //    private String registrationStart;
//    private String registrationEnd;
    private String teacherName;
    private String roomName;
    private String startDate;
    private String endDate;
    private Integer studentNumberMax;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String userCreated;
    private String dayOfWeek;
    private Integer lessonStart;
    private Integer lessonEnd;
    private Integer classPeriodNumber;
    private Integer page;
    private Integer pageSize;
    private Boolean isRegister;


    public static SubjectResp ConvertToSubjectResp(SubjectEntity subjectEntity) {
        if (subjectEntity == null) {
            return null;
        }
        SubjectResp subjectResp = new SubjectResp();
        BeanUtils.copyProperties(subjectEntity, subjectResp);
        subjectResp.setStartDate(Utils.convertDateToString(subjectEntity.getStartDate(), "yyyy-MM-dd"));
        subjectResp.setEndDate(Utils.convertDateToString(subjectEntity.getEndDate(), "yyyy-MM-dd"));
        // subjectResp.setRegistrationStart(Utils.convertDateToString(subjectEntity.getRegistrationStart(),"yyyy-MM-dd"));
        // subjectResp.setRegistrationEnd(Utils.convertDateToString(subjectEntity.getRegistrationEnd(),"yyyy-MM-dd"));
        return subjectResp;
    }
}
