package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.UserEquipment;

/**
 * UserEquipment service EJB interface.
 */
@Local
public interface UserEquipmentData {

	public Collection <UserEquipment> getAllUserEquipments();

	public void addUserEquipments(Collection <UserEquipment> userEquipments);

	public void addUserEquipment(UserEquipment userEquipment);

}
