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
	<form:form id="inputForm" modelAttribute="oaNews" action="${ctx}/oa/oaNews/save" method="post" class="form-horizontal">
		<h1 style="color: red;text-align: center;margin-top: 10px;">${oaNews.title}</h1>
        <HR color=#987cb9 SIZE=3>
        <span>${oaNews.content}</span>
        <c:if test="${not empty oaNews.files}">
            <span><b>附 件: </b></span><br/>
            <div >
                <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                <sys:ckfinder input="files" type="files" uploadPath="/oa/oaNews" readonly="true"/>
            </div>
        </c:if>
        <HR color=#987cb9 SIZE=3>
        <div style="padding-left: 60%">
            <span><b>发布人: </b>${oaNews.createManName}</span><br/>
            <span><b>审核人: </b>${oaNews.auditManName}</span><br/>
            <span><b>时　间: </b><fmt:formatDate value="${oaNews.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
        </div>
        <HR color=#987cb9 SIZE=3>
		<div class="form-actions" style="text-align: center">
            <c:if test="${oaNews.auditFlag eq '0'}">
                <input id="okBtnSubmit" onclick="location='${ctx}/oa/oaNews/auditNews?auditFlag=1&id=${oaNews.id}'" class="btn btn-primary" type="button" value="审核发布"/>
                <input id="btnSubmit" onclick="location='${ctx}/oa/oaNews/auditNews?auditFlag=2&id=${oaNews.id}'" class="btn btn-warning" type="button" value="拒绝发布"/>
            </c:if>
            <input id="btnCancel" class="btn" type="button" value="返回首页" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>