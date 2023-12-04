package com.toripizi.farmhub.machine.repository;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.machine.entity.Machine;
import com.toripizi.farmhub.machine.entity.Machine_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MachinePersistenceRepository implements MachineRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Machine> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Machine> query = cb.createQuery(Machine.class);
        Root<Machine> root = query.from(Machine.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Machine> findAllByFarmer(Farmer farmer) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Machine> query = cb.createQuery(Machine.class);
        Root<Machine> root = query.from(Machine.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Machine_.farmer), farmer)
                ));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Machine> findAllByCategory(Category category) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Machine> query = cb.createQuery(Machine.class);
        Root<Machine> root = query.from(Machine.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Machine_.category), category)
                ));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Machine> findAllByCategoryAndFarmer(Category category, Farmer farmer) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Machine> query = cb.createQuery(Machine.class);
        Root<Machine> root = query.from(Machine.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Machine_.category), category),
                        cb.equal(root.get(Machine_.farmer), farmer)
                ));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Machine> find(UUID id) {
        return Optional.ofNullable(em.find(Machine.class, id));
    }

    @Override
    public Optional<Machine> findByIdAndFarmer(UUID machineId, Farmer farmer) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Machine> query = cb.createQuery(Machine.class);
            Root<Machine> root = query.from(Machine.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Machine_.id), machineId),
                            cb.equal(root.get(Machine_.farmer), farmer)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void create(Machine entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.persist(entity);
    }

    @Override
    public void update(Machine entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.merge(entity);
    }

    @Override
    public void delete(Machine entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.remove(em.find(Machine.class, entity.getId()));
    }

    @Override
    public void detach(Machine entity) {
        em.detach(entity);
    }
}
