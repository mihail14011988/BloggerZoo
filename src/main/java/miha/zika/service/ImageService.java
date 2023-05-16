/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import miha.zika.entity.UserBlogger;
import miha.zika.repo.CommentRepo;
import miha.zika.repo.ImageRepo;
import miha.zika.repo.PostRepo;
import miha.zika.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
