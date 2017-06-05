package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.UserEquipmentEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa.UserEquipmentJPA;

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
