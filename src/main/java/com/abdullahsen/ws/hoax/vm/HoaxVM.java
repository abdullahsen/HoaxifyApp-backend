package com.abdullahsen.ws.hoax.vm;

import com.abdullahsen.ws.hoax.Hoax;
import lombok.Data;

import java.util.Date;

@Data
public class HoaxVM {

    private long id;
    private String content;
    private Date timestamp;

    public HoaxVM(Hoax hoax) {
        this.id = hoax.getId();
        this.content = hoax.getContent();
        this.timestamp = hoax.getTimestamp();
    }
}
