package com.airtribe.learntrack.repository;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.learntrack.entity.Course;

public class CourseRepository {
    private final List<Course> courses = new ArrayList<>();

    public void save(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course must not be null.");
        }
        if (findById(course.getId()) != null) {
            throw new IllegalArgumentException("Course with ID " + course.getId() + " already exists.");
        }
        courses.add(course);
    }

    public Course findById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }
}