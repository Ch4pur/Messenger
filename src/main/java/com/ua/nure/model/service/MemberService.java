package com.ua.nure.model.service;

import com.ua.nure.model.entity.Member;

import java.util.List;

public interface MemberService {

    void addMember(Member member);

    void updateMembersRole(long roleId, long memberId);

    void removeMemberById(long member);

    List<Member> getMemberByRoomId(long roomId);

    List<Member> getMembersByRoomIdAndRoleId(long roomId, long roleId);
}
