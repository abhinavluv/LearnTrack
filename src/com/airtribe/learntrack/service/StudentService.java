package com.airtribe.learntrack.service;

import java.util.List;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(String firstName, String lastName, String email, String batch) {
        Student student = new Student(IdGenerator.getNextStudentId(), firstName, lastName, email, batch, true);
        studentRepository.save(student);
        return student;
    }

    public Student addStudent(String firstName, String lastName, String batch) {
        Student student = new Student(IdGenerator.getNextStudentId(), firstName, lastName, batch, true);
        studentRepository.save(student);
        return student;
    }

    public Student findStudentById(int id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new EntityNotFoundException("Student with ID " + id + " was not found.");
        }
        return student;
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public void updateStudent(Student updatedStudent) {
        Student student = findStudentById(updatedStudent.getId());
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setEmail(updatedStudent.getEmail());
        student.setBatch(updatedStudent.getBatch());
        student.setActive(updatedStudent.isActive());
    }

    public void deactivateStudent(int id) {
        findStudentById(id).setActive(false);
    }

    public void removeStudent(int id) {
        findStudentById(id);
        studentRepository.removeById(id);
    }
}