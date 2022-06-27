package file.zhang.service.impl;

import file.zhang.dao.Room_infoMapper;
import file.zhang.entity.Room_info;
import file.zhang.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service(value="room_infoService")
public class RoomServiceImpl implements RoomService {

    @Autowired
    private Room_infoMapper room_infoMapper;

    @Override
    public List<Room_info> getAllRoom() {
        return room_infoMapper.selectByExample(null);
    }

    @Override
    public Room_info getRoom(String room_id) {
        return room_infoMapper.selectByPrimaryKey(room_id);
    }
}
