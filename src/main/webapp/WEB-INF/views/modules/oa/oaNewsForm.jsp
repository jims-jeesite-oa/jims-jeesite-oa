<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新闻公告管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    if (CKEDITOR.instances.content.getData()==""){
                        top.$.jBox.tip('请填写新闻内容','warning');
                    }else{
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
        <li><a href="${ctx}/oa/oaNews/">新闻公告</a> <span class="divider">/</span></li>
        <li class="active">
            <shiro:hasPermission name="oa:oaNews:edit">${not empty oaNews.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaNews:edit">查看</shiro:lacksPermission>
        </li>
    </ul>
	<form:form id="inputForm" modelAttribute="oaNews" action="${ctx}/oa/oaNews/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
        <form:hidden path="auditFlag"/>
		<sys:message content="${message}"/>
        <c:if test="${oaNews.auditFlag eq '1'}">
            <div class="control-group">
                <label class="control-label">&nbsp;</label>
                <div class="controls" style="color: red;font-size: 20px;">
                    已审核，不能再次修改。
                </div>
            </div>
        </c:if>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">新闻审核官：</label>
            <div class="controls">
                <form:select path="auditMan" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fno:getAuditManAllList()}" itemLabel="auditMan" itemValue="auditId" htmlEscape="false"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">附件：</label>
            <div class="controls">
                <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <sys:ckfinder input="files" type="files" uploadPath="/oa/oaNews" selectMultiple="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">是否置顶:</label>
            <div class="controls">
                <form:radiobuttons path="isTopic"  items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" value="${isTopic}"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">新闻内容</label>
            <div class="controls">
                <form:textarea id="content" htmlEscape="true" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
                <sys:ckeditor replace="content" uploadPath="/oa/news" />
            </div>
        </div>
		<div class="form-actions">
            <c:if test="${oaNews.auditFlag ne '1'}">
			    <shiro:hasPermission name="oa:oaNews:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>