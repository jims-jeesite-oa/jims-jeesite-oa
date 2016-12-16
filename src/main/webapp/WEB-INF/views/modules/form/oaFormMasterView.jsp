<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>编辑器设计表单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
<ul class="breadcrumb">
    <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
    <li id="levelMenu2"><a href="#"></a> <span class="divider">/</span></li>
    <li class="active">预览</li>
</ul>
    <form:form id="inputForm" modelAttribute="oaFormMaster" action="${ctx}/form/oaFormMaster/save" method="post" class="form-horizontal">
	    ${oaFormMaster.content}
    </form:form>
</body>
</html>