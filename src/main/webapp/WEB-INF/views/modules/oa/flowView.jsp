<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>表单</title>
    <meta name="decorator" content="default"/>
    <script type="text/html" id="temp">
        <sys:treeselect id="office" name="office.id" value="" labelName="" labelValue=""
                        title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
        <span class="help-inline"><font color="red">*</font> </span>
</script>
    <script type="text/javascript">
        $(document).ready(function() {
            "${oaFormMaster.content}".replace('[姓名]',$('#temp').html())
        })
    </script>
</head>
<body>
    <form:form id="inputForm" modelAttribute="oaFormMaster" action="${ctx}/form/oaFormMaster/save" method="post" class="form-horizontal">

    </form:form>
</body>
</html>
