<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>邮件帐户设置</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function js_method() {
            $("#mytable").append("")
        }

        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    if (CKEDITOR.instances.content.getData() == "") {
                        top.$.jBox.tip('请填写邮件内容', 'warning');
                    } else {
//                        loading('正在提交，请稍等...');
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
    <style  type="text/css">
        .bt{

        }
    </style>
</head>
<body>
<div style="background-color: #ffffff">
    <table style="width: 96%" align="center">
        <tr style="height: 45px">
            <td style="padding-left: 5px;font-size: 25px">邮件帐户设置</td>
        </tr>
        <tr>
            <td style="border-top:1px solid #DFDDDD;padding-bottom: 15px" colspan="2"></td>
        </tr>
    </table>
    <table style="width: 96%;border: 1px solid #DFDDDD;background-color: #F5F5F5;height: 340px" align="center">
        <form:form modelAttribute="mailAccount" action="${ctx}/oa/mailInfo/saveAccount" method="post" id="form1"
                   class="form-horizontal">
            <form:hidden path="id"></form:hidden>
            <tr>
                <td style="padding-left: 70px;width:100px;text-align: right">邮件显示名称：</td>
                <td style="padding-top: 2px"><form:input path="mailName" htmlEscape="true" type="text"
                                                         style="width:800px;"></form:input>
                    <span class="help-inline"><font color="red">*</font> </span></td>
            </tr>
            <tr>
                <td style="padding-left: 70px;width:100px;text-align: right">邮件地址：</td>
                <td><form:input path="mailAddress" htmlEscape="true" type="text"
                                                         style="width:800px;" placeholder=""></form:input>
                    <span class="help-inline"><font color="red">*</font> </span></td>
            </tr>
            <tr>
                <td style="padding-left: 70px;width:100px;text-align: right">端口号：</td>
                <td><form:input path="port" htmlEscape="true" type="text"
                                                         style="width:800px;" placeholder="例如：25"></form:input>
                    <span class="help-inline"><font color="red">*</font></span>
                </td>
            </tr>
            <tr>
                <td style="padding-left: 70px;width:100px;text-align: right">接收服务器：</td>
                <td ><form:input path="mailAccept" htmlEscape="true" type="text"
                                                         style="width:800px;" placeholder="例如：imap.qq.com"></form:input>
                     <span class="help-inline"><font color="red">*</font></span>
                </td>
            </tr>


            <tr>
                <td style="padding-left: 70px;width:100px;text-align: right">发送服务器：</td>
                <td ><form:input path="mailSend" htmlEscape="true" type="text"
                                                         style="width:800px;" placeholder="例如：smtp.qq.com"></form:input>
                     <span class="help-inline"><font color="red">*</font></span>
                </td>
            </tr>

            <tr>
                <td style="padding-left: 70px;width:100px;text-align: right">用户名：</td>
                <td ><form:input path="username" htmlEscape="true" type="text"
                                                         style="width:800px;"></form:input>
                    <span class="help-inline"><font color="red">*</font> </span></td>
            </tr>

            <tr>
                <td style="padding-left: 70px;width:100px;text-align: right">密码：</td>
                <td ><form:input path="password" htmlEscape="true" type="password"
                                                         style="width:800px;"></form:input>
                    <span class="help-inline"><font color="red">*</font> </span></td>
            </tr>
            <tr>
                <td style="padding-left: 70px;width:100px;text-align:right">
                </td>
                <td style="text-align:left">
                    <input type="submit" class="btn btn-primary" value="保存">　
                </td>
            </tr>
        </form:form>
    </table>
</div>

</body>
</html>