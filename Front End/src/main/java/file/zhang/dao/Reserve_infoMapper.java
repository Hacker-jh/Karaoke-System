package file.zhang.dao;

import file.zhang.entity.Reserve_info;
import file.zhang.entity.Reserve_infoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Reserve_infoMapper {
    long countByExample(Reserve_infoExample example);

    int deleteByExample(Reserve_infoExample example);

    int deleteByPrimaryKey(String id);

    int insert(Reserve_info record);

    int insertSelective(Reserve_info record);

    List<Reserve_info> selectByExample(Reserve_infoExample example);

    Reserve_info selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Reserve_info record, @Param("example") Reserve_infoExample example);

    int updateByExample(@Param("record") Reserve_info record, @Param("example") Reserve_infoExample example);

    int updateByPrimaryKeySelective(Reserve_info record);

    int updateByPrimaryKey(Reserve_info record);
}