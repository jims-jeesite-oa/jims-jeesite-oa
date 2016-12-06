<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新闻审核官管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    //保存审核官姓名
                    $('#auditMan').val($('#auditIdName').val())
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
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
    <li><a href="#">新闻公告</a> <span class="divider">/</span></li>
    <li><a href="${ctx}/oa/oaAuditMan/">新闻审核官</a> <span class="divider">/</span></li>
    <li class="active">
        <shiro:hasPermission name="oa:oaAuditMan:edit">${not empty oaNews.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaAuditMan:edit">查看</shiro:lacksPermission>
    </li>
</ul>
<form:form id="inputForm" modelAttribute="oaAuditMan" action="${ctx}/oa/oaAuditMan/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="auditMan" id="auditMan"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核官姓名：</label>
        <div class="controls">
            <sys:treeselect id="auditId" name="auditId" value="${oaAuditMan.auditId}" labelName="" labelValue="${oaAuditMan.auditMan}"
                            title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">审核官职位：</label>
        <div class="controls">
            <form:input path="auditJob" htmlEscape="false" maxlength="80" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="oa:oaAuditMan:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>