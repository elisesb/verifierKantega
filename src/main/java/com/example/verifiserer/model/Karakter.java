package com.example.verifiserer.model;
import jakarta.persistence.*;

@Entity
@Table(name = "grade")

public class Karakter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diploma_id", nullable = false)
    private Vitnemal diploma;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "course_code", nullable = false)
    private String courseCode;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private int year;

    public Karakter(Long id, Vitnemal diploma, String courseName, String courseCode, String grade, int year) {
        this.id = id;
        this.diploma = diploma;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.grade = grade;
        this.year = year;
    }

    public Karakter() {

    }
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vitnemal getDiploma() {
        return diploma;
    }

    public void setDiploma(Vitnemal diploma) {
        this.diploma = diploma;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
// Getters and Setters
