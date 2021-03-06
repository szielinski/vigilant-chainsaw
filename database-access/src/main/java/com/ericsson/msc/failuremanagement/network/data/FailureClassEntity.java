package com.ericsson.msc.failuremanagement.network.data;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Failure Class JPA entity. Matches an integer id of a failure to a string description.
 */
@Entity
@Table(name = "failure_class")
@NamedQueries({@NamedQuery(name = "findAllFailureClasses", query = "SELECT f FROM FailureClassEntity f")})
public class FailureClassEntity {

	@Id
	@Column(name = "failure_class")
	private Integer failureClass;
	@Column(length = 100)
	private String description;

	@OneToMany(mappedBy = "failureClass", targetEntity = FailureTraceEntity.class)
	private Collection <FailureTraceEntity> failureTrace;

	/**
	 * No-args constructor used by the JPA.
	 */
	public FailureClassEntity() {
	}

	/**
	 * Create a failure class with the given id and description.
	 * 
	 * @param failureClass
	 *            Integer id of the failure class.
	 * @param description
	 *            String description of the failure class.
	 */
	public FailureClassEntity(Integer failureClass, String description) {
		this.failureClass = failureClass;
		this.description = description;
	}

	/**
	 * Returns the unique integer id of the failure class.
	 * 
	 * @return Integer id of the failure class.
	 */
	public Integer getFailureClass() {
		return failureClass;
	}

	/**
	 * Set the integer id of the failure class.
	 * 
	 * @param failureClass
	 *            Unique integer id.
	 */
	public void setFailureClass(Integer failureClass) {
		this.failureClass = failureClass;
	}

	/**
	 * Get the String description of the failure class.
	 * 
	 * @return String description of the failure class.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the String description of the failure class.
	 * 
	 * @param description
	 *            String description of the failure class.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((failureClass == null) ? 0 : failureClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FailureClassEntity other = (FailureClassEntity) obj;
		if (failureClass == null) {
			if (other.failureClass != null)
				return false;
		}
		else if ( !failureClass.equals(other.failureClass))
			return false;
		return true;
	}
}
