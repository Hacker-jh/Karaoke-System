window.onload=function () {
    init();
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


    function init(data_arr, color_arr, text_arr,realdata_arr) {
        //绘制饼图
        //比例数据和颜色
         var data_arr = [0.2, 0.25, 0.45, 0.1];
         var color_arr = ["#00FF21", "#FFAA00", "#00AABB", "#FF4400"];
         var text_arr = ["小小房", "小房", "中房", "大房"];

        drawCircle("canvas_circle", data_arr, color_arr, text_arr);
    }



    function drawCircle(canvasId, data_arr, color_arr, text_arr)
    {
        var c = document.getElementById(canvasId);
        var ctx = c.getContext("2d");

        var radius = c.height / 2 - 20; //半径
        var ox = radius + 20, oy = radius + 20; //圆心

        var width = 30, height = 10; //图例宽和高
        var posX = ox * 2 + 20, posY = 30;   //
        var textX = posX + width + 5, textY = posY + 10;

        var startAngle = 0; //起始弧度
        var endAngle = 0;   //结束弧度
        for (var i = 0; i < data_arr.length; i++)
        {
            //绘制饼图
            endAngle = endAngle + data_arr[i] * Math.PI * 2; //结束弧度
            ctx.fillStyle = color_arr[i];
            ctx.beginPath();
            ctx.moveTo(ox, oy); //移动到到圆心
            ctx.arc(ox, oy, radius, startAngle, endAngle, false);
            ctx.closePath();
            ctx.fill();
            startAngle = endAngle; //设置起始弧度

            //绘制比例图及文字
            ctx.fillStyle = color_arr[i];
            ctx.fillRect(posX, posY + 20 * i, width, height);
            ctx.moveTo(posX, posY + 20 * i);
            ctx.font = 'bold 12px 微软雅黑';    //斜体 30像素 微软雅黑字体
            ctx.fillStyle = color_arr[i]; //"#000000";
            var percent = text_arr[i] + "：" +data_arr[i]*100 +"%";
            ctx.fillText(percent, textX, textY + 20 * i);
        }
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
                        $("#register_btn").modal('hide');
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






}