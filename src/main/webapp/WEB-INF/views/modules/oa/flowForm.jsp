<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
	<form id="inputForm" action="${ctx}/oa/flow/save" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${flow.id}"/>
		<input type="hidden" name="act.taskId" value="${flow.act.taskId}"/>
		<input type="hidden" name="act.taskName" value="${flow.act.taskName}"/>
		<input type="hidden" name="act.taskDefKey" value="${flow.act.taskDefKey}"/>
		<input type="hidden" name="act.procInsId" value="${flow.act.procInsId}"/>
		<input type="hidden" name="act.procDefId" value="${flow.act.procDefId}"/>
		<input type="hidden" id="flag" name="act.flag" value="${flow.act.flag}"/>
        <input type="hidden" name="tableName" value="${flow.tableName}"/>
		<sys:message content="${message}"/>
		<fieldset>
			<legend>审批申请</legend>
			<table class="table-form">
				<tr>
					<td class="tit" rowspan="3">调整原因</td>
					<td class="tit">薪酬档级</td>
					<td><input name="col1" type="text"/></td>
					<td class="tit" rowspan="3">拟调整标准</td>
					<td class="tit">薪酬档级</td>
					<td><input name="col2" type="text"/></td>
				</tr>
                <tr>
                    <td class="tit" rowspan="3">调整原因</td>
                    <td class="tit">薪酬档级</td>
                    <td><input name="col3" type="text"/></td>
                </tr>
			</table>
		</fieldset>
		<div class="form-actions">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请" onclick="$('#flag').val('yes')"/>&nbsp;
            <c:if test="${not empty flow.id}">
                <input id="btnSubmit2" class="btn btn-inverse" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
            </c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty flow.id}">
			<act:histoicFlow procInsId="${act.procInsId}" />
		</c:if>
	</form>
</body>
</html>
