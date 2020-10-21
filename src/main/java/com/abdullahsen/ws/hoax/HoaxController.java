package com.abdullahsen.ws.hoax;

import com.abdullahsen.ws.hoax.vm.HoaxVM;
import com.abdullahsen.ws.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HoaxController {

    @Autowired
    HoaxService hoaxService;

    @PostMapping("/api/1.0/hoaxes")
    GenericResponse saveHoax(@Valid @RequestBody Hoax hoax){
        hoaxService.save(hoax);
        return new GenericResponse("Hoax is saved");
    }

    @GetMapping("/api/1.0/hoaxes")
    Page<HoaxVM> getHoaxes(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return hoaxService.getHoaxes(pageable).map(hoax -> new HoaxVM(hoax));
    }




}
