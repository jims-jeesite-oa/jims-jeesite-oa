<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/11/25
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
      <form name="common" id="common" action="/">
          ${formContent}
          <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
          <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
      </form>
</body>
</html>