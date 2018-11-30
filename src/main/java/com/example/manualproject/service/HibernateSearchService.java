package com.example.manualproject.service;

import com.example.manualproject.model.Instruction;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class HibernateSearchService {
    @Autowired
    private final EntityManager entityManager;


    @Autowired
    public HibernateSearchService(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public void initializeHibernateSearch() {

        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public List<Instruction> search(String searchTerm, String searchBy) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Instruction.class).get();
        Query luceneQuery = null;
        if (searchBy.equals("global")) luceneQuery = queryForGlobalSearch(qb, searchTerm);
        if (searchBy.equals("tag")) luceneQuery = queryForTagSearch(qb, searchTerm);
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Instruction.class);
        List<Instruction> instructions = null;
        try {
            instructions = jpaQuery.getResultList();
        } catch (NoResultException nre) {
        }
        return instructions;
    }

    Query queryForGlobalSearch(QueryBuilder qb, String searchTerm) {
        Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1)
                .onFields("name", "category.name", "steps.name", "steps.text", "tags.name", "author.name")
                .matching(searchTerm).createQuery();
        return luceneQuery;
    }

    Query queryForTagSearch(QueryBuilder qb, String searchTerm) {
        Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1)
                .onFields("tags.name")
                .matching(searchTerm).createQuery();
        return luceneQuery;
    }
}
