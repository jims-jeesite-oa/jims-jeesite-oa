<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>编辑器设计表单管理</title>
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
		<li><a href="${ctx}/form/oaFormMaster/">编辑器设计表单列表</a></li>
		<li class="active"><a href="${ctx}/form/oaFormMaster/form?id=${oaFormMaster.id}">编辑器设计表单<shiro:hasPermission name="form:oaFormMaster:edit">${not empty oaFormMaster.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="form:oaFormMaster:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaFormMaster" action="${ctx}/form/oaFormMaster/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">医院机构Id：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${oaFormMaster.office.id}" labelName="office.name" labelValue="${oaFormMaster.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xlarge "/>
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
				<form:input path="tableName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单分类：</label>
			<div class="controls">
				<form:radiobuttons path="formType" items="${fns:getDictList('form_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布状态：</label>
			<div class="controls">
				<form:radiobuttons path="publishStatus" items="${fns:getDictList('publish_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数据模板：</label>
			<div class="controls">
				<form:radiobuttons path="dataTemplete" items="${fns:getDictList('data_templete')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设计类型：</label>
			<div class="controls">
				<form:radiobuttons path="designType" items="${fns:getDictList('design_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
                <form:textarea id="content" htmlEscape="true"  rows="4" maxlength="200" class="input-xxlarge" path="content"/>
                <sys:ckeditor replace="content" />
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