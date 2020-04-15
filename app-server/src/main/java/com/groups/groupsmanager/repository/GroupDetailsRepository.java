package com.groups.groupsmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.groups.groupsmanager.model.GroupMembers;

@Repository
public interface GroupDetailsRepository extends JpaRepository<GroupMembers, Long> {

	@Query("select gd from GroupMembers gd where gd.groupId=:groupId and gd.memberId=:memberId")
	Optional<GroupMembers> findByGroupIdAndMemberId(Long groupId, Long memberId);

	Optional<List<GroupMembers>> findByMemberId(Long id);

	Optional<List<GroupMembers>> findByGroupId(Long id);

}
