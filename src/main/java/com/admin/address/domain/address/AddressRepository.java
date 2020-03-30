package com.admin.address.domain.address;

import com.admin.address.domain.post.Posts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query(value="SELECT p FROM Address p where p.id = :id ORDER BY p.modifiedDate DESC")
    List<Address> findAddressByIdList(@Param("id")Long id);

    @Query(value="SELECT p FROM Address p where p.id = :id and p.addressPhone=:addressPhone")
    List<Address> findAddressByIdPhoneList(@Param("id")Long id,@Param("addressPhone")String addressPhone);

    @Query(value="SELECT p FROM Address p where p.id = :id and p.addressEmail=:addressEmail")
    List<Address> findAddressByIdEmailList(@Param("id")Long id,@Param("addressEmail")String addressEmail);

    @Query(value="SELECT p FROM Address p where p.id = :id and p.addressName=:addressName")
    List<Address> findAddressByIdNameList(@Param("id")Long id,@Param("addressName")String addressName);



    @Transactional
    @Modifying
    @Query(value="DELETE FROM Address p where p.addressId in :ids")
    int deleteAddressesById(@Param("ids")List<Long> ids);
}
