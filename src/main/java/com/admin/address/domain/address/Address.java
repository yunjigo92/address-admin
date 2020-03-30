package com.admin.address.domain.address;

import com.admin.address.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Getter
@NoArgsConstructor
@Entity
public class Address extends BaseTimeEntity {


    @Column(nullable = false)
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(nullable = true)
    private int addressNum;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private String addressEmail;

    @Column(nullable = false)
    private String addressPhone;



    @Builder
    public Address(Long id,String addressName, String addressEmail, String addressPhone){
        this.id = id;
        this.addressName = addressName;
        this.addressEmail = addressEmail;
        this.addressPhone = addressPhone;
    }


    public Address update(String addressName, String addressEmail,String addressPhone){
        this.addressName = addressName;
        this.addressEmail = addressEmail;
        this.addressPhone = addressPhone;
        return this;
    }



}
