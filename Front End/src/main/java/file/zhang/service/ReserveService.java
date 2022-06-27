package file.zhang.service;

import file.zhang.entity.Reserve_info;

import java.util.List;

public interface ReserveService {
    public List<Reserve_info> findReserve(String room_id);
    public void insertReserve(Reserve_info reserve_info);

    public List<Reserve_info> getReserve(String guest_id);

    public void deleteReserve(String id);
}
