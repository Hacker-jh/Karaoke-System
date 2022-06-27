package file.zhang.service.impl;

import file.zhang.dao.Guest_infoMapper;
import file.zhang.entity.Guest_infoWithBLOBs;
import file.zhang.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="guest_infoService")
public class GuestServiceImpl implements GuestService {
    @Autowired
    private Guest_infoMapper guest_infoMapper;

    @Override
    public Guest_infoWithBLOBs getGuest(String guest_id) {

        return guest_infoMapper.selectByPrimaryKey(guest_id);
    }

    @Override
    public void insertGuest(Guest_infoWithBLOBs guest_info) {
          this.guest_infoMapper.insert(guest_info);
    }

}
