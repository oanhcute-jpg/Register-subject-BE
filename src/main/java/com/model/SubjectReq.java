package com.model;

import lombok.Data;

@Data
public class SubjectReq {
    int page;
    int pageSize;
    String search;
}
