package file.zhang.service;

import file.zhang.entity.Room_info;

import java.util.List;

public interface RoomService {
    public List<Room_info> getAllRoom();
    public Room_info getRoom(String room_id);
}
