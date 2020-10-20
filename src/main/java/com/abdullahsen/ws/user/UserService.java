package com.abdullahsen.ws.user;

import com.abdullahsen.ws.error.NotFoundException;
import com.abdullahsen.ws.file.FileService;
import com.abdullahsen.ws.user.vm.UserUpdateVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;

@Service

public class UserService {

	
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	FileService fileService;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileService fileService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.fileService = fileService;
	}

	public void save(User user) {
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		userRepository.save(user);
	}


    public Page<User> getUsers(Pageable pageable, User user) {
		if (user != null){
			return userRepository.findByUsernameNot(user.getUsername(),pageable);
		}
		return userRepository.findAll(pageable);
    }

    public User getByUsername(String username){
		User inDB = userRepository.findByUsername(username);

		if (inDB == null){
			throw new NotFoundException();
		}
		return userRepository.findByUsername(username);
	}

	public User updateUser(String username, UserUpdateVm updatedUser) {
		User inDB = getByUsername(username);
		inDB.setDisplayName(updatedUser.getDisplayName());
		if(updatedUser.getImage()!= null){
			String oldImage = inDB.getImage();
			try {
				String storedFileName = fileService.writeBase64EncodedStringToFile(updatedUser.getImage());
				inDB.setImage(storedFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileService.deleteFile(oldImage);
		}
		return userRepository.save(inDB);
	}

}
