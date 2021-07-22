package org.example.courses;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        Configuration conf = new Configuration().configure();
        try(SessionFactory sessionFactory = conf.buildSessionFactory()){
            EntityManager entityManager = sessionFactory.createEntityManager();
            CourseEntityManager courseEntityManager = new CourseEntityManager();
            courseEntityManager.findAndPrintUpcomingLesson(entityManager, 1L);
            entityManager.close();
        }
    }
}
