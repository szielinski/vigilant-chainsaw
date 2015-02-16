package com.ericsson.msc.group5.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="UE_Type")
public class UEType {
	@Id
	@Column(name="ue_type_id")
	private Integer ueTypeId; 
	@Column(name="ue_type", length=45)
	private String ueType;
	
	public UEType() {
	}
	
	public UEType(Integer ueTypeId, String ueType){
		this.ueTypeId = ueTypeId;
		this.ueType = ueType;
	}

	public Integer getUeTypeId() {
		return ueTypeId;
	}

	public void setUeTypeId(Integer ueTypeId) {
		this.ueTypeId = ueTypeId;
	}

	public String getUeType() {
		return ueType;
	}

	public void setUeType(String ueType) {
		this.ueType = ueType;
	}
}