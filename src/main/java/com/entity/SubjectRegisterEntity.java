package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "subject_register")
@Data
public class SubjectRegisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private Long subjectId;
    @Column()
    private LocalDate createdDate;
    @Column()
    private LocalDate updatedDate;
    @Column()
    private String userCreated;
}
