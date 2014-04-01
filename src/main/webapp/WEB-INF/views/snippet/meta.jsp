<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: meng.zm
  Date: 14-3-13
  Time: 下午9:07
  To change this template use File | Settings | File Templates.
--%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>文库</title>
<script type="text/javascript" src="js/jquery.js"></script>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<link href="js/uploadify/uploadify.css" rel="stylesheet" type="text/css"/>
<link href="css/base.css" rel="stylesheet" type="text/css"/>
