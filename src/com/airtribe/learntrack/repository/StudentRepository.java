package com.airtribe.learntrack.repository;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.learntrack.entity.Student;

public class StudentRepository {
    private final List<Student> students = new ArrayList<>();

    public void save(Student student) {
        students.add(student);
    }

    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public boolean removeById(int id) {
        Student student = findById(id);
        return student != null && students.remove(student);
    }
}