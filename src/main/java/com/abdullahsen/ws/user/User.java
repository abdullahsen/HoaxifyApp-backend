package com.abdullahsen.ws.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.abdullahsen.ws.shared.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
@Entity
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull(message = "{hoaxify.constraints.username.NotNull.message}")
	@Size(min = 4, max = 255)
	@UniqueUsername(message = "{hoaxify.constraints.username.UniqueUsername.message}")
	@JsonView(Views.Base.class)
	private String username;
	
	@NotNull(message = "{hoaxify.constraints.displayName.NotNull.message}")
	@Size(min = 4, max = 255)
	@JsonView(Views.Base.class)
	private String displayName;
	
	@NotNull
	@Size(min = 8, max = 255)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
			message = "{hoaxify.constraints.password.Pattern.message}" )
	private String password;

	@JsonView(Views.Base.class)
	private String image;
	
		
}
