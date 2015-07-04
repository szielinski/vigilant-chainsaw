package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.UserEquipmentEntity;

/**
 * UserEquipment service EJB interface.
 */
@Local
public interface UserEquipmentData {

	public Collection <UserEquipmentEntity> getAllUserEquipments();

	public void addUserEquipments(Collection <UserEquipmentEntity> userEquipments);

	public void addUserEquipment(UserEquipmentEntity userEquipment);

}
