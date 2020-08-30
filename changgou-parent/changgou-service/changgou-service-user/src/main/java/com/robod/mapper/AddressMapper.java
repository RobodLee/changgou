package com.robod.mapper;
import com.robod.user.pojo.Address;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Address的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("addressMapper")
public interface AddressMapper extends Mapper<Address> {

    /**
     * 根据用户名查询收货地址
     * @param username 用户名
     * @return 收获地址集合
     */
    @Select("select * from tb_address where username = #{username}")
    List<Address> list(String username);

}
