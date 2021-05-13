package com.ua.nure.model.repository;

import com.ua.nure.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("from Member m where m.room.id = :room_id and m.user.id = :user_id")
    Member getMemberByRoomIdAndUserId(@Param("room_id") long roomId, @Param("user_id") long userId);

    @Query("from Member m where m.room.id = :room_id")
    List<Member> findMembersByRoomId(@Param("room_id") long id);
}
