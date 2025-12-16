package com.controller;


import com.entity.SubjectEntity;
import com.entity.SubjectRegisterEntity;
import com.entity.User;
import com.model.RegisterSubject;
import com.model.SubjectReq;
import com.model.SubjectResp;
import com.service.SubjectService;
import com.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/subject")
@CrossOrigin(origins = "http://localhost:4200")
public class SubjectController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SubjectService subjectService;


    @PostMapping("/add")
    public ResponseEntity<SubjectEntity> addSubject(@RequestBody SubjectEntity subjectEntity) {
        SubjectEntity subjectEntitySave = subjectService.addSubject(subjectEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectEntitySave);
    }
    @PostMapping("/open/register")
    public ResponseEntity<SubjectEntity> openRegister(@RequestBody SubjectEntity subjectEntity) {
        if(subjectEntity.getIsRegister()){
            subjectEntity.setIsRegister(false);
        }else {
            subjectEntity.setIsRegister(true);
        }
        SubjectEntity subjectEntitySave = subjectService.openRegister(subjectEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectEntitySave);
    }

    @DeleteMapping("/delete/{subjectId}")
    public ResponseEntity<?> addSubject(@PathVariable Long subjectId) {
        subjectService.deleteById(subjectId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Thành công");
    }

    @PostMapping("/delete/register-subject")
    public ResponseEntity<String> deleteRegisterSubject(@RequestBody SubjectRegisterEntity subjectEntity) {
        subjectService.deleteRegisterSubjectById(subjectEntity.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PostMapping("/register-subject")
    public ResponseEntity<?> addSubject(HttpServletRequest request, @RequestBody SubjectRegisterEntity subjectEntity) {
        User user = jwtUtil.getUserByToken(request);
        subjectEntity.setUserCreated(user.getUsername());
        Map<String,Object> map = new HashMap<>();
        List<SubjectRegisterEntity> subjectRegisterEntity=subjectService.findSubjectRegisterByIdAndUser(user.getUsername(),subjectEntity.getSubjectId());
        if(subjectRegisterEntity!=null&&subjectRegisterEntity.size()>0){
            map.put("status","error");
            map.put("message","Bạn đã đăng kí môn này");
            return ResponseEntity.status(HttpStatus.CREATED).body(map);
        }
        if(!subjectService.checkNumberRegister(subjectEntity)){
            map.put("status","error");
            map.put("message","Số lượng sinh viên đã đủ.");
            return ResponseEntity.status(HttpStatus.CREATED).body(map);
        }
        map.put("status","Thành công");
        map.put("message","Đăng kí thành công");
        subjectService.registerSubject(subjectEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @GetMapping("/get/register-subject")
    public ResponseEntity<SubjectEntity> getById(@PathVariable Long id) {
        SubjectEntity subjectEntitySave = subjectService.getById(id);
        // List<SubjectEntity>subjectEntities =  new ArrayList<>();
        //  subjectEntities.add(subjectEntitySave);
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectEntitySave);
    }

    @GetMapping("/get/register-subject-all/{type}")
    public ResponseEntity<List<RegisterSubject>> getAllEverySubject(HttpServletRequest request,@PathVariable String type) {
        //  SubjectEntity subjectEntitySave= subjectService.getById(id);
        User user = jwtUtil.getUserByToken(request);
        List<RegisterSubject> subjectEntities = subjectService.getAllEvents(user,type);
        ;
        //  subjectEntities.add(subjectEntitySave);
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectEntities);
    }

    @PostMapping("/get/all")
    public ResponseEntity<?> getAll(@RequestBody SubjectReq  subjectReq) {
        //  SubjectEntity subjectEntitySave= subjectService.getById(id);
        Map<String,Object> subjectEntities = subjectService.getAll(subjectReq);
        ;
        //  subjectEntities.add(subjectEntitySave);
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectEntities);
    }

    @GetMapping("/get/list-register-subject")
    public ResponseEntity<List<SubjectResp>> listRegisterSubject(@RequestParam String dayOfWeek, @RequestParam Integer lessonStart) {
        //  SubjectEntity subjectEntitySave= subjectService.getById(id);
        List<SubjectResp> subjectEntities = subjectService.getRegisterSubjects(dayOfWeek, lessonStart);
        ;
        //  subjectEntities.add(subjectEntitySave);
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectEntities);
    }
}
