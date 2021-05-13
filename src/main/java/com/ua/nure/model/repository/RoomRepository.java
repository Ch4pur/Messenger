package com.ua.nure.model.repository;

import com.ua.nure.model.entity.Room;
import com.ua.nure.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r inner join Member m on (r = m.room) where m.user.id = :user_id")
    List<Room> getRoomsByUserId(@Param("user_id") long userId);

    @Query("select r from Room r inner join Member m on (r = m.room) where m.user = :user")
    List<Room> getRoomsByUser(@Param("user") User user);

    @Query("select count(m) from Member m where m.room.id = :room_id")
    int getCountOfRoomMembers(@Param("room_id") long id);
}
