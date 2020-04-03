package com.admin.address.web.dto;

import com.admin.address.domain.address.Address;

import java.time.LocalDateTime;

public class AddressListResponseDto {
    private Long addressId;
    private String addressName;
    private String addressEmail;
    private String addressPhone;
    private LocalDateTime modifiedDate;

    public AddressListResponseDto(Address entity){
        this.addressId = entity.getAddressId();
        this.addressName = entity.getAddressName();
        this.addressEmail = entity.getAddressEmail();
        this.addressPhone = entity.getAddressPhone();
        this.modifiedDate = entity.getModifiedDate();
    }

}
