package com.toripizi.farmhub.farmer.repository;

import com.toripizi.farmhub.farmer.entity.Farmer;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class FarmerPersistenceRepository implements FarmerRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Farmer> findAll() {
        return em.createQuery("select f from Farmer f", Farmer.class).getResultList();
    }

    @Override
    public Optional<Farmer> find(UUID id) {
        return Optional.ofNullable(em.find(Farmer.class, id));
    }

    @Override
    public Optional<Farmer> findByLogin(String login) {
        try {
            return Optional.of(em.createQuery("select f from Farmer f where f.login = :login", Farmer.class)
                    .setParameter("login", login)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void create(Farmer entity) {
        em.persist(entity);
    }

    @Override
    public void update(Farmer entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Farmer entity) {
        /* Clearing cache used as workaround when not handling both sides of relationships, not recommended. */
        em.getEntityManagerFactory().getCache().evictAll(); //Clearing 2nd level cache.
        em.clear(); //Clearing 1st level cache.
        em.remove(em.find(Farmer.class, entity.getId()));
    }

    @Override
    public byte[] findAvatar(UUID id) {
        return new byte[0];
    }

    @Override
    public void updateAvatar(UUID id, InputStream is) {

    }

    @Override
    public void deleteAvatar(UUID id) {

    }
}
