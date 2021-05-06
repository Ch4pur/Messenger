package com.ua.nure.model.repository;

import com.ua.nure.model.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    @Query("from RoomType r inner join Member m on (r=m.role) where m.user.id = :user_id and m.room.id = :room_id")
    RoomType getRoomTypeByUserIdAndRoomId(@Param("user_id") long userId, @Param("room_id") long roomId);
}
