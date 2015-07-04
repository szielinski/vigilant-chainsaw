package com.ericsson.msc.failuremanagement.failureslog.basedata.business;

import java.util.Collection;
import javax.ejb.Local;

import com.ericsson.msc.failuremanagement.failureslog.basedata.data.FailureClassEntity;

/**
 * FailureClass service EJB interface.
 */
@Local
public interface FailureClassData {

	/**
	 * Retrieve a collection of all stored FailureClasses.
	 * 
	 * @return A collection of all FailureClass objects stored by the system.
	 */
	public Collection <FailureClassEntity> getFailureClasses();

	/**
	 * Batch store a collection of FailureClasses. Optimized to handle large
	 * volumes of data efficiently.
	 * 
	 * @param failureClasses
	 *            A collection of FailureClass objects to store.
	 */
	public void addFailureClasses(Collection <FailureClassEntity> failureClasses);

	public void addFailureClass(FailureClassEntity testFailureClass);
}
