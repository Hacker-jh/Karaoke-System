package file.zhang.controller;

import file.zhang.entity.Room_info;
import file.zhang.service.RoomService;
import file.zhang.utils.MessageAndData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/room")
@CrossOrigin
public class RoomController {
    @Autowired
    private RoomService roomService;
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public MessageAndData qetallroom(String search){
        List<Room_info> room_list= roomService.getAllRoom();
        System.out.println(room_list);
        MessageAndData messageAndData;
        if(search=="")
        {
            if (room_list == null) {
                messageAndData = MessageAndData.error();
                messageAndData.setMessage("未查到房间");
            } else {
                messageAndData = MessageAndData.success();
                messageAndData.add("roomlist", room_list).setMessage("获取房间列表成功");
            }
        }
        else{
            Room_info room=roomService.getRoom(search);
            if(room==null){
                messageAndData=MessageAndData.error();
                messageAndData.setMessage("未查到房间");
            }
            else{
                List<Room_info> roomList=new ArrayList<>();
                roomList.add(room);
                messageAndData=MessageAndData.success();
                messageAndData.add("roomlist",roomList).setMessage("找到房间");
            }
        }
        return messageAndData;
    }
}
