package org.example.courses;

import org.example.courses.model.Lesson;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.Instant;

public class CourseEntityManager {

    public void findAndPrintUpcomingLesson(EntityManager entityManager, Long studentId) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Lesson> query = entityManager.createQuery("SELECT l FROM Grade g " +
                    "INNER JOIN Lesson l on l.id = g.lesson.id\n" +
                    "INNER JOIN Student s on s.id = g.student.id\n" +
                    "WHERE g.student.id =:studentId AND l.timestamp > :timestamp\n" +
                    "ORDER BY l.timestamp", Lesson.class);
            query.setParameter("studentId", studentId);
            query.setParameter("timestamp", Timestamp.from(Instant.now()));
            Lesson upcomingLesson = query.getSingleResult();
            System.out.println("Upcoming Lesson \n" +
                    "Date and Time: " + upcomingLesson.getTimestamp() + "\n" +
                    "Topic: " + upcomingLesson.getTopic() + "\n" +
                    "Lecturer: " + upcomingLesson.getLecturer());
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }

    }
}
