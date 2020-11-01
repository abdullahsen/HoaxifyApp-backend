package com.abdullahsen.ws.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class FileCleanupService {

    @Autowired
    FileAttachmentRepository fileAttachmentRepository;

    @Autowired
    FileService fileService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 100)
    public void cleanupStorage(){
        Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        List<FileAttachment> filesToBeDeleted =
                fileAttachmentRepository.findByDateBeforeAndHoaxIsNull(twentyFourHoursAgo);

        for(FileAttachment file:filesToBeDeleted){
            fileService.deleteAttachmentFile(file.getName());
            fileAttachmentRepository.deleteById(file.getId());
        }
    }
}
