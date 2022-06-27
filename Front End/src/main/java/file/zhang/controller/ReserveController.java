package file.zhang.controller;


import file.zhang.entity.Reserve_info;
import file.zhang.service.ReserveService;
import file.zhang.utils.MessageAndData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/reserve")
@CrossOrigin
public class ReserveController {
    @Autowired
    private ReserveService reserveService;

    @RequestMapping(value = "/free",method = RequestMethod.GET)
    public MessageAndData qetreservebyRId(String room_id){
        MessageAndData messageAndData;
        int[] freetime = {0, 0, 0, 0, 0, 0};
        List<Reserve_info> reserve_list=reserveService.findReserve(room_id);
        if(reserve_list!=null) {
            System.out.println(reserve_list);
            for (int i = 0; i < reserve_list.size(); i++) {
                Reserve_info reserve_info = reserve_list.get(i);
                System.out.println("预约数字串:" + reserve_info.getReserveFlag());
                String reservetime = reserve_info.getReserveFlag().toString();
                for (int j = 1; j < reservetime.length(); j++) {
                    if (reservetime.charAt(j) == '1') {
                        freetime[j - 1] = 1;
                    }
                }
            }
        }
//        for(int i=0;i<6;i++) {
//            System.out.println(freetime[i]);
//        }
        messageAndData=MessageAndData.success();
        messageAndData.add("free",freetime).setMessage("获取空闲时间段成功");
        return messageAndData;
    }

    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public MessageAndData insert(String guest_id,String room_id,String reserve_flag){
        MessageAndData messageAndData;
        System.out.println(guest_id);
        System.out.println(room_id);
        System.out.println(reserve_flag);
        String id= String.valueOf(System.currentTimeMillis());
        String time= String.valueOf(LocalDateTime.now());
        System.out.println(id);
        System.out.println(time);

        Reserve_info reserve_info=new Reserve_info();
        reserve_info.setgId(guest_id);
        BigDecimal bd=new BigDecimal(reserve_flag);
        reserve_info.setReserveFlag(bd);
        reserve_info.setrId(room_id);
        reserve_info.setTime(time);
        reserve_info.setId(id);
        reserveService.insertReserve(reserve_info);
        messageAndData = MessageAndData.success();
        messageAndData.setMessage("预约成功");
        return messageAndData;
    }


    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public MessageAndData insert(String id){
        MessageAndData messageAndData;
        reserveService.deleteReserve(id);

        messageAndData = MessageAndData.success();
        messageAndData.setMessage("删除成功");
        return messageAndData;
    }

}
