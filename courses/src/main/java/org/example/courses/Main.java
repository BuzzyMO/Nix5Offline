package org.example.courses;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        long studentId;
        if (args.length > 0) {
            studentId = Long.parseLong(args[0]);
        } else {
            throw new RuntimeException("You should set studentId as arg");
        }
        Configuration conf = new Configuration().configure();
        try (SessionFactory sessionFactory = conf.buildSessionFactory()) {
            Session entityManager = (Session) sessionFactory.createEntityManager();
            CourseEntityManager courseEntityManager = new CourseEntityManager();
            courseEntityManager.findAndPrintUpcomingLesson(entityManager, studentId);
        }
    }
}
