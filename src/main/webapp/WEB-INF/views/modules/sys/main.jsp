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
                    <c:forEach items="${fns:getAllNews()}" var="news">
                        <li>
                            <a href="${ctx}/oa/oaNews/getAuditNews?id=${news.id}">${news.title}
                                （<fmt:formatDate value="${news.createDate}" pattern="yyyy-MM-dd"/>）
                                <c:if test="${news.isTopic eq '1'}">
                                    <i class="icon-arrow-up"></i>
                                </c:if>
                            </a>
                        </li>
                    </c:forEach>
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
                    <li>
                        &nbsp;
                    </li>
                    <c:forEach items="${fns:getAuditNews()}" var="news">
                        <li>
                            <a href="${ctx}/oa/oaNews/getAuditNews?id=${news.id}">${news.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>