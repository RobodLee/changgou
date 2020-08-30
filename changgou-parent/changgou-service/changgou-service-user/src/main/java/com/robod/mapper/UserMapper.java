package com.robod.mapper;
import com.robod.user.pojo.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:User的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("userMapper")
public interface UserMapper extends Mapper<User> {

    /**
     * 添加积分
     * @param username 用户
     * @param points   积分
     */
    @Update("update tb_user set points = points + #{points} where username = username")
    void addPoints(String username, int points);

}
