<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新闻审核官管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
    <ul class="breadcrumb">
        <li><a href="#">新闻公告</a> <span class="divider">/</span></li>
        <li class="active">新闻审核官</li>
    </ul>
	<form:form id="searchForm" modelAttribute="oaAuditMan" action="${ctx}/oa/oaAuditMan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>审核官职位：</label>
				<form:input path="auditJob" htmlEscape="false" maxlength="80" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><a href="${ctx}/oa/oaAuditMan/form" role="button" class="btn btn-primary">添加</a></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
            <tr>
                <th>审核官姓名</th>
                <th>审核官职位</th>
                <shiro:hasPermission name="oa:oaAuditMan:edit">
                    <th>操作</th></shiro:hasPermission>
            </tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaAuditMan">
			<tr>
                <td>
                    ${oaAuditMan.auditMan}
                </td>
                <td>
                    ${oaAuditMan.auditJob}
                </td>
				<shiro:hasPermission name="oa:oaAuditMan:edit"><td>
    				<a href="${ctx}/oa/oaAuditMan/form?id=${oaAuditMan.id}">修改</a>
					<a href="${ctx}/oa/oaAuditMan/delete?id=${oaAuditMan.id}" onclick="return confirmx('确认要删除该新闻审核官吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>