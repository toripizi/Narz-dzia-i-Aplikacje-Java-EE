package com.toripizi.farmhub.category.repository;

import com.toripizi.farmhub.category.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CategoryPersistenceRepository implements CategoryRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Category> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Category> find(UUID id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    @Override
    public void create(Category entity) {
        em.persist(entity);
    }

    @Override
    public void update(Category entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Category entity) {
        /* Clearing cache used as workaround when not handling both sides of relationships, not recommended. */
        em.getEntityManagerFactory().getCache().evictAll(); //Clearing 2nd level cache.
        em.clear(); //Clearing 1st level cache.
        em.remove(em.find(Category.class, entity.getId()));
    }

    @Override
    public void detach(Category entity) {
        em.detach(entity);
    }
}
