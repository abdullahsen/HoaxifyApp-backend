package com.abdullahsen.ws.hoax.vm;

import com.abdullahsen.ws.file.FileAttachment;
import com.abdullahsen.ws.file.vm.FileAttachmentVM;
import com.abdullahsen.ws.hoax.Hoax;
import com.abdullahsen.ws.user.User;
import com.abdullahsen.ws.user.vm.UserVM;
import lombok.Data;

import java.util.Date;

@Data
public class HoaxVM {

    private long id;
    private String content;
    private long timestamp;
    private UserVM user;
    private FileAttachmentVM fileAttachment;

    public HoaxVM(Hoax hoax) {
        this.id = hoax.getId();
        this.content = hoax.getContent();
        this.timestamp = hoax.getTimestamp().getTime();
        this.user = new UserVM(hoax.getUser());
        if(hoax.getFileAttachment() != null){
            this.fileAttachment = new FileAttachmentVM(hoax.getFileAttachment());
        }
    }
}
