package file.zhang.controller;

import file.zhang.entity.Guest_info;
import file.zhang.entity.Guest_infoWithBLOBs;
import file.zhang.entity.Reserve_info;
import file.zhang.service.GuestService;
import file.zhang.service.ReserveService;
import file.zhang.utils.MessageAndData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller(value="GuestController")
@RestController
@RequestMapping("/guest")
@CrossOrigin
public class GuestController {

    @Autowired
    private GuestService guestService;
    @Autowired
    private ReserveService reserveService;
    private Guest_info guest;


    //@ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public MessageAndData qetguestbyId(String username, String password,HttpSession session){
        Guest_info guest_info=guestService.getGuest(username);
        System.out.println(username);
        System.out.println(guest_info);
        MessageAndData messageAndData;
        if(guest_info==null){
            messageAndData=MessageAndData.error();
            messageAndData.setMessage("未查到用户");
        }
        if(guest_info.getPassword().equals(password)) {
            messageAndData=MessageAndData.success();
            messageAndData.add("guest",guest_info).setMessage("密码正确");
            guest=guest_info;
        }
        else {
            messageAndData = MessageAndData.error();
            messageAndData.setMessage("查找到用户，密码错误");
        }

        return messageAndData;
    }

    @RequestMapping(value = "/autologin",method = RequestMethod.GET)
    public MessageAndData autologin(HttpSession session){
        MessageAndData messageAndData;
        System.out.println(guest);
        if (guest == null){
            messageAndData=MessageAndData.error();
            messageAndData.setMessage("您还未登录");
        }else{
            messageAndData = MessageAndData.success();
            messageAndData.add("guest",guest);
        }
        return messageAndData;
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public MessageAndData addguest(String username,String name,String password,String phone) {
        MessageAndData messageAndData;
        Guest_infoWithBLOBs guest_info=guestService.getGuest(username);
        if (guest_info != null){
            messageAndData=MessageAndData.error();
            messageAndData.setMessage("用户名已存在");
        }else{
            messageAndData = MessageAndData.success();
            messageAndData.setMessage("注册成功");
            guest_info = new Guest_infoWithBLOBs();
            guest_info.setId(username);
            guest_info.setName(name);
            guest_info.setPassword(password);
            guest_info.setPhone(phone);
            guest_info.setVip("1");
            guestService.insertGuest(guest_info);
            guest=guest_info;
        }
        return messageAndData;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public MessageAndData logoutguest() {
        MessageAndData messageAndData;
        if (guest == null){
            messageAndData=MessageAndData.error();
            messageAndData.setMessage("您还未登录");
        }else{
            messageAndData = MessageAndData.success();
            messageAndData.setMessage("成功退出");
            guest=null;
        }
        return messageAndData;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public MessageAndData all() {
        MessageAndData messageAndData;
        System.out.println(guest);
        if (guest == null){
            messageAndData=MessageAndData.error();
            messageAndData.setMessage("您还未登录");
        }else{
            messageAndData = MessageAndData.success();
            messageAndData.add("guest",guest);
            List<Reserve_info> reserve_list=reserveService.getReserve(guest.getId());
            messageAndData.add("reservelist",reserve_list).setMessage("查询客户预约单成功");
        }
        return messageAndData;
    }
}
