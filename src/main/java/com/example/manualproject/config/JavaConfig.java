package com.example.manualproject.config;

import com.example.manualproject.service.HibernateSearchService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
public class JavaConfig {
    @Autowired
    private EntityManager entityManager;

    @Bean
    public SessionFactory sessionFactory(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        return emf.unwrap(SessionFactory.class);
    }

    @Bean
    HibernateSearchService hibernateSearchService() {
        HibernateSearchService hibernateSearchService = new HibernateSearchService(entityManager);
        hibernateSearchService.initializeHibernateSearch();
        return hibernateSearchService;
    }

}
