package org.example.courses.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @Access(AccessType.PROPERTY)
    private Group group;

    @OneToMany(mappedBy = "student")

    private List<LessonRegistration> lessonRegistrations;

    public Student(){
        lessonRegistrations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<LessonRegistration> getLessonRegistrations() {
        return lessonRegistrations;
    }

    public void setLessonRegistrations(List<LessonRegistration> lessonRegistrations) {
        this.lessonRegistrations = lessonRegistrations;
    }

    public void addLessonRegistration(LessonRegistration lessonRegistration){
        lessonRegistrations.add(lessonRegistration);
        lessonRegistration.setStudent(this);
    }
}
