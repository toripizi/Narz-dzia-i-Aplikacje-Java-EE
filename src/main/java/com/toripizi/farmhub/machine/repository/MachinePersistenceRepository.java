package com.toripizi.farmhub.machine.repository;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.machine.entity.Machine;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
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
    public List<Machine> findAllByFarmer(Farmer farmer) {
        return em.createQuery("select t from Machine t where t.farmer = :farmer", Machine.class)
                .setParameter("farmer", farmer)
                .getResultList();
    }

    @Override
    public List<Machine> findAllByCategory(Category category) {
        return em.createQuery("select m from Machine m where m.category = :category", Machine.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public List<Machine> findAllByCategoryAndFarmer(Category category, Farmer farmer) {
        return em.createQuery("select t from Machine t where t.category = :category and t.farmer = :farmer", Machine.class)
                .setParameter("category", category)
                .setParameter("farmer", farmer)
                .getResultList();
    }

    @Override
    public Optional<Machine> find(UUID id) {
        return Optional.ofNullable(em.find(Machine.class, id));
    }

    @Override
    public Optional<Machine> findByIdAndFarmer(UUID machineId, Farmer farmer) {
        try {
            return Optional.of(em.createQuery("select t from Machine t where t.id = :id and t.farmer = :farmer", Machine.class)
                    .setParameter("farmer", farmer)
                    .setParameter("id", machineId)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
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
