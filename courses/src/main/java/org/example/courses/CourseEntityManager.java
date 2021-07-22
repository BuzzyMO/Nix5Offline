package org.example.courses;

import org.example.courses.model.Lesson;
import org.example.courses.model.LessonRegistration;
import org.example.courses.model.Student;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Clock;
import java.util.Comparator;
import java.util.List;

public class CourseEntityManager {

    public void findAndPrintUpcomingLesson(EntityManager entityManager, Long studentId){
        Student student = entityManager.find(Student.class, studentId);
        Lesson upcomingLesson = getUpcomingLesson(student.getLessonRegistrations());
        System.out.println("Upcoming Lesson \n" +
                "Date and Time: " + upcomingLesson.getTimestamp() +"\n"+
                "Topic: " + upcomingLesson.getTopic() + "\n" +
                "Lecturer: " + upcomingLesson.getLecturer()
                );

    }

    private Lesson getUpcomingLesson(List<LessonRegistration> lessonRegistrations){
        Timestamp currentTimestamp = new Timestamp(Clock.systemUTC().millis());
        lessonRegistrations.sort(Comparator.comparing(a -> a.getLesson().getTimestamp()));
        for(LessonRegistration lReg : lessonRegistrations){
            Lesson lesson = lReg.getLesson();
            Timestamp lessonTimestamp = lesson.getTimestamp();
            if(lessonTimestamp.after(currentTimestamp)){
                return lesson;
            }
        }
        throw new RuntimeException("upcoming lesson isn't exists");
    }
}
