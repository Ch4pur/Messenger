package com.ua.nure.server.model.service;

import com.ua.nure.server.exception.ServiceException;
import com.ua.nure.server.model.entity.Member;

import javax.transaction.Transactional;
import java.util.List;

public interface MemberService {
    //TODO implement this functional
    void addMember(Member member) throws ServiceException;
    //TODO implement this functional
    void updateMemberRestrictions(Member member) throws ServiceException;
    //TODO implement this functional
    void removeMemberById(long member);

    Member getMemberByRoomIdAndUserId(long roomId, long userId) throws ServiceException;

    @Transactional
    List<Member> getMembersByRoomId(long roomId) throws ServiceException;
}
