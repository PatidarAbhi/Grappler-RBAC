package com.grapplermodule1.GrapplerEnhancement.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class TicketsDTO {
    private Long id;

    @NotEmpty(message = "Ticket name is required")
    private String name;

    private LocalDate endDate;

    private LocalDate createdDate;

    private String creatorEmail;

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    private List<Long> assigneeIds;

    public List<Long> getAssigneeIds() {
        return assigneeIds;
    }

    public void setAssigneeIds(List<Long> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    private String status;


    private String priority;
    @NotNull(message = "Project id is required")
    private Long projectId;

    private String projectName;

    private String creatorName;

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public TicketsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public TicketsDTO(Long id, String name, Long projectId, String projectName, String creatorName, LocalDate endDate, String status, String priority, LocalDate createDate) {
        this.id = id;
        this.name = name;
        this.projectId=projectId;
        this.projectName = projectName;
        this.creatorName = creatorName;
        this.endDate=endDate;
        this.status=status;
        this.priority=priority;
        this.createdDate=createDate;
    }

    @Override
    public String toString() {
        return "TicketsDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", endDate=" + endDate +
                ", createdDate=" + createdDate +
                ", creatorEmail='" + creatorEmail + '\'' +
                ", assigneeIds=" + assigneeIds +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", creatorName='" + creatorName + '\'' +
                '}';
    }
}

