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

            $("#checkall").click(
                    function(){
                        if(this.checked){
                            $("input[name='checkbox']").each(function(){this.checked=true;});
                        }else{
                            $("input[name='checkbox']").each(function(){this.checked=false;});
                        }
                    }
            );

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
            chestr=chestr.substring(0,chestr.length-1)
            if (!checked) {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;'>未选中任何邮件</div>";
                return;
            }
            window.location.href = "${ctx}/oa/mailInfo/phoneWrite?ids=" + chestr;
        }
        //循环定时删除
        window.setInterval(show, 5000);
        function show() {
            document.getElementById("ss").innerHTML = "";
        }


        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            form1.action = '${ctx}/oa/mailInfo/phone';
            $("#form1").submit();
            return false;
        }

    </script>
</head>
<body>

<div style="background-color: #ffffff">
    <form:form modelAttribute="user" action="" method="post" id="form1" class="form-horizontal">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <table class="table">
            <tr class="tr1">
                <td colspan="3" style="padding-left: 15px;">联系人 (全部)</td>
            </tr>
            <tr>
                <td class="reTd">
                    <input type="button" value="写信" class="btn btn-success" onclick="deleteBy()">
                <td>
                    <div id="ss"></div>
                </td>
            </tr>
        </table>

        <table style="width:100%; ">
            <thead>
            <tr>
                <th align="left"><input id="checkall" type="checkbox"> </th>
                <th align="left"></th>
                <th align="left">姓名</th>
                <th align="left">邮箱</th>
                <th align="left">电话</th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${page.list}" var="user">

                <tr class="reTr" data-id="${user.id}">
                    <td style="width: 40px">
                        <input type="checkbox" name="checkbox" value="${user.id}">
                    </td>
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
                    <td style="width: 15%">

                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </form:form>
</div>
<div class="pagination" align="center">${page}</div>
</body>
</html>