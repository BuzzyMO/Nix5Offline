package org.example.courses.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer value;

    @OneToMany(mappedBy = "grade")
    private List<LessonRegistration> lessonRegistrations;

    public Grade(){
        lessonRegistrations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<LessonRegistration> getLessonRegistrations() {
        return lessonRegistrations;
    }

    public void setLessonRegistrations(List<LessonRegistration> lessonRegistrations) {
        this.lessonRegistrations = lessonRegistrations;
    }

    public void addLessonRegistration(LessonRegistration lessonRegistration){
        lessonRegistrations.add(lessonRegistration);
        lessonRegistration.setGrade(this);
    }
}
