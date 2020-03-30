package com.admin.address.web;

import com.admin.address.config.auth.LoginUser;
import com.admin.address.config.auth.dto.SessionUser;
import com.admin.address.service.AddressService;
import com.admin.address.web.dto.AddressListResponseDto;
import com.admin.address.web.dto.AddressResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final AddressService addressService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 20) Pageable pageable){


        if(user != null){
            model.addAttribute("userName",user.getName());
            model.addAttribute("id",user.getId());

            model.addAttribute("address",addressService.findAllByIdDesc(pageable));
            return "index";
        }else{
            return "login";
        }
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }


    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id,Model model) {
        AddressResponseDto dto = addressService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }

    @GetMapping("/address/save")
    public String addressSave(Model model,@LoginUser SessionUser user){
        model.addAttribute("id",user.getId());
        return "address-save";
    }

    @GetMapping("/address/update/{addressId}")
    public String addressUpdate(@PathVariable Long addressId,Model model) {
        AddressResponseDto dto = addressService.findById(addressId);
        model.addAttribute("address",dto);
        return "address-update";
    }


    @GetMapping("/address/overlapPhone/{addressPhone}")
    public String findByPhoneId(@PathVariable String addressPhone,@LoginUser SessionUser user, Model model){
        List<AddressListResponseDto> responseDtoList= addressService.findAddressByIdPhoneList(user.getId(),addressPhone);
        model.addAttribute("address",responseDtoList);
        model.addAttribute("count",responseDtoList.size());
        return "index";
    }

    @GetMapping("/address/overlapEmail/{addressEmail}")
    public String findByEmailId(@PathVariable String addressEmail,@LoginUser SessionUser user, Model model){
        List<AddressListResponseDto> responseDtoList= addressService.findAddressByIdEmailList(user.getId(),addressEmail);
        model.addAttribute("address",responseDtoList);
        model.addAttribute("count",responseDtoList.size());
        return "index";
    }

    @GetMapping("/address/overlapName/{addressName}")
    public String findByNameId(@PathVariable String addressName,@LoginUser SessionUser user, Model model){
        List<AddressListResponseDto> responseDtoList=addressService.findAddressByIdNameList(user.getId(),addressName);
        model.addAttribute("address",responseDtoList);
        model.addAttribute("count",responseDtoList.size());
        return "index";
    }


}
