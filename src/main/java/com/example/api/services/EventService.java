package com.example.api.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.example.api.domain.event.Event;
import com.example.api.domain.event.EventRequestDTO;
import com.example.api.respositories.EventRepositories;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private EventRepositories eventRepositories;

    public Event createEvent(EventRequestDTO data){

        String imageUrl = null;
        if(data.image() != null){
           imageUrl = this.uploadImage(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(new Date(data.date()));
        newEvent.setImgUrl(imageUrl);
        newEvent.setRemote(data.remote());

        eventRepositories.save(newEvent);
        return newEvent;
        
    }

    private String uploadImage(MultipartFile multipartFile){
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        try{
            File file = this.convertToFile(multipartFile);
            s3Client.putObject(bucketName,fileName, file);
            file.delete();
            return s3Client.getUrl(bucketName, fileName).toString();
        }catch(Exception e){
            System.out.println("Error uploading.");
            return "";
        }
    }

    private File convertToFile(MultipartFile multipartFile) throws IOException{
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
        
    }
    
}
