<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>tt</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        body{
            background-color: #EDEFF0;
            padding-right: 20px;
        }
        #main {
            width: 100%;
            min-width: 954px;
        }
        #main ul {
            list-style: none;
            margin: 0px;
        }
        #main li {
            float: left;
            margin-left: 25px;
            margin-top: 25px;
        }
        #main li>div {
            height: 330px;
        }
        .content {
            width: 100%;
            height: 260px;
            -webkit-box-shadow:0 0 7px #AAA;
            -moz-box-shadow:0 0 7px #AAA;
            box-shadow:0 0 7px #AAA;
            border-radius:2px;
            -moz-border-radius:2px; /* 老的 Firefox */
            background-color: #fff;
            padding: 10px 0px 10px 0px;
        }
        table {
            width: 100%;
        }
        table td {
            height: 35px;
            padding-left: 15px;
            font-size: 13px;
            font-family:sans-serif, 黑体;
            font-weight:500;
            color: #606060;
        }
        table td i{
            margin-right: 5px;
        }
        .title{
            padding: 1px 10px;
            border-bottom: 3px solid red;
            font-size: 18px;
            font-weight: 600;
            cursor: pointer;
        }
        .moreBtn {
            margin-right: 10px;
            float: right;
            cursor: pointer;
        }
        hr {
            width:100%;
            margin-top:0px;
            height:1px;
            border:0px;
            background-color:#D5D5D5;color:#D5D5D5;
        }
        .dateTd {
            width: 80px;
        }
        a {
            color: #606060;
        }
    </style>

</head>
<body>
    <div id="main">
        <ul>
            <li>
                <div>
                    <div>
                        <span class="title">新 闻</span>
                        <span class="moreBtn"><a href="${ctx}/oa/oaNews/more">More ></a></span>
                        <hr/>
                    </div>
                    <div class="content">
                        <table class="info">
                            <c:forEach items="${fns:getAllNews()}" var="news"  begin="0" end="6">
                                <tr>
                                    <td>
                                        <i class="icon-volume-up"></i>
                                        <a href="${ctx}/oa/oaNews/getAuditNews?id=${news.id}">
                                            ${fns:abbr(news.title,45)}
                                            <c:if test="${news.isTopic eq '1'}">
                                                <i class="icon-arrow-up"></i>
                                            </c:if>
                                        </a>
                                    </td>
                                    <td class="dateTd"><fmt:formatDate value="${news.createDate}" pattern="yyyy-MM-dd"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </li>
            <li>
                <div >
                    <div>
                        <span class="title">内部通知</span>
                        <span class="moreBtn"><a href="${ctx}/oa/oaNotify/self">More ></a></span>
                        <hr/>
                    </div>
                    <div class="content">
                        <table class="info">
                            <c:forEach items="${fno:getMyNotify()}" var="notifys"  begin="0" end="6">
                                <tr>
                                    <td>
                                        <i class="icon-volume-up"></i>
                                        <a href="${ctx}/oa/oaNotify/view?id=${notifys.id}">
                                            ${fns:abbr(notifys.title,41)}
                                            <c:if test="${notifys.readFlag == '0'}">
                                                （未读）
                                            </c:if>
                                        </a>
                                    </td>
                                    <td class="dateTd"><fmt:formatDate value="${notifys.createDate}" pattern="yyyy-MM-dd"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </li>
            <li>
                <div >
                    <div>
                        <span class="title">日程安排</span>
                        <span class="moreBtn"><a href="${ctx}/oa/oaSchedule">More ></a></span>
                        <hr/>
                    </div>
                    <div class="content">
                        <table class="info">
                            <c:forEach items="${fno:getSchedules('0')}" var="schedules"  begin="0" end="6">
                                <tr>
                                    <td>
                                        <i class="icon-calendar"></i>
                                        <a href="${ctx}/oa/oaSchedule/form?id=${schedules.id}">
                                            ${fns:abbr(schedules.content,45)}
                                        </a>
                                    </td>
                                    <td class="dateTd"><fmt:formatDate value="${schedules.scheduleDate}" pattern="yyyy-MM-dd"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </li>
            <li>
                <div >
                    <div>
                        <span class="title">待办流程</span>
                        <span class="moreBtn">More ></span>
                        <hr/>
                    </div>
                    <div class="content">
                        <table class="info">
                            <c:forEach items="${fns:getAuditNews()}" var="news" begin="0" end="6">
                                <tr>
                                    <td><i class="icon-cogs"></i>
                                        <a href="${ctx}/oa/oaNews/getAuditNews?id=${news.id}">新闻审核：${fns:abbr(news.title,40)}</a>
                                    </td>
                                    <td class="dateTd"><fmt:formatDate value="${news.createDate}" pattern="yyyy-MM-dd"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <script>
        var mainWidth = 0
        $(window).resize(function () {
            changeCss()
        });
        changeCss()
        function changeCss(){
            if($('#main').width() != mainWidth){
                mainWidth = $('#main').width()
                $('#main li').width(mainWidth / 2 - 40)
            }
        }
    </script>
</body>

</html>