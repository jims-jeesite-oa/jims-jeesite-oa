<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>邮件信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/mailInfo/">邮件信息列表</a></li>
		<li class="active"><a href="${ctx}/oa/mailInfo/info?id=${mailInfo.id}">邮件信息<shiro:hasPermission name="oa:mailInfo:edit">${not empty mailInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:mailInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="mailInfo" action="${ctx}/oa/mailInfo/send" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">收件人：</label>
            <div class="controls">
                <sys:treeselect id="receiverId" name="receiverId" value="${mailInfo.receiverId}" labelName="" labelValue="${mailInfo.receiverId}"
                                title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" checked="true" notAllowSelectParent="true"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">抄送人：</label>
            <div class="controls">
                <form:input path="ccId" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">邮件主题：</label>
			<div class="controls">
				<form:input path="theme" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">附件：</label>
            <div class="controls">
                <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <sys:ckfinder input="files" type="files" uploadPath="/oa/mailInfo" selectMultiple="true"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">邮件正文：</label>
			<div class="controls">
                <form:textarea id="content" htmlEscape="true" path="content" class="input-xxlarge"/>
                <sys:ckeditor replace="content" height="200"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:mailInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="发 送"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>