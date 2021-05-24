package com.ua.nure.server.model.repository;

import com.ua.nure.server.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("from Message m where m.member.room.id = :room_id order by m.date")
    List<Message> getMessagesByRoomId(@Param("room_id") long id);

    @Query("from Message m where m.member.id = :member_id")
    List<Message> getMessagesByMemberId(@Param("member_id") long id);

    @Query("from Message m ,User u where m.member.user.id = :room_id and m.member.room.id = :room_id " +
            "and m.content like concat('%',:content, '%')")
    List<Message> getMessagesByContentPartAndRoomId(@Param("content") String content, @Param("room_id") long id);

    @Modifying
    @Transactional
    @Query("update Message set content = :new_content where id = :message_id")
    void editMessage(@Param("message_id") long messageId, @Param("new_content") String newContent);
}
