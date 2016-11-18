<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字段显示方式管理</title>
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/form/oaColumnShowStyle/">字段显示方式列表</a></li>
		<shiro:hasPermission name="form:oaColumnShowStyle:edit"><li><a href="${ctx}/form/oaColumnShowStyle/form">字段显示方式添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaColumnShowStyle" action="${ctx}/form/oaColumnShowStyle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属机构ID：</label>
				<sys:treeselect id="officeId" name="officeId" value="${oaColumnShowStyle.officeId}" labelName="" labelValue="${oaColumnShowStyle.officeId}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>是否是通用：</label>
				<form:select path="isCommon" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_master')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>表名：</label>
				<form:input path="tableName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>字段名：</label>
				<form:input path="columnName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>显示方式：</label>
				<form:select path="showType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('show_style')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="form:oaColumnShowStyle:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaColumnShowStyle">
			<tr>
				<td><a href="${ctx}/form/oaColumnShowStyle/form?id=${oaColumnShowStyle.id}">
					<fmt:formatDate value="${oaColumnShowStyle.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${oaColumnShowStyle.remarks}
				</td>
				<shiro:hasPermission name="form:oaColumnShowStyle:edit"><td>
    				<a href="${ctx}/form/oaColumnShowStyle/form?id=${oaColumnShowStyle.id}">修改</a>
					<a href="${ctx}/form/oaColumnShowStyle/delete?id=${oaColumnShowStyle.id}" onclick="return confirmx('确认要删除该字段显示方式吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>