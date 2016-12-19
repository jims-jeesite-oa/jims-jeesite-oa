<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>发送失败</title>
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
    </script>
    <style>
        a:link{
            color: #008000;
            text-decoration:underline
        }
    </style>
</head>
<body>
<div style="background-color: #ffffff">
    <table style="margin-left: 60px;margin-top: 40px" width="400px">
        <tr>
            <td style="width: 53px;height: 45px" rowspan="4" valign="top">
                <img src="${ctxStatic}/tree/css/mailCss/img/mail90.png" style="width: 53px;height: 45px"></td>
            <td style="font-size: 20px; color: #008000;font-weight: 500;height: 30px">您的邮件发送失败</td>
        </tr>
        <tr>
            <td style="font-size:50%;color:#B2AFAF;height: 30px">此邮件发送失败，没有绑定外部邮箱，不能发送</td>
        </tr>
        <tr>
            <td style="height: 50px">
                <input type="button" class="btn btn-primary" value="去绑定"  onclick="location='${ctx}/oa/mailInfo/account'" >
            </td>
        </tr>

    </table>
</div>
</body>
</html>