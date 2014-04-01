<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="snippet/doc.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="snippet/meta.jsp" %>

</head>
<body>
<div id="head">
    <div id="search-box">
        <a href="index.html" class="logo">
            <img src="img/logo_sovideo.png" width="150" height="27" alt="搜搜影视"
                 title="搜搜文档">
        </a>
        <form action="/v" method="get" name="searchForm" id="searchForm" class="search">
            <input type="text" class="query" id="query" name="query" value=""
                   autocomplete="off" maxlength="100">
           <%-- <input type="submit" value="" class="searchbtn" id="searchBtn">--%>
           <%-- <div id="autocomplete">
                <ul id="query-list">

                </ul>
            </div>--%>
        </form>
    </div>
</div>
<div id="body">
    <div id="block">
        <div id="b-l">
            <div class="line"></div>
            <div id="b-l-title" class="b-title">
                <span><a href="javascript:void(0)" status="max" style="color:#25b686">最多下载</a></span>
                <span><a href="javascript:void(0)" status="new">最新上传</a></span>
                <span><a href="javascript:void(0)" status="pdf">PDF</a></span>
                <span><a href="javascript:void(0)" status="word">WORD</a></span>
                <span><a href="javascript:void(0)" status="ppt">PPT</a></span>
                <span><a href="javascript:void(0)" status="txt">TXT</a></span>
                <span><a href="javascript:void(0)" status="excel">EXCEL</a></span>
                <span><a href="javascript:void(0)" status="other">其它</a></span>
            </div>
            <div class="b-title" style="display: none" id="p-title">
                <span><a href="javascript:void(0)" status="max" style="color:#25b686;font-size: 14px;font-weight: bold;" id="back">返回</a></span>
            </div>
            <div id="b-l-body">
                <div id="nav">
                    <ul id="nav-list"> </ul>
                </div>
                <div id="page">
                    <ul id="page-list"> </ul>
                </div>
                <div id="list-wrap">
                    <ul class="main-list" style="width: 2325px;" id="main-list"></ul>
                </div>
                <div style="position: absolute;left: 31px;top:61px;display: none" id="preview-box">
                    <a id="viewerPlaceHolder" style="width:800px;height:504px;display:block"></a>
                </div>
            </div>
        </div>
        <div id="b-r">
            <div class="line" style="width: 25px;<c:if test="${online=='YES'}"> display:none; </c:if>"></div>
             <div id="b-r-title" class="b-title" <c:if test="${online=='YES'}">style="display:none;" </c:if>>
                <span id="login"><a href="javascript:void(0)">登陆</a></span>
                <span id="reg"><a href="javascript:void(0)">注册</a></span>
            </div>
            <div id="b-r-title-1" class="b-title" <c:if test="${online=='YES'}">
                style="display:block;"
            </c:if>>
            </div>
            <div id="user-box" <c:if test="${online=='YES'}">
               style="display: none"
            </c:if>>
                <div style="width:554px;overflow: hidden;" id="user-slide">
                    <div id="login-box">
                        <p><label for="username">用户名/邮箱</label></p>
                        <p><input type="text"  id="username" name="username"/></p>
                        <p><label for="password">登录密码</label></p>
                        <p><input type="password" id="password" name="password"/></p>
                        <p class="skills mttips10 clear">
                            <button class="dBtn-btn dBtn-blue" align="bottom" tabindex="8" id="log-btn">登陆</button>
                            <span>&nbsp;&nbsp;或&nbsp;
                            <a href="javascript:void(0)" title="马上注册" onclick='$("#reg").click()'>马上注册</a></span></p>
                        <p id="log-msg" class="error"></p>
                    </div>
                    <div id="reg-box" style="padding-top:20px">
                        <div id="reg-view">
                            <p><label for="reg-name" style="margin-left: 13px">用户名:&nbsp;</label><input type="text" name="reg-name" id="reg-name"></p>
                            <p><label for="reg-pwd" style="margin-left: 27px">密码:&nbsp;</label><input type="password" name="reg-pwd" id="reg-pwd"></p>
                            <p><label for="reg-rpwd">确认密码:&nbsp;</label><input type="password" name="reg-rpwd" id="reg-rpwd"></p>
                            <p class="mttips" style="text-align: center;margin-top: 30px"><button class="dBtn-btn dBtn-blue regtips" id="reg-btn">确定注册</button></p>
                            <p id="reg-msg" class="error"></p>
                        </div>
                        <div id="online-view"></div>
                    </div>
                </div>
            </div>
            <div id="upload-box" <c:if test="${online=='YES'}">
                style="display:block;"
            </c:if>>
                <p style="padding-left:14px;position: relative;">
                    <img src="img/head.png" style="width: 120px;height: 120px;border:1px solid #ccc"/>
                    <span style="font-size: 16px;font-weight: bold;position: absolute;padding:10px;color: #598ede" id="login-username">${user.userName}</span>
                    <span style="font-size: 12px;font-weight: bold;position: absolute;padding:39px 10px;color: green;"><img src="img/gold.png"/> <span style="position: relative;top:-2px" id="login-mark">${user.userMark}</span></span>
                    <span style="position: absolute; right: 10px; top: 0px;font-size: 15px;color:#2284E2;font-weight: bold"><a href="javascript:void(0)" id="logout">退出</a></span>
                </p>
                <p>
                <div id="uploadBtn">
                    <input type="file" id="Filedata" name="Filedata" style="display: none;"/>
                    <div id="Filedata_mirror"></div>
                    <div id="file-queue">
                        <ul></ul>
                    </div>
                </div>
                </p>
            </div>
            <div style="" id="rd-box">
                <h4 style="padding-left:10px;font-weight: bold; font-family: 'Microsoft YaHei''微软雅黑';font-size:14px;margin:10px">文档贡献者</h4>
                <p style="margin: 0px">
                    <img src="img/head.png" style="width: 100px;height: 100px;border:1px solid #ccc;margin-left:21px;margin-right: 10px"/>
                    <span  id="upload-name"></span>
                    <span style="font-size: 12px;color: #888;position: absolute; top: 93px; left: 155px;">贡献于<span id="upload-time"></span></span>
                </p>
                <p style="padding-left: 21px">
                    <span style="font-weight: bold; font-family: 'Microsoft YaHei''微软雅黑';font-size:14px;">
                        文件名称：
                    </span>
                    <span id="r-name"></span>
                </p>
                <p style="padding-left: 21px">
                    <span style="font-weight: bold; font-family: 'Microsoft YaHei''微软雅黑';font-size:14px;">
                        文件大小：
                    </span>
                    <span id="r-size"></span>
                </p>
                <p style="padding-left: 21px">
                    <span style="font-weight: bold; font-family: 'Microsoft YaHei''微软雅黑';font-size:14px;">
                        下载次数：
                    </span>
                <span id="download-times"></span>
            </p>

            </div>
        </div>
    </div>
    <div id="foot"></div>
    <div id="img-view">
        <div class="arrow"></div>
        <img src="" width="100px" height="100px"/>
    </div>
</div>
<script type="text/javascript" src="js/uploadify/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript" src="js/commonUpload.js"></script>
<script type="text/javascript">
    user =<c:choose><c:when test="${online=='YES'}">"${user.id}"</c:when><c:otherwise>""</c:otherwise></c:choose>;
</script>
<script type="text/javascript" src="js/flexpaper_flash.js"></script>
<script type="text/javascript" src="js/flexpaper_flash_debug.js"></script>
<script type="text/javascript" src="js/base.js"></script>
</body>
</html>
