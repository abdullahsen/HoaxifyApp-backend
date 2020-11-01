package com.abdullahsen.ws.hoax;

import com.abdullahsen.ws.error.AuthorizationException;
import com.abdullahsen.ws.file.FileAttachment;
import com.abdullahsen.ws.file.FileAttachmentRepository;
import com.abdullahsen.ws.file.FileService;
import com.abdullahsen.ws.hoax.vm.HoaxSubmitVM;
import com.abdullahsen.ws.shared.CurrentUser;
import com.abdullahsen.ws.user.User;
import com.abdullahsen.ws.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HoaxService {


    HoaxRepository hoaxRepository;
    FileAttachmentRepository fileAttachmentRepository;
    FileService fileService;
    UserService userService;

    public HoaxService(HoaxRepository hoaxRepository,
                       FileAttachmentRepository fileAttachmentRepository,
                       FileService fileService,
                       UserService userService) {
        this.hoaxRepository = hoaxRepository;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.fileService = fileService;
        this.userService = userService;
    }


    public void save(HoaxSubmitVM hoaxSubmitVM, User user) {
        Hoax hoax = new Hoax();
        hoax.setContent(hoaxSubmitVM.getContent());
        hoax.setTimestamp(new Date());
        hoax.setUser(user);
        hoaxRepository.save(hoax);
        Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository.findById(hoaxSubmitVM.getAttachmentId());
        if(optionalFileAttachment.isPresent()){
            FileAttachment fileAttachment = optionalFileAttachment.get();
            fileAttachment.setHoax(hoax);
            fileAttachmentRepository.save(fileAttachment);
        }
    }

    public Page<Hoax> getHoaxes(Pageable pageable) {
        return hoaxRepository.findAll(pageable);
    }

    public Page<Hoax> getHoaxesByUsername(Pageable pageable, String username) {
        User user = userService.getByUsername(username);
        return hoaxRepository.findByUser(user, pageable);
    }

    public Page<Hoax> getOldHoaxes(long id, String username, Pageable pageable) {
        Specification<Hoax> specification = idLessThan(id);
        if (username != null) {
            User user = userService.getByUsername(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.findAll(specification, pageable);
    }


    public long getNewHoaxesCount(long id, String username) {
        Specification<Hoax> specification = idGreaterThan(id);
        if (username != null) {
            User user = userService.getByUsername(username);
            specification = specification.and(userIs(user));
        }

        return hoaxRepository.count(specification);
    }

    public List<Hoax> getNewHoaxes(long id, String username, Sort sort) {
        Specification<Hoax> specification = idGreaterThan(id);
        if (username != null) {
            User user = userService.getByUsername(username);
            specification = specification.and(userIs(user));
        }

        return hoaxRepository.findAll(specification, sort);
    }

    Specification<Hoax> idLessThan(long id) {
        return (Specification<Hoax>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"), id);
    }

    Specification<Hoax> idGreaterThan(long id) {
        return (Specification<Hoax>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"), id);
    }

    Specification<Hoax> userIs(User user) {
        return (Specification<Hoax>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

    public void delete(long id) {
        Hoax inDB = hoaxRepository.getOne(id);
        if(inDB.getFileAttachment() != null){
            String fileName = inDB.getFileAttachment().getName();
            fileService.deleteAttachmentFile(fileName);
        }
        hoaxRepository.deleteById(id);
    }

}
