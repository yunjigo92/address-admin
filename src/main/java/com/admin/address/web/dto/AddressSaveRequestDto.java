package com.admin.address.web.dto;

import com.admin.address.domain.address.Address;
import com.admin.address.domain.post.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class AddressSaveRequestDto {

    private Long id;

    @NotEmpty(message = "이름은 필수항목 입니다.")
    @Size(max=20,message="20자 이상 입력할 수 없습니다.")
    private String addressName;

    @NotEmpty(message = "이메일은 필수항목 입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String addressEmail;

    @NotEmpty(message = "연락처는 필수항목입니다.")
    @Pattern(regexp="(?:(010-?\\d{4})|(01[1|6|7|8|9]-?\\d{3,4}))-?\\d{4}$", message = "연락처를 양식에 맞게 작성해 주세요.")
    private String addressPhone;


    @Builder
    public AddressSaveRequestDto(Long id,String addressName, String addressEmail, String addressPhone){
       // this.addressNum = addressNum;
        this.id = id;
        this.addressName = addressName;
        this.addressEmail = addressEmail;
        this.addressPhone = addressPhone;
    }

    public Address toEntity(){
        return Address.builder()
                //.addressNum(addressNum)
                .id(id)
                .addressName(addressName)
                .addressEmail(addressEmail)
                .addressPhone(addressPhone)
                .build();
    }
}
