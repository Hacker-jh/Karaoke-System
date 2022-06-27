package file.zhang.service;

import file.zhang.entity.Guest_info;
import file.zhang.entity.Guest_infoWithBLOBs;

public interface GuestService {
    public Guest_infoWithBLOBs getGuest(String guest_id);
    public void insertGuest(Guest_infoWithBLOBs guest);

}
