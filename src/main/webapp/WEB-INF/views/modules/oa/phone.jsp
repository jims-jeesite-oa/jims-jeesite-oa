<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>联系人</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    if (CKEDITOR.instances.content.getData() == "") {
                        top.$.jBox.tip('请填写新闻内容', 'warning');
                    } else {
                        form.submit();
                    }
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

        });


        //彻底删除
        function deleteBy() {
            var checked = false;
            var ids = document.getElementsByName("checkbox");
            var chestr = "";
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    chestr += ids[i].value + ",";
                }
            }
            if (!checked) {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;'>未选中任何邮件</div>";
                return;
            }
            if (confirm('彻底删除后邮件将无法恢复，您确定要删除吗？')) {
                form1.action = '${ctx}/oa/mailInfo/thoroughDelete?ids=' + chestr + '&state=SENT';
                form1.submit();
            }
        }

        //删除
        function deleteB() {
            var checked = false;
            var chestr = "";
            var ids = document.getElementsByName("checkbox");
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                    chestr += ids[i].value + ",";
                }
            }
            if (!checked) {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;'>未选中任何邮件</div>";
                return;
            }
            form1.action = '${ctx}/oa/mailInfo/move?ids=' + chestr;
            form1.submit();
        }

        //循环定时删除
        window.setInterval(show, 5000);
        function show() {
            document.getElementById("ss").innerHTML = "";
        }


    </script>
</head>
<body>

<div style="background-color: #ffffff">
    <form:form modelAttribute="user" action="" method="post" id="form1" class="form-horizontal">
        <table class="table">
            <tr class="tr1">
                <td colspan="3" style="padding-left: 15px;">联系人 (全部)</td>
            </tr>
            <tr>
               <%-- <td class="reTd">
                    <c:if test="${fns.getUser().id eq 1}">
                    <input type="button" value="删除" class="btn btn-warning"
                           onclick="deleteB()">
                    </c:if>
                    <input type="button" value="写信" class="btn btn-success" onclick="deleteBy()">
                <td>
                    <div id="ss"></div>
                </td>--%>
            </tr>
        </table>

        <table style="width:100%; ">
            <thead>
            <tr>
                <th></th>
                <th></th>
                <th align="left">姓名</th>
                <th align="left">邮箱</th>
                <th align="left">电话</th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${page.list}" var="user">

                <tr class="reTr" data-id="${user.id}">
                    <td style=" width: 25px ;" align="left">
                    </td>
                    <td style="width:25%;">
                            ${user.name}
                    </td>
                    <td style="width:40%;">
                            ${fns:abbr(user.email,50)}
                    </td>
                    <td style="width:15%;">
                        ${user.phone}
                    </td>
                   <%-- <td style="width:10%;" align="center">
                        <img src="${ctxStatic}/tree/css/mailCss/img/mail030.png"/>
                    </td>--%>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </form:form>
</div>
<div class="pagination" align="center">${page}</div>
</body>
</html>