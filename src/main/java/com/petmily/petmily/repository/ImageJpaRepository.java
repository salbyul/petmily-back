package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Image;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageJpaRepository implements IImageRepository{

    private final EntityManager em;


    @Override
    public void save(Image image) {
        em.persist(image);
    }

    @Override
    public List<Image> findByPost(Post post) {
        return em.createQuery("select i from Image i where i.post = :post")
                .setParameter("post", post)
                .getResultList();
    }

    /**
     * 과연 필요할까 고민!!
     * @param member
     * @return
     */
    @Override
    public List<Image> findByMember(Member member) {
        return null;
    }
}
