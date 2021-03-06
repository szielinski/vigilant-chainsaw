package com.ericsson.msc.failuremanagement.network.data;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.Index;

/**
 * Failure Trace JPA entity. The main entity in the application, maps to the
 * Base Data table.
 */

@Entity
@Table(name = "failure_trace")
@NamedQueries({
// Queries for dropdown population
		@NamedQuery(name = "getAllIMSIs", query = "SELECT DISTINCT (f.IMSI) FROM FailureTraceEntity f"),
		@NamedQuery(name = "getAllModels", query = "SELECT DISTINCT (u.model) FROM UserEquipmentEntity u"),
		// Other queries
		@NamedQuery(name = "getAllFailureTraces", query = "SELECT f FROM FailureTraceEntity f"),
		@NamedQuery(name = "getCauseCodesForGivenImsi", query = "SELECT DISTINCT (f.eventCause.causeCodeEventIdCK.causeCode) as causeCode FROM FailureTraceEntity f WHERE f.IMSI = :givenImsi ORDER BY causeCode ASC "),
		@NamedQuery(name = "getTotalNumberOfEntries", query = "SELECT count(f.failureTraceId) from FailureTraceEntity f"),
		@NamedQuery(name = "getEventCauseCombinations", query = "SELECT DISTINCT (f.eventCause.description) FROM FailureTraceEntity f WHERE f.IMSI = :givenImsi"),
		@NamedQuery(name = "getImsiOfFailureTraceByFailureClass", query = "SELECT DISTINCT (f.IMSI) FROM FailureTraceEntity f where f.failureClass.failureClass = :givenFailureClass ORDER BY f.IMSI ASC"),
		@NamedQuery(name = "getAllFailureClasses", query = "SELECT f FROM FailureClassEntity f"),
		@NamedQuery(name = "getEventCauseCombinationsForModel", query = "SELECT COUNT (f.eventCause.description) AS amount, f.eventCause.description FROM FailureTraceEntity f WHERE f.userEquipment.model = :model GROUP BY f.eventCause.description"),
		@NamedQuery(name = "getImsiOfFailureByTimePeriod", query = "SELECT DISTINCT(f.IMSI) FROM FailureTraceEntity f WHERE f.dateTime BETWEEN :startTime AND :endTime "),
		@NamedQuery(name = "getCountAndTotalDurationForGivenImsiWithinTimePeriod", query = "SELECT COUNT(f.dateTime), SUM(f.duration) FROM FailureTraceEntity f WHERE f.IMSI = :Imsi AND f.dateTime BETWEEN :startTime AND :endTime GROUP BY f.IMSI "),
		@NamedQuery(name = "getCountOfFailuresForGivenImsiWithinTimePeriod", query = "SELECT COUNT(f.dateTime) FROM FailureTraceEntity f WHERE f.IMSI = :Imsi AND f.dateTime BETWEEN :startTime AND :endTime GROUP BY f.IMSI"),
		@NamedQuery(name = "topTenIMSIsWithFailures", query = "SELECT COUNT(f.IMSI) as total, f.IMSI FROM FailureTraceEntity f WHERE f.dateTime BETWEEN :startTime AND :endTime GROUP BY f.IMSI ORDER by total DESC"),
		@NamedQuery(name = "getCountOfFailuresWithinTimePeriodForGivenModel", query = "SELECT COUNT(f.IMSI) FROM FailureTraceEntity f WHERE f.userEquipment.model = :model AND f.dateTime BETWEEN :startTime AND :endTime"),
		@NamedQuery(name = "top10MarketOperatorCellIdCombinations", query = "SELECT COUNT(f.IMSI) as total, f.cellId, f.countryCodeNetworkCode.operator, f.countryCodeNetworkCode.countryCodeNetworkCode.country.country FROM FailureTraceEntity f WHERE f.dateTime BETWEEN :startTime AND :endTime GROUP BY f.IMSI ORDER BY total DESC")})
public class FailureTraceEntity {

	@Id
	@Column(name = "failure_trace_id")
	private Long failureTraceId;
	@Column(name = "date_time")
	@Index(name = "imsiAndDateIndex")
	private Date dateTime;
	@Column(name = "cell_id")
	private Integer cellId;
	private Integer duration;
	@Column(name = "ne_version", length = 3)
	private String neVersion;
	@Column(name = "imsi", length = 20)
	@Index(name = "imsiAndDateIndex")
	private String IMSI;
	@Column(name = "hier3_id", length = 20)
	private String hier3Id;
	@Column(name = "hier32_id", length = 20)
	private String hier32Id;
	@Column(name = "hier321_id", length = 20)
	private String hier321Id;

	@ManyToOne
	@JoinColumn(name = "failure_class")
	private FailureClassEntity failureClass;
	@ManyToOne
	@JoinColumn(name = "typeAllocationCode")
	private UserEquipmentEntity userEquipment;
	@ManyToOne
	@JoinColumns({@JoinColumn(name = "cause_code", referencedColumnName = "cause_code"), @JoinColumn(name = "event_id", referencedColumnName = "event_id")})
	private EventCauseEntity eventCause;
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "country_code", referencedColumnName = "country_code"),
			@JoinColumn(name = "network_code", referencedColumnName = "network_code")})
	private CountryCodeNetworkCodeEntity countryCodeNetworkCode;

	/**
	 * No-args constructor used by the JPA.
	 */
	public FailureTraceEntity() {
	}

	public Long getFailureTraceId() {
		return failureTraceId;
	}

	public void setFailureTraceId(Long failureTraceId) {
		this.failureTraceId = failureTraceId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getCellId() {
		return cellId;
	}

	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getNeVersion() {
		return neVersion;
	}

	public void setNeVersion(String neVersion) {
		this.neVersion = neVersion;
	}

	public String getIMSI() {
		return IMSI;
	}

	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}

	public String getHier3Id() {
		return hier3Id;
	}

	public void setHier3Id(String hier3Id) {
		this.hier3Id = hier3Id;
	}

	public String getHier32Id() {
		return hier32Id;
	}

	public void setHier32Id(String hier32Id) {
		this.hier32Id = hier32Id;
	}

	public String getHier321Id() {
		return hier321Id;
	}

	public void setHier321Id(String hier321Id) {
		this.hier321Id = hier321Id;
	}

	public FailureClassEntity getFailureClass() {
		return failureClass;
	}

	public void setFailureClass(FailureClassEntity failureClass) {
		this.failureClass = failureClass;
	}

	public UserEquipmentEntity getUserEquipment() {
		return userEquipment;
	}

	public void setUserEquipment(UserEquipmentEntity userEquipment) {
		this.userEquipment = userEquipment;
	}

	public EventCauseEntity getEventCause() {
		return eventCause;
	}

	public void setEventCause(EventCauseEntity eventCause) {
		this.eventCause = eventCause;
	}

	public CountryCodeNetworkCodeEntity getCountryCodeNetworkCode() {
		return countryCodeNetworkCode;
	}

	public void setCountryCodeNetworkCode(CountryCodeNetworkCodeEntity countryCodeNetworkCode) {
		this.countryCodeNetworkCode = countryCodeNetworkCode;
	}
}
