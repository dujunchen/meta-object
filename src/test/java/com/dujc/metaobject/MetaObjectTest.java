package com.dujc.metaobject;

import com.dujc.metaobject.bean.Student;
import com.dujc.metaobject.bean.Teacher;
import org.junit.Test;

import java.util.*;

public class MetaObjectTest {

    //bean nest property
    @Test
    public void test1() {
        Student student = new Student();
        student.setName("zhangsan");
        MetaObject metaObject = SystemMetaObject.forObject(student);
        Object name = metaObject.getValue("name");
        System.out.println(name);
        metaObject.setValue("age", 30);
        System.out.println(student.getAge());
    }

    //bean nest list
    @Test
    public void test2() {
        List<String> books = Arrays.asList("java", "c", "python");
        Student student = new Student();
        student.setBooks(books);
        MetaObject metaObject = SystemMetaObject.forObject(student);
        for (int i = 0; i < 3; i++) {
            Object book = metaObject.getValue(String.format("books[%d]", i));
            System.out.println(book);
        }
    }

    //bean nest map
    @Test
    public void test3() {
        Map<String, Object> scores = new HashMap();
        scores.put("语文", 80);
        scores.put("数学", 90);
        scores.put("英语", 100);
        Student student = new Student();
        student.setScores(scores);
        MetaObject metaObject = SystemMetaObject.forObject(student);
        Object score = metaObject.getValue("scores[数学]");
        System.out.println(score);
    }

    //bean nest bean
    @Test
    public void test4() {
        Teacher teacher = new Teacher();
        teacher.setTname("liulaoshi");
        Student student = new Student();
        student.setTeacher(teacher);
        MetaObject metaObject = SystemMetaObject.forObject(student);
        Object tName = metaObject.getValue("teacher.tname");
        System.out.println(tName);
    }

    //list
    @Test
    public void test5() {
        List<Object> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        MetaObject metaObject = SystemMetaObject.forObject(list);
        metaObject.add("4");
        System.out.println(list);
    }

    //map
    @Test
    public void test6() {
        Student stu1 = new Student();
        List<String> b1 = Arrays.asList("java", "c", "python");
        stu1.setBooks(b1);
        Student stu2 = new Student();
        List<String> b2 = Arrays.asList("c++", "scala", "javascript");
        stu2.setBooks(b2);
        Map<String, Object> map = new HashMap();
        map.put("stu1", stu1);
        map.put("stu2", stu2);
        MetaObject metaObject = SystemMetaObject.forObject(map);
        Object book = metaObject.getValue("stu1.books[2]");
        System.out.println(book);
    }
}
