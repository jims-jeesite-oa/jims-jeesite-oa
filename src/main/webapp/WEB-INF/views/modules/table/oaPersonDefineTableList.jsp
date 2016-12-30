<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义数据源管理</title>
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
        <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
        <li id="levelMenu2" class="active"></li>
    </ul>
	<form:form id="searchForm" modelAttribute="oaPersonDefineTable" action="${ctx}/table/oaPersonDefineTable/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>表名：</label>
				<form:input path="tableName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <shiro:hasPermission name="table:oaPersonDefineTable:edit">
                <li class="btns"><a href="${ctx}/table/oaPersonDefineTable/form" role="button" class="btn btn-primary">添加</a></li>
            </shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>表名</th>
				<th>注释</th>
				<th>创建者</th>
				<th>创建时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="table:oaPersonDefineTable:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaPersonDefineTable">
			<tr>
				<td><a href="${ctx}/table/oaPersonDefineTable/form?id=${oaPersonDefineTable.id}">
					${oaPersonDefineTable.tableName}
				</a></td>
				<td>
					${oaPersonDefineTable.tableComment}
				</td>
				<td>
					${fns:getUserById(oaPersonDefineTable.createBy.id).name}
				</td>
				<td>
					<fmt:formatDate value="${oaPersonDefineTable.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${oaPersonDefineTable.remarks}
				</td>
				<shiro:hasPermission name="table:oaPersonDefineTable:edit"><td>
    				<a href="${ctx}/table/oaPersonDefineTable/form?id=${oaPersonDefineTable.id}">修改</a>
					<a href="${ctx}/table/oaPersonDefineTable/form?updateType=reset&id=${oaPersonDefineTable.id}" onclick="return confirmx('字段重置将删除所有数据表内数据，确定要重置吗？', this.href)">字段重置</a>
					<a href="${ctx}/table/oaPersonDefineTable/delete?id=${oaPersonDefineTable.id}" onclick="return confirmx('确认要删除该自定义数据源吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>