package com.petmily.petmily.service;

import com.petmily.petmily.domain.Hashtag;
import com.petmily.petmily.domain.Post;
import com.petmily.petmily.repository.IHashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagService {

    private final IHashtagRepository hashtagRepository;

    @Transactional
    public void save(HttpServletRequest request, Post post) {
        String[] hashtags = request.getParameterValues("hashtag");
        if (hashtags != null && hashtags.length != 0) {
            for (String hashtagName : hashtags) {
                Hashtag hashtag = Hashtag.getHashtag(hashtagName, post);
                hashtagRepository.save(hashtag);
            }
        }
    }

    public List<Hashtag> findByPost(Post post) {
        return hashtagRepository.findByPost(post);
    }

    public List<Hashtag> findByHashtagName(String hashtagName) {
        return hashtagRepository.findByHashtagName(hashtagName);
    }

}
