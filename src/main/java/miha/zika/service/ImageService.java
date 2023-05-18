/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import miha.zika.entity.ImageComm;
import miha.zika.entity.UserBlogger;
import miha.zika.entity.UserPost;
import miha.zika.mainException.ImageNotFoundForPost;
import miha.zika.repo.CommentRepo;
import miha.zika.repo.ImageRepo;
import miha.zika.repo.PostRepo;
import miha.zika.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    public final static Logger LOG = LoggerFactory.getLogger(ImageService.class);
    private final ImageRepo imageRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Autowired
    public ImageService(ImageRepo imageRepo, PostRepo postRepo, UserRepo userRepo) {
        this.imageRepo = imageRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public ImageComm uploadImageToUser(MultipartFile file, Principal principal) throws IOException {
        UserBlogger blogger = getUserByPrincipal(principal);
        LOG.info("Uplouding file for user " + blogger.getName());
        ImageComm image = imageRepo.findByUserId(blogger.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(image)) {
            imageRepo.delete(image);
        }
        ImageComm imageN = new ImageComm();
        imageN.setUserId(blogger.getId());
        imageN.setImagesBytes(compressImage(file.getBytes()));
        imageN.setImageName(file.getOriginalFilename());
        return imageRepo.save(imageN);
    }

    public ImageComm uploadImageToPost(MultipartFile file, Principal principal, Long postId) throws IOException {
        UserBlogger blogger = getUserByPrincipal(principal);
        UserPost post = blogger.getUserPost().stream().filter(p -> p.getId().equals(postId)).collect(toSinglePostCollector());
        ImageComm image = new ImageComm();
        image.setPostId(post.getId());
        image.setImagesBytes(compressImage(file.getBytes()));
        image.setImageName(file.getOriginalFilename());
        LOG.info("Uploading image to Post {}", post.getId());
        return imageRepo.save(image);
    }

    public ImageComm getImageForUser(Principal principal) {
        UserBlogger blogger = getUserByPrincipal(principal);
        ImageComm image = imageRepo.findByUserId(blogger.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(image)) {
            image.setImagesBytes(decompressImage(image.getImagesBytes()));
        }
        return image;
    }

    public ImageComm getImageToPost(Long postId){
    ImageComm image = imageRepo.findByPostId(postId).orElseThrow(()-> new ImageNotFoundForPost("Image for Post not found")); 
    if(!ObjectUtils.isEmpty(image)){
    image.setImagesBytes(decompressImage(image.getImagesBytes()));
    }
    return image;
    }
    //Компрессия фотографий
    private byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buf = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buf);
            outputStream.write(buf, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException ex) {
            LOG.error("Cannot compress bytes");
        }
        return outputStream.toByteArray();
    }

    private static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buf = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buf);
                outputStream.write(buf, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ex) {
            LOG.error("Cannot compress bytes");
        }

        return outputStream.toByteArray();
    }

    private UserBlogger getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepo.findUserBloggerByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }

    private <T> Collector<T, ?, T> toSinglePostCollector() {
        return Collectors.collectingAndThen(Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                });
    }
}
