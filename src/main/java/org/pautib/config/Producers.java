package org.pautib.config;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Producers {

    @Produces
    @PersistenceContext(unitName = "pu")
    private EntityManager entityManager;

}
