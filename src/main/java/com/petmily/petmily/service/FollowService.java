package com.petmily.petmily.service;

import com.petmily.petmily.domain.Follow;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.exception.follow.FollowException;
import com.petmily.petmily.repository.IFollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final IFollowRepository followRepository;

    @Transactional
    public void follow(Member member, Member targetMember) {
        followRepository.save(member, targetMember);
    }

    public List<Follow> findAll(Member member) {
        return followRepository.findAll(member);
    }

    @Transactional(rollbackFor = FollowException.class)
    public void unFollow(Member member, Member targetMember) {
        followRepository.remove(member, targetMember);
    }

    public boolean isFollow(Member member, Member targetMember) {
        List<Follow> allFollow = followRepository.findAll(member);
        for (Follow follow : allFollow) {
            if (follow.getTargetMember() == targetMember) {
                return true;
            }
        }
        return false;
    }
}
