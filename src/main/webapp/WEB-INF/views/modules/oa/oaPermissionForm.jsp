<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

            $("#inputForm").validate({
                submitHandler: function (form) {
                   // loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>


</head>
<body>
<ul class="nav nav-tabs">
    <%--<li><a href="${ctx}/oa/oaSummaryPermission/formId">评阅日总结</a></li>--%>
    <li class="active"><a href="${ctx}/oa/oaSummaryDay/formWeek">评阅周总结</a></li>
</ul>
<br/>
<form:form id="searchForm" modelAttribute="oaSummaryWeek" action="${ctx}/oa/oaSummaryPermission/formId" method="post" class="breadcrumb form-search">
    <ul class="ul-form">
        <li>    　　　　　<label>被评阅人：</label>
            <form:select path="login" class="input-medium">
                <form:option value="" label=""/>    <%--htmlEscape="false"--%>
                <form:options items="${fns:getAllPermission()}" itemLabel="name" itemValue="id" htmlEscape="true" />
            </form:select>
        </li>
        <%--<li><label>评阅日期：</label>
            <input name="sumDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${oaSummaryWeek.sumDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>--%>
        <li class="btns"><input id="btnSeaSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<form:form id="inputForm" modelAttribute="oaSummaryWeek" action="${ctx}/oa/oaSummaryPermission/saveWeek" method="post"
           class="form-horizontal">
  <%--  <form:hidden path="oaVos" value="${oaSummaryWeek.oaVos}"/>--%>
    <form:hidden path="id" value="${oaSummaryWeek.id}"/>
    <form:hidden path="loginId" value="${oaSummaryWeek.loginId}"/>
    <table id="contentTable" class="table  table-bordered table-condensed">
        <tr>
            <td colspan="2" style="width: 250px;text-align: center;">下周工作目标</td>
            <td style="width: 900px">
                <form:input path="nextPlanTitle" htmlEscape="false" maxlength="1000" class="input-xlarge "  readonly="true"
                            cssStyle="width: 800px"  value="${oaSummaryWeek.nextPlanTitle}"/>
            </td>
        </tr>

        <tr>
            <td colspan="2" style="width: 250px;text-align: center;">下周工作计划综述</td>
            <td style="width: 900px">
                <form:textarea path="nextPlanContent" htmlEscape="false" rows="2" maxlength="2000"       readonly="true"
                               class="input-xxlarge " cssStyle="width:800px;"  value="${oaSummaryWeek.nextPlanContent}"/>
            </td>
        </tr>

    </table>
    <div style="float:left;">
        <table id="monitor" class="table  table-bordered table-condensed"  STYLE="float: right">
            <c:forEach items="${oaSummaryWeek.oaVos}" var="oaSummaryDay">
                <tr >
                    <td width="150px" class="date" id="sunday" rowspan="2"> ${fns:abbr(oaSummaryDay.date,50)}</td>
                    <td style=" text-align: center;" width="70px">任务完成</td>

                    <td width="910px">${oaSummaryDay.content}</td>

                </tr>
                <tr>
                    <td style=" text-align: center;">工作总结</td>
                    <td>${oaSummaryDay.status}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <table class="table  table-bordered table-condensed">
        <tr>
            <td colspan="2" style="width: 250px;text-align: center;">本周工作总结</td>
            <td style="width: 900px">
                <form:textarea path="content" htmlEscape="false" rows="2" maxlength="2000" class="input-xxlarge "     readonly="true"
                               cssStyle="width:800px;" value="${oaSummaryWeek.content}"/>
            </td>
        </tr>

        <tr>
            <td colspan="2" style="width: 250px;text-align: center;">评阅意见</td>
            <td style="width: 900px">
                <form:input path="evaluate" htmlEscape="false" rows="1" maxlength="2000" class="input-xxlarge "
                            cssStyle="width:800px;" value="${oaSummaryWeek.evaluate} " />
            </td>
        </tr>
    </table>


    <div align="center">
        <input id="btnSubmit"  class="btn btn-primary"  type="submit" value="保 存"/>&nbsp;
    </div>
</form:form>
</body>
</html>