package com.ua.nure.model.repository;

import com.ua.nure.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select case when count(r) > 0 then true else false end from Role r where r = :role")
    boolean isRoleExist(@Param("role") Role role);

    @Query("from Role r inner join Member m on (r=m.role) and m.id=:member_id")
    Role getRoleByMemberId(@Param("member_id") long id);


    @Query("from Role r inner join Member m on (r=m.role) and m.user.id=:user_id and m.room.id = :room_id")
    Role getRoleByUserIdAndRoomId(@Param("user_id") long userId, @Param("room_id") long roomId);
}
