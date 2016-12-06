<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>日程管理</title>
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
                        top.$.jBox.tip('请填写新闻内容', 'warning');
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


        //存为草稿箱
        function deleteBy() {
            form1.action = '${ctx}/oa/mailInfo/saveDrafts';
            form1.submit();
        }
        //发送
        function send() {
            form1.action = '${ctx}/oa/mailInfo/send';
            form1.submit();
        }

    </script>
</head>
<body>
<div style="background-color: #ffffff">
    <table class="table">
        <tr class="tr1">
            <td colspan="2" style="padding-left: 15px">写信</td>
        </tr>
    </table>
    <table style="width: 98.5%;" id="mytable">
        <form:form  modelAttribute="mailInfo" action="" method="post"  id="form1"
                   class="form-horizontal">
            <tr>
                <td class="td1">收件人</td>
                <td class="td"><form:input path="receiverId" htmlEscape="true" type="text"
                                           style="width:100%" value="${mailInfo.receiverId}"></form:input></td>
            </tr>
            <tr>
                <td colspan="2" style="padding-left: 69px ;"><a href="javascript:void(0);"
                                                                onclick="js_method()">添加抄送</a></td>
            </tr>
            <tr>
                <td class="td1">主题</td>
                <td class="td"><form:input path="theme" htmlEscape="true" type="text"
                                           style="width:100%" value="${mailInfo.theme}"></form:input></td>
            </tr>
            <tr>
                <td class="td1">附件</td>
                <td class="td" style="padding-bottom: 15px; height: 25px">
                    <form:hidden id="files" path="files" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
                    <sys:ckfinder input="files" type="files" uploadPath="/oa/mailInfo" selectMultiple="true"/>
                </td>

            </tr>
            <tr style=" ">
                <td class="td1" valign="top">正文</td>
                <td style=" padding-left: 7px;">
                    <form:textarea id="content" htmlEscape="true" path="content" rows="3" maxlength="200"
                                   class="input-xxlarge" value="${mailInfo.content}"/>
                    <sys:ckeditor replace="content" uploadPath="/oa/mailInfo" height="200px"/>
                </td>
            </tr>
            <tr style=" ">
                <td colspan="2" style="padding-left: 69px ;">发件人: <span>
                 ${fns:getUser().name}
                </span></td>
            </tr>
            <tr>
                <td colspan="2" style="padding-left: 69px ; padding-top: 7px">
                    <input type="submit" class="btn btn-primary" value="发送" onclick="send()">　
                    <input type="submit" class="btn btn-primary" value="存草稿" onclick="deleteBy()">
                </td>
            </tr>
        </form:form>
    </table>
</div>

</body>
</html>