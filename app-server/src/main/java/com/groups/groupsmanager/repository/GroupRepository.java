package com.groups.groupsmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.groups.groupsmanager.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	Optional<Group> findByGroupName(String groupName);

	Boolean existsByGroupName(String groupName);

	Optional<List<Group>> findByAdminId(Long id);

	Boolean existsByAdminName(String adminName);

	@Query("select g from Group g where g.groupName=:groupName and g.adminId=:id")
	Optional<Group> findByGroupNameAndAdminId(String groupName, Long id);

}
