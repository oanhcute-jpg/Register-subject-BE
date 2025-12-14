package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "subject")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên môn học (Ví dụ: Toán, Lý, Hóa)
    @Column()
    private String name;
    @Column()
    private Integer classPeriodNumber;

    // Mã môn học (VD: MATH101, PHY202)
    @Column()
    private String code;

    // Số tín chỉ
    @Column()
    private Integer credits;

    @Column(length = 100)
    private String department;
    @Column(length = 100)
    private String teacherName;
    @Column(length = 100)
    private String roomName;

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @Column()
    private Integer studentNumberMax;
    @Column()
    private LocalDate createdDate;
    @Column()
    private LocalDate updatedDate;
    @Column()
    private String userCreated;
    @Column()
    private String dayOfWeek;
    @Column()
    private Integer lessonStart;
    @Column()
    private Integer lessonEnd;
    @Column()
    private Boolean isRegister;


}
