package com.admin.address.web.dto;

import com.admin.address.domain.address.Address;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class AddressResponseDto {
    private Long addressId;
    private int addressNum;
    private String addressName;
    private String addressEmail;
    private String addressPhone;


    public AddressResponseDto(Address entity){
        this.addressId = entity.getAddressId();
        this.addressEmail = entity.getAddressEmail();
        this.addressName = entity.getAddressName();
        this.addressPhone = entity.getAddressPhone();
        this.addressNum = entity.getAddressNum();
    }

}
