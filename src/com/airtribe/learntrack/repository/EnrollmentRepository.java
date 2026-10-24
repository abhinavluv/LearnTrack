package com.airtribe.learntrack.repository;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.learntrack.entity.Enrollment;

public class EnrollmentRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    public void save(Enrollment enrollment) {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment must not be null.");
        }
        if (findById(enrollment.getId()) != null) {
            throw new IllegalArgumentException("Enrollment with ID " + enrollment.getId() + " already exists.");
        }
        enrollments.add(enrollment);
    }

    public Enrollment findById(int id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        return null;
    }

    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                result.add(enrollment);
            }
        }
        return result;
    }
}