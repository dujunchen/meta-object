package com.dujc.metaobject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private int id;
    private String name;
    private int age;
    private List<String> books;
    private Map<String, Object> scores;
    private Teacher teacher;
}
