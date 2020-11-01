package com.abdullahsen.ws.file.vm;

import com.abdullahsen.ws.file.FileAttachment;
import lombok.Data;

@Data
public class FileAttachmentVM {

    private String name;

    private String fileType;

    public FileAttachmentVM(FileAttachment fileAttachment){
        this.name = fileAttachment.getName();
        this.fileType = fileAttachment.getFileType();
    }
}
