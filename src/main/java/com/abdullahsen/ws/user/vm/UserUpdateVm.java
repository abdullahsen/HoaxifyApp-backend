package com.abdullahsen.ws.user.vm;

import com.abdullahsen.ws.shared.FileType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateVm {

    @NotNull(message = "{hoaxify.constraints.displayName.NotNull.message}")
    @Size(min = 4, max = 255)
    private String displayName;

    @FileType(types = {"jpeg","png"})
    private String image;
}
