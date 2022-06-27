window.onload=function () {
    $.ajax({
        url: "http://localhost:8888/guest/autologin",//超级链接
        method: "GET",
        error: function (xhr, status, error) {
            console.log(status);
        },
        success: function (result, status, xhr) {//xhr   xmlHttpRequest
            console.log(result);
            //操作DOM渲染页面
            if (result.statusCode == "200") {
                $("#item1").text("个人信息").toggleClass("text-danger");
                $("#item2").text("姓名："+result.dataZone.guest.name).toggleClass("text-danger");
                $("#item3").text("手机号:"+result.dataZone.guest.phone).toggleClass("text-danger");
                $("#item4").text("VIP："+result.dataZone.guest.vip).toggleClass("text-danger");
                $("#item5").text("用户名："+result.dataZone.guest.id).toggleClass("text-danger");
                $("#function_opt").css("visibility", "visible");
                $("#navbardrop5").css("visibility", "visible");
                $("#login_btn").modal('hide');
                $("#register_btn").modal('hide');
                $("#register_button").css("visibility", "hidden");
                $("#login_button").css("visibility", "hidden");
                $("#logout_button").css("visibility", "visible");
                alert(result.message);
            } else {
                alert(result.message);
            }
        }});




    var room_bottom=document.querySelector("#room_button");
    room_bottom.onclick=function(){
        let text=document.getElementById("room_input").value;
        console.log(text);
        $.ajax({
            url: " http://localhost:8888/room/all",//普通登录
            method: "GET",

            data:{search:text},
            error: function (xhr, status, error) {
                console.log(status);
            },
            success: function (result, status, xhr) {//xhr   xmlHttpRequest
                console.log(result);
                //操作DOM渲染页面
                if (result.statusCode == "200") {
                    $("#book_form").html("");
                    var otbody=document.getElementsByTagName("tbody")[0];
                    var roomlist=result.dataZone.roomlist;
                    console.log(roomlist[0]);
                   for(room in roomlist){
                       var otr = document.createElement("tr");
                       var otd1= document.createElement("td");
                       var otd2= document.createElement("td");
                       var otd3= document.createElement("td");
                       var otd4= document.createElement("button");
                       otd4.setAttribute("class","btn btn-primary");
                       otd4.setAttribute("onclick","reserve('"+result.dataZone.roomlist[room].id+"')");
                       otd4.setAttribute("data-toggle","modal");
                       otd4.setAttribute("data-target","#reserve_btn")
                       otd1.innerText=result.dataZone.roomlist[room].id;
                       otd2.innerText=result.dataZone.roomlist[room].rsize;
                       otd3.innerText=result.dataZone.roomlist[room].price;
                       otd4.innerText="预约";
                       otr.appendChild(otd1);
                       otr.appendChild(otd2);
                       otr.appendChild(otd3);
                       otr.appendChild(otd4);
                       otbody.appendChild(otr);
                   }

                    alert(result.message);
                } else {
                    alert(result.message);
                }


            },
        });

    }



    function checkinformation() {
        var user_name=form1.uname.value;
        var user_pwd=form1.pwd.value;
        if((user_name=="")||(user_name==null)){
            $("#username-feedback").css("visibility", "visible");
            return false;
        }
        else if((user_pwd=="")||(user_pwd==null)){
            $("#password-feedback").css("visibility", "visible");
            return false;}
        else {
            return true;
        }
    }

    var login_bottom=document.querySelector("#login_1");
    login_bottom.onclick=function login() {
        if(checkinformation()){
            let username=form1.uname.value;
            let password=form1.pwd.value;
            $.ajax({
                url: " http://localhost:8888/guest/get",//普通登录
                method: "GET",

                data:{username:username,password:password},
                error: function (xhr, status, error) {
                    console.log(status);
                },
                success: function (result, status, xhr) {//xhr   xmlHttpRequest
                    console.log(result);
                    //操作DOM渲染页面
                    if (result.statusCode == "200") {
                        $("#item1").text("个人信息").toggleClass("text-danger");
                        $("#item2").text("姓名："+result.dataZone.guest.name).toggleClass("text-danger");
                        $("#item3").text("手机号:"+result.dataZone.guest.phone).toggleClass("text-danger");
                        $("#item4").text("VIP："+result.dataZone.guest.vip).toggleClass("text-danger");
                        $("#item5").text("用户名："+result.dataZone.guest.id).toggleClass("text-danger");
                        $("#function_opt").css("visibility", "visible");
                        $("#navbardrop5").css("visibility", "visible");
                        $("#login_btn").modal('hide');
                        $("#register_btn").modal('hide');
                        $("#register_button").css("visibility", "hidden");
                        $("#login_button").css("visibility", "hidden");
                        $("#logout_button").css("visibility", "visible");
                        alert(result.message);
                    } else {
                        alert(result.message);
                    }


                },
            });
        }
        ;
    }

    function checkinformation2() {
        let name=form2.name2.value;
        let pwd=form2.pwd2.value;
        let uname=form2.uname2.value;
        if((name=="")||(name==null)){
            //$("#sfzh-feedback2").css("visibility", "visible");
            return false;
        }
        else if((pwd=="")||(pwd==null)){
            //$("#password-feedback2").css("visibility", "visible");
            return false;}
        else if((uname=="")||(uname==null)){
            //$("#name-feedback2").css("visibility", "visible");
            return false;}
        else {
            return true;
        }
    }

    var register_1=document.querySelector("#register_1");//员工注册
    register_1.onclick=function() {
        let sfzh = form2.uname2.value;
        let khxm = form2.pwd2.value;
        let sjh = form2.name2.value;
        let xb = form2.phone2.value;
        if (checkinformation2()) {
            $.ajax({
                url: "http://localhost:8888/guest/register",//员工注册
                method: "GET",
                data: {username: sfzh, name: sjh, password: khxm, phone: xb},
                error: function (xhr, status, error) {
                    console.log(status);
                },
                success: function (result, status, xhr) {//xhr   xmlHttpRequest
                    console.log(result);
                    //操作DOM渲染页面
                    if (result.statusCode == "200") {

                        alert(result.message);


                    } else if (result.statusCode == "400") {
                        alert(result.message);

                    }


                },
            });
        }
        ;
    }

    var logout_bottom=document.querySelector("#logout_button");
    logout_bottom.onclick=function() {
        console.log("退出");
        $.ajax({
            url: "http://localhost:8888/guest/logout",//退出登录
            method: "GET",
            error: function (xhr, status, error) {
                console.log(status);
            },
            success: function (result, status, xhr) {//xhr   xmlHttpRequest
                console.log(result);
                //操作DOM渲染页面
                if (result.statusCode == "200") {
                    window.location.replace("./main.html")
                    alert(result.message);


                }
                else if(result.statusCode=="400"){
                    alert(result.message);

                }


            },
        });

    }
    var reserve_1=document.querySelector("#reserve_1");//预约房间
    reserve_1.onclick=function() {
        var buttons = document.getElementsByClassName("form-check-input");
        var reserve_flag = "1";
        for (button in buttons) {
            //console.log(button);
            //console.log(buttons[button].innerHTML);
            if (buttons[button].checked) {

                reserve_flag = reserve_flag.concat("1");
                //console.log(reserve_flag)
            }
            if(buttons[button].checked==false){
                reserve_flag = reserve_flag.concat("0");
                //console.log(reserve_flag)
            }
        }
        let guest_get=document.getElementById("item5").innerText;
        let guest_id=guest_get.split("：")[1];
        let room_id=document.getElementById("room_id").innerText;
        console.log(guest_id);
        $.ajax({
            url: "http://localhost:8888/reserve/insert",//发送预约信息
            method: "GET",
            data:{guest_id:guest_id,room_id:room_id,reserve_flag:reserve_flag},
            error: function (xhr, status, error) {
                console.log(status);
            },
            success: function (result, status, xhr) {//xhr   xmlHttpRequest
                console.log(result);
                //操作DOM渲染页面
                if (result.statusCode == "200") {
                    window.location.replace("./book_room.html")
                    alert(result.message);


                }
                else if(result.statusCode=="400"){
                    alert(result.message);

                }


            },
        });
        //onsole.log(reserve_flag)
    }


        // if (checkinformation2()) {
        //     $.ajax({
        //         url: "http://localhost:8888/guest/register",//员工注册
        //         method: "GET",
        //         data: {username: sfzh, name: sjh, password: khxm, phone: xb},
        //         error: function (xhr, status, error) {
        //             console.log(status);
        //         },
        //         success: function (result, status, xhr) {//xhr   xmlHttpRequest
        //             console.log(result);
        //             //操作DOM渲染页面
        //             if (result.statusCode == "200") {
        //
        //                 alert(result.message);
        //
        //
        //             } else if (result.statusCode == "400") {
        //                 alert(result.message);
        //
        //             }
        //
        //
        //         },
        //     });
        // }
        // ;






}
function reserve(number){
    console.log(number);
    $.ajax({
        url: "http://localhost:8888/reserve/free",//查找空闲时间
        method: "GET",
        data: {room_id:number},
        error: function (xhr, status, error) {
            console.log(status);
        },
        success: function (result, status, xhr) {//xhr   xmlHttpRequest
            console.log(result);
            //操作DOM渲染页面
            if (result.statusCode == "200") {
                document.getElementById("room_id").innerText=number;
                var freetime=result.dataZone.free;
                console.log(freetime);
                var buttons=document.getElementsByClassName("form-check-input");

                // buttons[0].setAttribute("disabled","true");
                // buttons[1].setAttribute("disabled","true");
                for(free in freetime){
                    buttons[free].removeAttribute("disabled");
                    buttons[free].checked=false;
                    if(freetime[free]=='1'){
                        buttons[free].setAttribute("disabled","true");
                    }

                }
                alert(result.message);


            } else if (result.statusCode == "400") {
                alert(result.message);

            }


        },
    });
}