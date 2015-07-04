package com.ericsson.msc.failuremanagement.failureslog.basedata.data;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Event Cause JPA entity. Uses an embedded natural composite key made up of a
 * Cause Code and Event identifiers to map to a textual description of the
 * cause.
 */
@Entity
@Table(name = "event_cause")
@NamedQueries({@NamedQuery(name = "findAllEventCauses", query = "SELECT e FROM EventCauseEntity e")})
public class EventCauseEntity {

	@Id
	@Column(name = "cause_code_event_id")
	private EventCauseEntityCK causeCodeEventIdCK;

	@Column(length = 100)
	private String description;

	@OneToMany(mappedBy = "eventCause", targetEntity = FailureTraceEntity.class)
	private Collection <FailureTraceEntity> failureTrace;

	/**
	 * No-args constructor used by the JPA.
	 */
	public EventCauseEntity() {
	}

	/**
	 * Create a Cause Code/Event combination given a composite key and String
	 * description.
	 * 
	 * @param causeCodeEventIdCK
	 *            Composite Cause Code/Event key.
	 * @param description
	 *            Textual description of the event.
	 */
	public EventCauseEntity(EventCauseEntityCK causeCodeEventIdCK, String description) {
		this.causeCodeEventIdCK = causeCodeEventIdCK;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EventCauseEntityCK getCauseCodeEventIdCK() {
		return causeCodeEventIdCK;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((causeCodeEventIdCK == null) ? 0 : causeCodeEventIdCK.hashCode());
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
		EventCauseEntity other = (EventCauseEntity) obj;
		if (causeCodeEventIdCK == null) {
			if (other.causeCodeEventIdCK != null)
				return false;
		}
		else if ( !causeCodeEventIdCK.equals(other.causeCodeEventIdCK))
			return false;
		return true;
	}
}
