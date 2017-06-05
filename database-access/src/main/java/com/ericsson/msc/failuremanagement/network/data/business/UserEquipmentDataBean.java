package com.ericsson.msc.failuremanagement.network.data.business;

import com.ericsson.msc.failuremanagement.network.data.UserEquipmentEntity;
import com.ericsson.msc.failuremanagement.network.data.dao.UserEquipmentJPA;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Stateless
@Local
public class UserEquipmentDataBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserEquipmentJPA dao;

    public Collection<UserEquipmentEntity> getAllUserEquipments() {
        return dao.getAllUserEquipment();
    }

    public void addUserEquipments(Collection<UserEquipmentEntity> userEquipments) {
        dao.batchInsertUserEquipment(userEquipments);
    }

    public void addUserEquipment(UserEquipmentEntity userEquipment) {
        dao.insertUserEquipment(userEquipment);
    }

}
