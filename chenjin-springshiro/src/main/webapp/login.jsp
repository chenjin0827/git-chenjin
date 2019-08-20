<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<html>
<head>
    <title>欢迎登陆</title>
    <script>

        <%
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        System.out.println("绝对路径为===="+basePath);
        System.out.println("end=====");

    %>
        $(function () {
            $("#form").form({
                url: "<c:out value='${pageContext.request.contextPath }'/>/test/index2.htmlx",
                onSubmit: function () {
                    return true;
                },
                success: function (result) {
                    alert("success====!");
                },
                error: function () {
                    alert("error=====");
                }
            });

            /* $("#form").submit(function(e){
                 alert("Submitted");
             });*/
            $("#form").submit();
        });

    </script>
</head>
<body>
<div>
    <form id="form">
        <div>用户名：<input name="s1" type="text"/></div>
        <div>密码：<input name="s2" type="text"/></div>
        <input name="s3" value="666"/>
        <input type="submit" id="submit"/>
    </form>
</div>

</body>
</html>
