package com.robod.mapper;
import com.robod.user.pojo.UndoLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:UndoLog的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository("undoLogMapper")
public interface UndoLogMapper extends Mapper<UndoLog> {
}
