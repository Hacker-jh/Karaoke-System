package file.zhang.service.impl;


import file.zhang.dao.Reserve_infoMapper;
import file.zhang.entity.Reserve_info;
import file.zhang.entity.Reserve_infoExample;
import file.zhang.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value="reserve_infoService")
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private Reserve_infoMapper reserve_infoMapper;
    @Override
    public List<Reserve_info> findReserve(String room_id) {
        Reserve_infoExample reserve_infoExample=new Reserve_infoExample(Reserve_info.class);
        Reserve_infoExample.Criteria criteria=reserve_infoExample.createCriteria();
        criteria.andRIdEqualTo(room_id);
        List<Reserve_info> reserve_infoList = reserve_infoMapper.selectByExample(reserve_infoExample);
        return reserve_infoList;
    }

    @Override
    public void insertReserve(Reserve_info reserve_info) {
     this.reserve_infoMapper.insert(reserve_info);
    }

    @Override
    public List<Reserve_info> getReserve(String guest_id) {
        Reserve_infoExample reserve_infoExample=new Reserve_infoExample(Reserve_info.class);
        Reserve_infoExample.Criteria criteria=reserve_infoExample.createCriteria();
        criteria.andGIdEqualTo(guest_id);
        List<Reserve_info> reserve_infoList = reserve_infoMapper.selectByExample(reserve_infoExample);
        return reserve_infoList;
    }

    @Override
    public void deleteReserve(String id) {
        System.out.println(id);
        this.reserve_infoMapper.deleteByPrimaryKey(id);
    }

}
