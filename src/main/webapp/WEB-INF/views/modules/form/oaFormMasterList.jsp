<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>编辑器设计表单管理</title>
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
	<form:form id="searchForm" modelAttribute="oaFormMaster" action="${ctx}/form/oaFormMaster/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>表单标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>对应表：</label>
				<form:input path="tableName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>表单分类：</label>
				<form:radiobuttons path="formType" items="${fns:getDictList('form_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <shiro:hasPermission name="form:oaFormMaster:edit">
                <li class="btns"><a href="${ctx}/form/oaFormMaster/form" role="button" class="btn btn-primary">添加</a></li>
            </shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>医院机构Id</th>
				<th>表单标题</th>
				<th>表单别名</th>
				<th>对应表</th>
				<th>表单分类</th>
				<th>发布状态</th>
				<th>数据模板</th>
				<th>设计类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="form:oaFormMaster:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaFormMaster">
			<tr>
				<td><a href="${ctx}/form/oaFormMaster/form?id=${oaFormMaster.id}">
					${oaFormMaster.office.name}
				</a></td>
				<td>
					${oaFormMaster.title}
				</td>
				<td>
					${oaFormMaster.alias}
				</td>
				<td>
					${oaFormMaster.tableName}
				</td>
				<td>
					${fns:getDictLabel(oaFormMaster.formType, 'form_type', '')}
				</td>
				<td>
					${fns:getDictLabel(oaFormMaster.publishStatus, 'publish_status', '')}
				</td>
				<td>
					${fns:getDictLabel(oaFormMaster.dataTemplete, 'data_templete', '')}
				</td>
				<td>
					${fns:getDictLabel(oaFormMaster.designType, 'design_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${oaFormMaster.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${oaFormMaster.remarks}
				</td>
				<shiro:hasPermission name="form:oaFormMaster:edit"><td>
    				<a href="${ctx}/form/oaFormMaster/form?id=${oaFormMaster.id}">修改</a>
    				<a href="${ctx}/form/oaFormMaster/view?id=${oaFormMaster.id}">预览</a>
					<a href="${ctx}/form/oaFormMaster/delete?id=${oaFormMaster.id}" onclick="return confirmx('确认要删除该编辑器设计表单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>