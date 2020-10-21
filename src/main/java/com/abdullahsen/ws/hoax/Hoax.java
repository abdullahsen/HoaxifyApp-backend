package com.abdullahsen.ws.hoax;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class Hoax {

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 1, max = 1000)
    @Column(length = 1000)
    private String content;

    private Date timestamp;
}
