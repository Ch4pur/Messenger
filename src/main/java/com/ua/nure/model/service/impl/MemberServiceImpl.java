package com.ua.nure.model.service.impl;

import com.ua.nure.model.entity.Member;
import com.ua.nure.model.entity.RoomType;
import com.ua.nure.model.repository.MemberRepository;
import com.ua.nure.model.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, UserRepository userRepository,
                             RoomRepository roomRepository, RoleRepository roleRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addMember(@NotNull Member member) {
        if (!(roleRepository.isRoleExist(member.getRole())
                && userRepository.existsById(member.getUser().getId())
                && roomRepository.existsById(member.getRoom().getId()))) {
            //TODO custom exception
        }
        if (memberRepository.existsById(member.getId())) {
            //TODO custom exception
        }

        RoomType roomType = member.getRoom().getRoomType();
        long roomId = member.getRoom().getId();

        int maxCountOfRoomMembers = roomType.getMaxQuantityOfMembers();
        int countOfRoomMembers = roomRepository.getCountOfRoomMembers(roomId);
        if (maxCountOfRoomMembers < countOfRoomMembers + 1) {
            //TODO custom exception
        }

        memberRepository.save(member);
    }

    @Override
    public void updateMembersRole(long roleId, long memberId) {
        if (!(roomRepository.existsById(roleId) && memberRepository.existsById(memberId))) {
            //TODO custom exception
        }
        memberRepository.setRoleToMember(roleId, memberId);
    }

    @Override
    public void removeMemberById(long memberId) {
        if (memberRepository.existsById(memberId)) {
            //TODO custom exception
        }
        memberRepository.deleteById(memberId);
    }

    @Override
    public List<Member> getMemberByRoomId(long roomId) {
        if (!roomRepository.existsById(roomId)) {
            //TODO custom exception
        }
        return memberRepository.findMembersByRoomId(roomId);
    }

    @Override
    public List<Member> getMembersByRoomIdAndRoleId(long roomId, long roleId) {
        if (!(roomRepository.existsById(roomId) && roleRepository.existsById(roleId))) {
            //TODO custom exception
        }

        return memberRepository.getMembersByRoomIdAndRoleId(roomId, roleId);
    }
}
