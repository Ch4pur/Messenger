package com.ua.nure.model.service.impl;

import com.ua.nure.exception.ServiceException;
import com.ua.nure.model.entity.Member;
import com.ua.nure.model.entity.Room;
import com.ua.nure.model.repository.MemberRepository;
import com.ua.nure.model.repository.RoomRepository;
import com.ua.nure.model.repository.UserRepository;
import com.ua.nure.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, UserRepository userRepository,
                             RoomRepository roomRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void addMember(@NotNull Member member) throws ServiceException {
        if (!userRepository.existsById(member.getUser().getId())) {
            throw new ServiceException("Specified user doesn`t exist");
        }
        if (!roomRepository.existsById(member.getRoom().getId())) {
            throw new ServiceException("Specified room doesn`t exist");
        }
        if (memberRepository.existsById(member.getId())) {
            throw new ServiceException("Specified member already exist");
        }

        Room room = member.getRoom();
        int maxCountOfRoomMembers = room.getMaxQuantityOfMembers();
        int countOfRoomMembers = roomRepository.getCountOfRoomMembers(room.getId());
        if (maxCountOfRoomMembers < countOfRoomMembers + 1) {
            throw new ServiceException("Room is full");
        }

        memberRepository.save(member);
    }

    @Override
    public void updateMemberRestrictions(Member member) throws ServiceException {
        if (!memberRepository.existsById(member.getId())) {
            throw new ServiceException("Specified member doesn't exist");
        }
        memberRepository.save(member);
    }


    @Override
    public void removeMemberById(long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Override
    public Member getMemberByRoomIdAndUserId(long roomId, long userId) throws ServiceException {
        if (!roomRepository.existsById(roomId)) {
            throw new ServiceException("Specified room doesn't exist");
        }
        if (!userRepository.existsById(userId)) {
            throw new ServiceException("Specified user doesn't exist");
        }
        return memberRepository.getMemberByRoomIdAndUserId(roomId, userId);
    }

    @Override
    public List<Member> getMembersByRoomId(long roomId) throws ServiceException {
        if (!roomRepository.existsById(roomId)) {
            throw new ServiceException("Specified room doesn't exist");
        }
        return memberRepository.findMembersByRoomId(roomId);
    }
}
