package com.example.verifiserer.model;

import jakarta.persistence.*;
@Entity
@Table(name = "diploma")
public class Vitnemal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    @Column(name = "degree_name", nullable = false)
    private String degreeName;

    @Column(name = "graduation_date", nullable = false)
    private String graduationDate;

    public Vitnemal(Long id, Student student, Institution institution, String degreeName, String graduationDate) {
        this.id = id;
        this.student = student;
        this.institution = institution;
        this.degreeName = degreeName;
        this.graduationDate = graduationDate;
    }

    public Vitnemal() {

    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }
}
