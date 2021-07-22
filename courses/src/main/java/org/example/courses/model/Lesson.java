package org.example.courses.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    @Access(AccessType.PROPERTY)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    @Access(AccessType.PROPERTY)
    private Lecturer lecturer;

    @OneToMany(mappedBy = "lesson")
    private List<LessonRegistration> lessonRegistrations;

    public Lesson(){
        lessonRegistrations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public List<LessonRegistration> getLessonRegistrations() {
        return lessonRegistrations;
    }

    public void setLessonRegistrations(List<LessonRegistration> lessonRegistrations) {
        this.lessonRegistrations = lessonRegistrations;
    }

    public void addLessonRegistration(LessonRegistration lessonRegistration){
        lessonRegistrations.add(lessonRegistration);
        lessonRegistration.setLesson(this);
    }
}
