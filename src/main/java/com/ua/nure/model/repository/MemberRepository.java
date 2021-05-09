package com.ua.nure.model.repository;

import com.ua.nure.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("from Member where room.id = :room_id")
    List<Member> findMembersByRoomId(@Param("room_id") long id);

    @Query("from Member where room.id = :room_id and role.id = :role_id")
    List<Member> getMembersByRoomIdAndRoleId(@Param("room_id") long roomId, @Param("role_id") long roleId);

    @Modifying
    @Transactional
    @Query(value = "update room_members set role_id = :role_id where member_id = :member_id",
            nativeQuery = true)
    void setRoleToMember(@Param("role_id") long roleId, @Param("member_id") long memberId);
}
