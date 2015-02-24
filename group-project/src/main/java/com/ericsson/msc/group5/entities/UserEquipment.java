package com.ericsson.msc.group5.entities;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "user_equipment")
public class UserEquipment {

	@Id
	@Column(name = "type_allocation_code")
	private Integer typeAllocationCode;
	@Column(name = "marketing_name", length = 60)
	private String marketingName;
	@Column(name = "manufacturer", length = 60)
	private String manufacturer;
	@Column(name = "model", length = 70)
	private String model;
	@ManyToOne
	@JoinColumn(name = "access_capability_id")
	private AccessCapability accessCapabilityClass;
	@ManyToOne
	@JoinColumn(name = "user_equipment_type_id")
	private UserEquipmentType UserEquipmentType;
	@ManyToOne
	@JoinColumn(name = "operating_system_id")
	private OperatingSystem operatingSystem;
	@ManyToOne
	@JoinColumn(name = "input_mode")
	private InputMode inputModeClass;

	@OneToMany(mappedBy = "userEqipment")
	private Collection <FailureTrace> failureTrace;

	public UserEquipment() {

	}

	public UserEquipment(Integer typeAllocationCode, String marketingName,
			String manufacturer, AccessCapability accessCapabilityClass,
			String model, UserEquipmentType userEquipmentType,
			OperatingSystem operatingSystem, InputMode inputModeClass) {
		super();
		this.typeAllocationCode = typeAllocationCode;
		this.marketingName = marketingName;
		this.manufacturer = manufacturer;
		this.accessCapabilityClass = accessCapabilityClass;
		this.model = model;
		this.UserEquipmentType = userEquipmentType;
		this.operatingSystem = operatingSystem;
		this.inputModeClass = inputModeClass;
	}

	public Integer getTypeAllocationCode() {
		return typeAllocationCode;
	}

	public void setTypeAllocationCode(Integer typeAllocationCode) {
		this.typeAllocationCode = typeAllocationCode;
	}

	public String getMarketingName() {
		return marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public UserEquipmentType getUserEquipmentType() {
		return UserEquipmentType;
	}

	public void setUserEquipmentType(UserEquipmentType userEquipmentType) {
		this.UserEquipmentType = userEquipmentType;
	}

	public AccessCapability getAccessCapabilityClass() {
		return accessCapabilityClass;
	}

	public void setAccessCapabilityClass(AccessCapability accessCapabilityClass) {
		this.accessCapabilityClass = accessCapabilityClass;
	}

	public OperatingSystem getOperatingSystem() {
		return operatingSystem;
	}

	public void setoS(OperatingSystem operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public InputMode getInputModeClass() {
		return inputModeClass;
	}

	public void setInputModeClass(InputMode inputModeClass) {
		this.inputModeClass = inputModeClass;
	}
}
