package com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.jpa;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.UserEquipmentEntity;
import com.ericsson.msc.failuremanagement.failureslog.basedata.data.dao.UserEquipmentDAO;

public class UserEquipmentJPA implements UserEquipmentDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection <UserEquipmentEntity> getAllUserEquipment() {
		return em.createNamedQuery("findAllUserEquipment").getResultList();
	}

	@Override
	public UserEquipmentEntity getUserEquipment(int typeAllocationCode) {
		return em.find(UserEquipmentEntity.class, typeAllocationCode);
	}

	@Override
	public void insertUserEquipment(UserEquipmentEntity userEquipment) {
		em.persist(userEquipment);
	}

	@Override
	public void batchInsertUserEquipment(Collection <UserEquipmentEntity> userEquipmentList) {
		for (UserEquipmentEntity userEquipment : userEquipmentList)
			em.persist(userEquipment);
	}
}
