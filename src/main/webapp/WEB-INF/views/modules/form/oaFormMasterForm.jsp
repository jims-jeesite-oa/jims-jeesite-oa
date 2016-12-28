<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>编辑器设计表单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
                rules: {
                    formNo: {remote: "${ctx}/form/oaFormMaster/checkFormNo?oldFormNo=" + encodeURIComponent('${oaFormMaster.formNo}')}
                },
                messages: {
                    formNo: {remote: "表单编号已存在"}
                },
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
    <ul class="breadcrumb">
        <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
        <li id="levelMenu2"><a href="#"></a> <span class="divider">/</span></li>
        <li class="active">
            <shiro:hasPermission name="form:oaFormMaster:edit">${not empty oaFormMaster.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="form:oaFormMaster:edit">查看</shiro:lacksPermission>
        </li>
    </ul>
	<form:form id="inputForm" modelAttribute="oaFormMaster" action="${ctx}/form/oaFormMaster/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">表单编号：</label>
            <div class="controls">
                <form:input id="formNo" path="formNo" htmlEscape="false" maxlength="50" class="input-xlarge required abc" cssStyle="text-transform:uppercase" onkeyup="this.value=this.value.toUpperCase()"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">表单标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单别名：</label>
			<div class="controls">
				<form:input path="alias" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对应表：</label>
			<div class="controls">
                <form:select path="tableName" class="input-xlarge required">
                    <form:options items="${fns:getSelfTable()}" itemLabel="tableComment" itemValue="tableName" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
                <form:textarea id="content" htmlEscape="true"  rows="4" maxlength="200" class="input-xxlarge" path="content"/>
                <sys:ckeditor replace="content" toolbar="simple"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单描述：</label>
			<div class="controls">
				<form:input path="formDesc" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="form:oaFormMaster:edit">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>