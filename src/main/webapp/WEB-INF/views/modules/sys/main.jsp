<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>栏目列表</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
    <style type="text/css">
        #content{
            width: 80%;
            margin: 0 auto;
        }
        .t{
            width: 99.3%;
        }
        .w{
            width: 33%;
            float: left;
        }
        .accordion-inner{padding:2px;}
    </style>
</head>
<body>
<div id="content">
    <div class="accordion-group t">
        <div class="accordion-heading">
            <a class="accordion-toggle">新闻公告</a>
        </div>
        <div class="accordion-body">
            <div class="accordion-inner">
                <ul>
                    <c:forEach items="${page.list}" var="oaNotify">
                        <li>
                            a
                        </li>
                    </c:forEach>
                    <li><a href="javascript:cookie('tabmode','${tabmode eq '1' ? '0' : '1'}');location=location.href">${tabmode eq '1' ? '关闭' : '开启'}页签模式</a></li>
                    <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
                    <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="accordion-group w">
        <div class="accordion-heading">
            <a class="accordion-toggle">内部通知</a>
        </div>
        <div class="accordion-body">
            <div class="accordion-inner">
                <ul>
                    <li>1阿尔去爱人啊啊发</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="accordion-group w">
        <div class="accordion-heading">
            <a class="accordion-toggle">日程安排</a>
        </div>
        <div class="accordion-body">
            <div class="accordion-inner">
                <ul>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="accordion-group w">
        <div class="accordion-heading">
            <a class="accordion-toggle">待办流程</a>
        </div>
        <div class="accordion-body">
            <div class="accordion-inner">
                <ul>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>