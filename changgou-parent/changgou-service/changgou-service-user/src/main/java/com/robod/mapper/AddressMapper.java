package com.robod.mapper;
import com.robod.user.pojo.Address;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Address的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("addressMapper")
public interface AddressMapper extends Mapper<Address> {
}
