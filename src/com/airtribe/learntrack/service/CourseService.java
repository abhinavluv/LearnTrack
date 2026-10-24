package com.airtribe.learntrack.service;

import java.util.List;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(String courseName, String description, int durationInWeeks) {
        Course course = new Course(IdGenerator.getNextCourseId(), courseName, description, durationInWeeks, true);
        courseRepository.save(course);
        return course;
    }

    public Course findCourseById(int id) {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course with ID " + id + " was not found.");
        }
        return course;
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    public void setCourseActive(int id, boolean active) {
        findCourseById(id).setActive(active);
    }
}