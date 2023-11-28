package com.toripizi.farmhub.machine.repository;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.machine.entity.Machine;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class MachinePersistenceRepository implements MachineRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Machine> findAll() {
        return em.createQuery("select t from Machine t", Machine.class).getResultList();
    }

    @Override
    public List<Machine> findAllByCategory(Category category) {
        return em.createQuery("select m from Machine m where m.category = :category", Machine.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public Optional<Machine> find(UUID id) {
        return Optional.ofNullable(em.find(Machine.class, id));
    }

    @Override
    public void create(Machine entity) {
        em.persist(entity);
    }

    @Override
    public void update(Machine entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Machine entity) {
        em.remove(em.find(Machine.class, entity.getId()));
    }
}
