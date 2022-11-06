package com.petmily.petmily.service;

import com.petmily.petmily.domain.Image;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import com.petmily.petmily.dto.image.ImageSaveDto;
import com.petmily.petmily.repository.IImageRepository;
import com.petmily.petmily.repository.IMemberRepository;
import com.petmily.petmily.security.JwtTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final IImageRepository imageRepository;
    private final IMemberRepository memberRepository;
    @Value("${file.dir}") private String path;

    @Transactional
    public void save(List<MultipartFile> multipartFiles, HttpServletRequest request, Post post) throws IOException {
        Member findMember = findMember(request);
        for (MultipartFile multipartFile : multipartFiles) {
            String storedName = storedName(multipartFile);
            Image image = Image.getImage(new ImageSaveDto(findMember, post, uploadedName(multipartFile), storedName));
            imageRepository.save(image);
            multipartFile.transferTo(new File(getFullPath(storedName)));
        }
    }

    public List<Image> findByPost(Post post) {
        return imageRepository.findByPost(post);
    }

    public List<byte[]> getListByteArray(List<Image> imageList) throws IOException {
        List<byte[]> resourceList = new ArrayList<>();
        for (Image image : imageList) {
            File file = new File(getFullPath(image.getStoredName()));
            byte[] array = Files.readAllBytes(file.toPath());
            resourceList.add(array);
        }
        return resourceList;
    }

    private Member findMember(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        List<Member> findMembers = memberRepository.findByNickname(nickname);
        if (findMembers.size() != 1) {
            throw new JwtTokenException("유효하지 않은 토큰입니다.");
        }
        return findMembers.get(0);
    }

    private String uploadedName(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename();
    }

    private String storedName(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        log.info("storedName: {}", uuid + LocalDateTime.now().toString() + "." + ext);
        return uuid + "&" + LocalDateTime.now().toString() + "." + ext;

    }

    private String extractExt(String originalFilename) {
        int position = originalFilename.lastIndexOf(".");
        return originalFilename.substring(position + 1);
    }

    private String getFullPath(String fileName) {
        return path + fileName;
    }

}
