package com.toripizi.farmhub.category.repository;

import com.toripizi.farmhub.category.entity.Category;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class CategoryPersistenceRepository implements CategoryRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select o from Category o", Category.class).getResultList();
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
}
