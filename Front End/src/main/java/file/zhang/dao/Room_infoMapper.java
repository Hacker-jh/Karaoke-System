package file.zhang.dao;

import file.zhang.entity.Room_info;
import file.zhang.entity.Room_infoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Room_infoMapper {
    long countByExample(Room_infoExample example);

    int deleteByExample(Room_infoExample example);

    int deleteByPrimaryKey(String id);

    int insert(Room_info record);

    int insertSelective(Room_info record);

    List<Room_info> selectByExample(Room_infoExample example);

    Room_info selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Room_info record, @Param("example") Room_infoExample example);

    int updateByExample(@Param("record") Room_info record, @Param("example") Room_infoExample example);

    int updateByPrimaryKeySelective(Room_info record);

    int updateByPrimaryKey(Room_info record);
}