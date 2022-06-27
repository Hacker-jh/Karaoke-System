package file.zhang.dao;

import file.zhang.entity.Guest_info;
import file.zhang.entity.Guest_infoExample;
import file.zhang.entity.Guest_infoWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Guest_infoMapper {
    long countByExample(Guest_infoExample example);

    int deleteByExample(Guest_infoExample example);

    int deleteByPrimaryKey(String id);

    int insert(Guest_infoWithBLOBs record);

    int insertSelective(Guest_infoWithBLOBs record);

    List<Guest_infoWithBLOBs> selectByExampleWithBLOBs(Guest_infoExample example);

    List<Guest_info> selectByExample(Guest_infoExample example);

    Guest_infoWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Guest_infoWithBLOBs record, @Param("example") Guest_infoExample example);

    int updateByExampleWithBLOBs(@Param("record") Guest_infoWithBLOBs record, @Param("example") Guest_infoExample example);

    int updateByExample(@Param("record") Guest_info record, @Param("example") Guest_infoExample example);

    int updateByPrimaryKeySelective(Guest_infoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(Guest_infoWithBLOBs record);

    int updateByPrimaryKey(Guest_info record);
}