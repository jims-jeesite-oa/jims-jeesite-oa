<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>工作日志管理</title>
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
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2" class="active"></li>
</ul>
<form:form id="inputForm" modelAttribute="oaSummaryWeek" action="${ctx}/oa/oaSummaryDay/saveWeek" method="post"
           class="form-horizontal">
    <%--  <form:hidden path="oaVos" value="${oaSummaryWeek.oaVos}"/>--%>
    <form:hidden path="id" value="${oaSummaryWeek.id}"/>
    <form:hidden path="weekOfYear" value="${oaSummaryWeek.weekOfYear}"/>
    第 <font color="red">${oaSummaryWeek.weekOfYear} </font> 周
    <table id="contentTable" class="table  table-bordered table-condensed">
        <tr>
            <td colspan="2" style="width: 250px;text-align: center;">下周工作目标</td>
            <td style="width: 900px">
                <form:input path="nextPlanTitle" htmlEscape="false" maxlength="1000" class="input-xlarge "
                            cssStyle="width: 800px" value="${oaSummaryWeek.nextPlanTitle}"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="width: 250px;text-align: center;">下周工作计划综述</td>
            <td style="width: 900px">
                <form:textarea path="nextPlanContent" htmlEscape="false" rows="2" maxlength="2000"
                               class="input-xxlarge " cssStyle="width:800px;" value="${oaSummaryWeek.nextPlanContent}"/>
            </td>
        </tr>

    </table>
    <div style="float:left;">
        <table id="monitor" class="table  table-bordered table-condensed" STYLE="float: right">
            <c:forEach items="${oaSummaryWeek.oaVos}" var="oaSummaryDay">
                <tr>
                    <td width="150px" class="date" id="sunday" rowspan="3"> ${fns:abbr(oaSummaryDay.date,50)}</td>
                    <td style=" text-align: center;" width="70px">任务完成</td>

                    <td width="910px">${oaSummaryDay.content}</td>

                </tr>
                <tr>
                    <td style=" text-align: center;">工作总结</td>
                    <td>${oaSummaryDay.status}</td>
                </tr>
                <tr>
                    <td style=" text-align: center;">同事评阅</td>
                    <td>${oaSummaryDay.appraise}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <table class="table  table-bordered table-condensed">
        <tr>
            <td colspan="2" style="width: 250px;text-align: center;">本周工作总结</td>
            <td style="width: 900px">
                <form:textarea path="content" htmlEscape="false" rows="2" maxlength="2000" class="input-xxlarge "
                               cssStyle="width:800px;" value="${oaSummaryWeek.content}"/>
            </td>
        </tr>

        <tr>
            <td colspan="2" style="width: 250px;text-align: center;">评阅意见</td>
            <td style="width: 900px">
                <form:input path="evaluateContent" htmlEscape="false" rows="1" maxlength="2000" class="input-xxlarge "
                            cssStyle="width:800px;" readonly="true" value="${oaSummaryWeek.evaluateContent}"/>
            </td>
        </tr>
    </table>


    <div align="center">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        <input id="okBtnSubmit"
               onclick="location='${ctx}/oa/oaSummaryDay/lackWeek?flag=1&weekOfYear=${oaSummaryWeek.weekOfYear}'"
               class="btn btn-primary" type="button" value="上一周"/>
        <input id="akBtnSubmit"
               onclick="location='${ctx}/oa/oaSummaryDay/lackWeek?flag=3&weekOfYear=${oaSummaryWeek.weekOfYear}'"
               class="btn btn-primary" type="button" value="本  周"/>
        <input id="blackSubmit"
               onclick="location='${ctx}/oa/oaSummaryDay/lackWeek?flag=2&weekOfYear=${oaSummaryWeek.weekOfYear}'"
               class="btn btn-warning" type="button" value="下一周"/>
    </div>
</form:form>
</body>
</html>