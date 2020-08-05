package com.robod.mapper;
import com.robod.user.pojo.OauthClientDetails;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:OauthClientDetails的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("oauthClientDetailsMapper")
public interface OauthClientDetailsMapper extends Mapper<OauthClientDetails> {
}
