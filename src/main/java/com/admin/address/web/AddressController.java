package com.admin.address.web;


import com.admin.address.config.auth.LoginUser;
import com.admin.address.config.auth.dto.SessionUser;
import com.admin.address.service.AddressService;
import com.admin.address.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostRemove;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AddressController {
    private final AddressService addressService;


    @PostMapping("/api/v1/address")
    public Long save(@RequestBody @Valid AddressSaveRequestDto requestDto){
        return addressService.save(requestDto);
    }

    @PutMapping("/api/v1/address/{id}")
    public Long update(@PathVariable Long id, @RequestBody @Valid AddressSaveRequestDto requestDto){
        return addressService.update(id,requestDto);
    }


    @GetMapping("/api/v1/address/{id}")
    public AddressResponseDto findById(@PathVariable Long id){
        return addressService.findById(id);
    }


    @DeleteMapping("/api/v1/address/{id}")
    public Long delete(@PathVariable Long id){
        addressService.delete(id);
        return id;
    }

    @PostMapping("/api/v1/address/delete")
    public int deleteList(@RequestParam(value="ids[]")List<Long> ids){

       return addressService.deleteOverlap(ids);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Object exception(Exception e) {
        return e.getMessage();
    }

}
