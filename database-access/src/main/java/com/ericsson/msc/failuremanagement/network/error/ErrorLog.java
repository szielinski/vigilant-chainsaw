package com.ericsson.msc.failuremanagement.network.error;

import javax.persistence.*;

/**
 * Error Log JPA entity. Uses error_log_id as PK
 */
@Entity
@Table(name = "error_log")
@NamedQueries({@NamedQuery(name = "findAllErrorLogs", query = "SELECT e FROM ErrorLog e"),
        @NamedQuery(name = "getErrorsLogsByGenerationDate", query = "SELECT e FROM ErrorLog e WHERE e.generationTime = :generationDate"),
        @NamedQuery(name = "deleteAllErrorLogs", query = "DELETE FROM ErrorLog")})
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "error_log_id")
    private Integer errorLogId;
    @Column(name = "generation_time")
    private String generationTime;
    @Column(name = "error_description")
    private String errorDescription;
    @Column(name = "base_data", length = 350)
    private String baseData;

    /**
     * No-args constructor used by the JPA.
     */
    public ErrorLog() {
    }

    public ErrorLog(String generationTime, String errorDescription, String baseData) {
        this.generationTime = generationTime;
        this.errorDescription = errorDescription;
        this.baseData = baseData;
    }

    public String getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(String generationTime) {
        this.generationTime = generationTime;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getBaseData() {
        return baseData;
    }

    public void setBaseData(String baseData) {
        this.baseData = baseData;
    }

    public Integer getErrorLogId() {
        return errorLogId;
    }
}