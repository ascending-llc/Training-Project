package com.ascending.training.controller;

import com.ascending.training.service.FileService;
import com.ascending.training.service.MessageService;
import com.ascending.training.service.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/files"})
public class FileController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String queueName = "training_queue_ascending_com";
    private static final String fileDownloadDir = "/Users/liweiwang/ascending/lecture";

    @Autowired
    private FileService fileService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private StorageService storageService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        String msg = String.format("The file name=%s, size=%d could not be uploaded.", file.getOriginalFilename(), file.getSize());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String homeDir = System.getProperty("catalina.base") !=null ? System.getProperty("catalina.base") : "/tmp/";
        String s3Key = FilenameUtils.getBaseName(file.getOriginalFilename()) + "_"+UUID.randomUUID()+"."+extension;

//        try {
        File f = new File(homeDir+s3Key);
        try {
            file.transferTo(f);
            storageService.uploadObject(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

//            if (url != null) {
//                msg = String.format("The file name=%s, size=%d was uploaded, url=%s", file.getOriginalFilename(), file.getSize(), url);
//                messageService.sendMessage(url,5);
//            }
//        }
//        catch (Exception e) {
//            logger.error(e.getMessage());
//            return ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).body(e.getMessage());
//        }
//
//        logger.info(msg);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);
    }

    @RequestMapping(value = "/{bucketName}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@PathVariable String bucketName, @RequestParam("file") MultipartFile file) {
        String msg = String.format("The file name=%s, size=%d could not be uploaded.", file.getOriginalFilename(), file.getSize());

        try {
            String url = fileService.uploadFile(bucketName, file);
            if (url != null) {
                msg = String.format("The file name=%s, size=%d was uploaded, url=%s", file.getOriginalFilename(), file.getSize(), url);
                messageService.sendMessage(url,5);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).body(e.getMessage());
        }

        logger.info(msg);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);
    }

    @RequestMapping(value = "/{fileName}", method = RequestMethod.GET, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = null;
        String msg = "The file doesn't exist.";

        try {
            Path filePath = Paths.get(fileDownloadDir).toAbsolutePath().resolve(fileName).normalize();
            resource = new UrlResource(filePath.toUri());
            if(!resource.exists()) return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(msg);
        }
        catch (MalformedURLException ex) {
            logger.debug(ex.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        //Send message to SQS
        messageService.sendMessage(String.format("The file %s was downloaded", resource.getFilename()),5);

        return ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
               .body(resource);
    }
}
