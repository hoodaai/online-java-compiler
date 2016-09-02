package com.web.rest;

import com.config.ApplicationProperties;
import com.service.JavaCompiler;
import com.service.UnzipUtility;
import com.web.rest.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * File uploader
 */
@RestController
@RequestMapping("/api")
public class UploadResource {
    private final Logger log = LoggerFactory.getLogger(UploadResource.class);

    @Inject
    private ApplicationProperties applicationProperties;

    @Inject
    private UnzipUtility unzipUtility;

    @Inject
    private JavaCompiler javaCompiler;

    @RequestMapping(value = "/upload",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {

        log.debug("REST request to upload file: {}", file.getOriginalFilename());

        if (!file.isEmpty()) {
            try {
                StringBuilder path = new StringBuilder().append(applicationProperties.getOutputdirectory())
                        .append("/")
                        .append(file.getOriginalFilename().split("\\.")[0])
                        .append("/");

                unzipUtility.unzip(file.getInputStream(), applicationProperties.getOutputdirectory());
                List<String> output = javaCompiler.compile(path.toString());

                return new ResponseEntity(output, HttpStatus.OK);

            } catch (IOException e) {
                log.error(e.getMessage(), e);
                Response response = new Response( HttpStatus.BAD_REQUEST.toString(),
                        "Can not open file. Please check if file is zipped correctly.");
                return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Response response = new Response( HttpStatus.BAD_REQUEST.toString(),
                        "Can not open file. Please check if file is zipped correctly.");
                return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            Response response = new Response( HttpStatus.BAD_REQUEST.toString(), "Failed to upload because File is empty");
            return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
        }
    }

}
