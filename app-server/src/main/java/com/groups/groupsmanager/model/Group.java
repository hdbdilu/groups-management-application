package com.groups.groupsmanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "groupentity", uniqueConstraints = { @UniqueConstraint(columnNames = "groupName") })
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String groupName;
	@Column(nullable = false)
	private Long adminId;
	@Column(nullable = false)
	private String adminName;
	@Column(nullable = false)
	private Date creationTime = new Date();

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getId() {
		return id;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Group(Long id, String groupName, Long adminId, String adminName, Date creationTime) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.adminId = adminId;
		this.adminName = adminName;
		this.creationTime = creationTime;
	}

	public Group() {

	}

}
