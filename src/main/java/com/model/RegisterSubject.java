package com.model;

import lombok.Data;

import java.util.List;

@Data
public class RegisterSubject {
    Long id;
    String title;
    String code;
    String group;
    String roomName;
    String teacherName;
    List<Integer> daysOfWeek;
    String startTime;
    String endTime;
    String startRecur;
    String endRecur;
    String maxStudent;

}
