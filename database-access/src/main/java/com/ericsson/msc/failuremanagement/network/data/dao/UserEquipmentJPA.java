package com.ericsson.msc.failuremanagement.network.data.dao;

import com.ericsson.msc.failuremanagement.network.data.UserEquipmentEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Local
@Stateless
public class UserEquipmentJPA {

    @PersistenceContext
    private EntityManager em;

    public Collection<UserEquipmentEntity> getAllUserEquipment() {
        return em.createNamedQuery("findAllUserEquipment").getResultList();
    }

    public UserEquipmentEntity getUserEquipment(int typeAllocationCode) {
        return em.find(UserEquipmentEntity.class, typeAllocationCode);
    }

    public void insertUserEquipment(UserEquipmentEntity userEquipment) {
        em.persist(userEquipment);
    }

    public void batchInsertUserEquipment(Collection<UserEquipmentEntity> userEquipmentList) {
        for (UserEquipmentEntity userEquipment : userEquipmentList)
            em.persist(userEquipment);
    }
}
