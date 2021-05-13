package com.ua.nure.model.service;

import com.ua.nure.exception.ServiceException;
import com.ua.nure.model.entity.Member;

import javax.transaction.Transactional;
import java.util.List;

public interface MemberService {

    void addMember(Member member) throws ServiceException;

    void updateMemberRestrictions(Member member) throws ServiceException;

    void removeMemberById(long member);

    Member getMemberByRoomIdAndUserId(long roomId, long userId) throws ServiceException;

    @Transactional
    List<Member> getMembersByRoomId(long roomId) throws ServiceException;
}
