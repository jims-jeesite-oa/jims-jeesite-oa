<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字段显示方式管理</title>
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
		<li><a href="${ctx}/form/oaColumnShowStyle/">字段显示方式列表</a></li>
		<li class="active"><a href="${ctx}/form/oaColumnShowStyle/form?id=${oaColumnShowStyle.id}">字段显示方式<shiro:hasPermission name="form:oaColumnShowStyle:edit">${not empty oaColumnShowStyle.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="form:oaColumnShowStyle:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaColumnShowStyle" action="${ctx}/form/oaColumnShowStyle/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属机构ID：</label>
			<div class="controls">
				<sys:treeselect id="officeId" name="officeId" value="${oaColumnShowStyle.officeId}" labelName="" labelValue="${oaColumnShowStyle.officeId}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否是通用：</label>
			<div class="controls">
				<form:select path="isCommon" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_master')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表名：</label>
			<div class="controls">
				<form:input path="tableName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单名：</label>
			<div class="controls">
				<form:input path="formName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段名：</label>
			<div class="controls">
				<form:input path="columnName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段类型：</label>
			<div class="controls">
				<form:select path="columnType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('column_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">显示方式：</label>
			<div class="controls">
				<form:select path="showType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('show_style')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="form:oaColumnShowStyle:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>