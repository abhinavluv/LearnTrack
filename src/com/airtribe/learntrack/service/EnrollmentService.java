package com.airtribe.learntrack.service;

import java.time.LocalDate;
import java.util.List;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;

public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Enrollment enrollStudent(int studentId, int courseId) {
        if (studentRepository.findById(studentId) == null) {
            throw new EntityNotFoundException("Student with ID " + studentId + " was not found.");
        }
        if (courseRepository.findById(courseId) == null) {
            throw new EntityNotFoundException("Course with ID " + courseId + " was not found.");
        }

        Enrollment enrollment = new Enrollment(IdGenerator.getNextEnrollmentId(), studentId, courseId,
                LocalDate.now(), EnrollmentStatus.ACTIVE);
        enrollmentRepository.save(enrollment);
        return enrollment;
    }

    public List<Enrollment> listEnrollmentsForStudent(int studentId) {
        if (studentRepository.findById(studentId) == null) {
            throw new EntityNotFoundException("Student with ID " + studentId + " was not found.");
        }
        return enrollmentRepository.findByStudentId(studentId);
    }

    public void updateStatus(int enrollmentId, EnrollmentStatus status) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment with ID " + enrollmentId + " was not found.");
        }
        enrollment.setStatus(status);
    }
}