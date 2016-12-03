<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="blank"/>
    <link href="${ctxStatic}/common/icons.css" type="text/css" rel="stylesheet" />

    <style type="text/css">
        body {
            background-color: #EDEFF0;
            margin: 0;
            padding: 0;
        }
        .mask {
            position: absolute; filter: alpha(opacity=60); background-color: #777;
            z-index: 1000; left: 0px;
            opacity:0.5; -moz-opacity:0.5;
            display: none;
        }
        #header {margin:0;position:static;} #header li {font-size:14px;_font-size:12px;}
        #header .brand {font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:26px;padding-left:33px;}
        #userControl>li>a{/*color:#fff;*/text-shadow:none;}
        #userControl>li>a:hover, #user #userControl>li.open>a{background:transparent;}
        #menu {
            width: 100%;
            background-color: #FFFFFF;
        }
        #menu,#menu > div{
            height: 90px;
        }
        #userLogo {
            float: left;
            width: 200px;
            border-right: 3px solid #EDEFF0;
        }
        #userLogo img{
            width: 70px;
            height: 70px;
            margin-top: 10px;
            margin-left: 15px;
            float: left;
        }
        #loginName{
            font-size: 14px;
        }
        #userLogo div{
            position: absolute;
            left: 95px;
        }
        #userLogo ul {
            padding-top: 15px;
            font-family:"微软雅黑","黑体","宋体";
            font-weight: 100;
        }
        #menu ul,#left ul {
            list-style:none;
            margin: 0;
        }
        #menu #topMenus {
            position: absolute;
            left: 200px;
            width: 1000px;
        }
        #topMenus li {
            float:left;
            width:65px;
            cursor: pointer;
            padding: 10px 10px;
        }
        #topMenus li a,#left li a{
            font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;
            text-decoration:none;
            color: #555555;
        }
        #topMenus li a span{
            margin: 0 auto;
            display: block;
            text-align:center;
        }
        #moreMenu {
            width: 100%;
            height: 200px;
            display: none;
        }
        #moreMenu {
            background-color: #FFFFFF;
            z-index: 1002;
            filter: alpha(opacity=100);
            opacity:1;
            height: 400px;
        }
        #left,#right{
            position: absolute;
            bottom: 0px;
        }
        #left {
            left: 0px;
            overflow-x:hidden;
            overflow-y:auto;
            width: 200px;
            background-color: #FFFFFF;
        }
        #right {
            right: 0px;
        }

        #left ul {
            margin-top: 20px;
        }
        #left li {
            height: 27px;
            padding-left: 30px;
            padding-top: 5px;
            font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;
            font-size: 14px;
            cursor: pointer;
        }
        #left .hover {
            background-color: #CBDBF7;
        }
        #left .active {
            background-color: #30A5FF;
        }
        #mainFrame {
            border: 0px;
        }
    </style>
</head>
<body>
<%--遮罩层--%>
<div id="mask" class="mask" onclick="hideMask()"></div>
<div id="main">
    <div id="header" class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="brand"><span id="productName">${fns:getConfig('productName')}</span></div>
            <ul id="userControl" class="nav pull-right">
                <li id="mianPage"><a href="${ctx}/sys/main/form" target="mainFrame" title="首页"><i class="icon-home"></i></a></li>
                <li id="themeSwitch" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"><i class="icon-th-large"></i></a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
                    </ul>
                    <!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
                </li>
                <li id="userInfo" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">您好, ${fns:getUser().name}&nbsp;<span id="notifyNum" class="label label-info hide"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
                        <li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
                        <li><a href="${ctx}/oa/oaNotify/self" target="mainFrame"><i class="icon-bell"></i>&nbsp;  我的通知 <span id="notifyNum2" class="label label-info hide"></span></a></li>
                    </ul>
                </li>
                <li><a href="${ctx}/logout" title="退出登录">退出</a></li>
                <li>&nbsp;</li>
            </ul>
        </div>
    </div>

    <div id="menu">
        <div id="userLogo">
            <c:set var="user" value="${fns:getUser()}"/>
            <img src="${user.photo}" class="img-circle">
            <div>
                <ul>
                    <li id="loginName">${user.name}</li>
                    <li>
                        <c:out value="${user.office.name.length() > 7 ? user.office.name.substring(0,7).concat('...') : user.office.name}" />&nbsp;</li>
                    <li>
                        <c:out value="${user.roleNames.length() > 7 ? user.roleNames.substring(0,7).concat('...') : user.roleNames}" /></li>
                </ul>
            </div>
        </div>
        <div id="topMenus">
            <ul>
                <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                    <c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
                        <li>
                            <a class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" data-id="${menu.id}">
                                <span class="icons-${menu.icon}"></span>
                                <span>${menu.name}</span></a>
                        </li>
                    </c:if>
                </c:forEach>
                <li>
                    <a class="menu moreMenu" href="#">
                        <span class="icons-more"></span> </a>
                </li>
            </ul>
        </div>
    </div>

    <div id="moreMenu" class="mask">
        <div id="myCarousel" class="carousel slide" style="width: 500px;margin:0 auto;">
            <!-- 轮播（Carousel）项目 -->
            <div class="carousel-inner">
                <div class="item active">
                    <img src="/static/images/icons/5.png" alt="First slide">
                </div>
                <div class="item">
                    <img src="/static/images/icons/5.png" alt="Second slide">
                </div>
                <div class="item">
                    <img src="/static/images/icons/5.png" alt="Third slide">
                </div>
            </div>
        </div>
        <a class="carousel-control left" href="#myCarousel"
           data-slide="prev" style="float: right">&lsaquo;</a>
        <a class="carousel-control right" href="#myCarousel"
           data-slide="next">&rsaquo;</a>
    </div>

    <div>
        <div id="left">
        </div>
        <div id="right">
            <iframe id="mainFrame" name="mainFrame" src="" style="" scrolling="yes" frameborder="yes" width="100%"></iframe>
        </div>
    </div>
</div>
<div id="myModal" class="modal hide fade modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">添加</h3>
    </div>
    <div class="modal-body">

    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="btn btn-primary">保存</button>
    </div>
</div>
<script type="text/javascript">
    function showModal(){
        $('#myModal').modal('toggle')
    }
    function initModal(data){
        $('#myModal .modal-body').html(data)
        showModal()
    }

    var menuObj = $("#menu");
    var headerObj = $("#header");
    var frameObj = $("#left, #right, #right iframe");
    $('.mask').css('top',headerObj.height() + menuObj.height() + 3)
    $(window).resize(function () {
        wSize()
    });
    wSize()
    function wSize(){
        var strs = getWindowSize().toString().split(",");
        $('html').css({overflow: 'hidden'})
        $('#right').css('width',strs[1] - $('#left').width() - 3)
        frameObj.css('height',strs[0] - headerObj.height() - menuObj.height() - 3);

        $('#myModal').css({'left' : (strs[1] - $('#myModal').width()) / 2})
    }
    function getWindowSize() {
        return ["Height", "Width"].map(function (a) {
            return window["inner" + a] || document.compatMode === "CSS1Compat" && document.documentElement["client" + a] || document.body["client" + a]
        })
    }

    //显示遮罩层
    function showMask(){
        $("#mask").css("height",$('#left').height() + 5);
        $("#mask").css("width",$(document).width());
        $("#mask").show();
    }
    //隐藏遮罩层
    function hideMask(){
        $("#mask").hide();
        $("#moreMenu").slideUp("fast");
    }


    $("#menu #topMenus a.menu").click(function(){
        if($(this).hasClass('moreMenu')){
            if($('#mask').css('display') == 'none'){
                $("#moreMenu").slideDown("fast");
                showMask()
            } else {
                hideMask()
            }
            return false
        }
        if($('#mask').css('display') == 'block'){
            hideMask()
        }
        $.get($(this).attr("data-href"), function(data){
            if (data.indexOf("id=\"loginForm\"") != -1){
                alert('未登录或登录超时。请重新登录，谢谢！');
                top.location = "${ctx}";
                return false;
            }
            $("#left").empty();
            $("#left").append(data);
            $('#left .menu2').css({fontSize: '12px',paddingLeft:'48px'})

            $('#left a').click(function(e){
                var thisLi = $(this).parent('li')
                if(thisLi.hasClass('menu1') && thisLi.next().hasClass('parent-' + thisLi.attr('data-id'))){
                    thisLi.next().click()
                    return false
                }
                $('#left li').removeClass("active")
                $(this).parent('li').addClass("active")
                $('#left a').css({color : '#555'})
                $(this).css({color : '#fff'})
                e.stopPropagation()
            })
            $('#left li').mouseover(function(){
                $('#left li').removeClass("hover")
                $(this).addClass("hover")
            })
            $('#left li').mouseout(function(){
                $(this).removeClass("hover")
            })
            $('#left li').click(function(e){
                $('#left li').removeClass("active")
                $(this).addClass("active")
                $('a',this)[0].click()
            })
            $('#left li:eq(0)').click()
        });
    });

    $("#menu #topMenus a.menu:eq(0)").click()
</script>
</body>
</html>