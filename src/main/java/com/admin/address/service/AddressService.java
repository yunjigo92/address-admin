package com.admin.address.service;


import com.admin.address.domain.address.Address;
import com.admin.address.domain.address.AddressRepository;
import com.admin.address.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AddressService{

    private final AddressRepository addressRepository;


    @Transactional
    public Long save(AddressSaveRequestDto requestDto){
       long val = addressRepository.save(requestDto.toEntity()).getAddressId();
        System.out.println(val);
        return val;
    }

    @Transactional
    public Long update(Long addressId, AddressSaveRequestDto requestDto){
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주소록이 없습니다."+addressId));

        address.update(
                requestDto.getAddressName(),
                requestDto.getAddressEmail(),
                requestDto.getAddressPhone());

        return addressId;
    }

    public AddressResponseDto findById(Long addressId){
        Address entity = addressRepository.findById(addressId)
                .orElseThrow(()-> new IllegalArgumentException("해당 주소록이 없습니다." + addressId));
        return new AddressResponseDto(entity);
    }



    @Transactional(readOnly = true)
    public List<AddressListResponseDto> findAddressByIdPhoneList(Long id,String addressPhone){
        return addressRepository.findAddressByIdPhoneList(id,addressPhone).stream()
                .map(AddressListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AddressListResponseDto> findAddressByIdEmailList(Long id,String addressEmail){
        return addressRepository.findAddressByIdEmailList(id,addressEmail).stream()
                .map(AddressListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AddressListResponseDto> findAddressByIdNameList(Long id,String addressName){
        return addressRepository.findAddressByIdNameList(id,addressName).stream()
                .map(AddressListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<AddressListResponseDto> findAllDesc(Long id){
        return addressRepository.findAddressByIdList(id).stream()
                .map(AddressListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void delete(Long addressId){
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주소록이 없습니다"+ addressId));

        addressRepository.delete(address);
    }


    @Transactional
    public int deleteOverlap(List<Long> ids){
       return addressRepository.deleteAddressesById(ids);
    }



    @Transactional
    public List<AddressListResponseDto> findAllByIdDesc(Pageable pageable) {
        Page<Address> page = addressRepository.findAll(pageable);
        return page.stream()
                .map(AddressListResponseDto::new)
                .collect(Collectors.toList());
    }

}
