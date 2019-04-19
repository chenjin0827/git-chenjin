<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎登陆</title>
    <script  type="text/javascript" src="resources/js/jquery-easyui/jquery.min.js"></script>
    <script>
        $(function () {
            $("#loginBt").click(function(){
                alert(44);
            });
        });

    </script>
</head>
<body>
<form>
    <div>
        <form id="form">
            <div>用户名：<input type="text"/></div>
            <div>密码：<input type="text"/></div>
            <button id="loginBt">登录</button>
        </form>
    </div>
</form>

</body>
</html>
