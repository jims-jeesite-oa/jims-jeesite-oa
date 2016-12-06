<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/tree/css/mailCss/noneStyle.css" type="text/css"/>
    <title>收件箱</title>
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
        function deleteBy(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (confirm('彻底删除后邮件将无法恢复，您确定要删除吗？')) {
                window.location.href = '${ctx}/oa/mailInfo/thoroughDelete?ids=' + chestr + '&state=' + state;
            }
        }

        //已读邮件
        function read(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (chestr) {
                window.location.href = '${ctx}/oa/mailInfo/read?ids=' + chestr + '&readMark=1&state=' + state;
            }
        }

        //未读邮件
        function unread(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (chestr) {
                window.location.href = '${ctx}/oa/mailInfo/read?ids=' + chestr + '&readMark=0&state=' + state;
            }
        }

        //移动到已发送
        function send(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (state == "SENT") {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;'>不能移动到相同的目录</div>";
                return;
            }
            window.location.href = '${ctx}/oa/mailInfo/remove?ids=' + chestr + '&state=' + state + '&state1=SENT'
        }
        //移动到收件箱
        function inbox(state) {
            var id = document.getElementById("mailId").value;
            var chestr = id;
            if (state == "INBOX") {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;'>不能移动到相同的目录</div>";
                return;
            }
            window.location.href = '${ctx}/oa/mailInfo/remove?ids=' + chestr + '&state=' + state + '&state1=INBOX'
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

    <table style="margin-left: 5px;margin-top: 5px;width: 99.5%">
        <tr>
            <td colspan="2" align="center">
                <div id="ss"></div>
                <input type="hidden" value="${mailInfo.id}" id="mailId">
            </td>
        </tr>
        <tr class="findTr">
            <td colspan="2" style="">　　${mailInfo.theme}</td>
        </tr>
        <tr class="findTr">
            <td width="80px">　　发件人：</td>
            <td>${mailInfo.name}</td>
        </tr>
        <tr class="findTr">
            <td width="80px">　　时　间：</td>
            <td><fmt:formatDate value="${mailInfo.time}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
        </tr>
        <tr style="height: 25px; background-color: #EFF5FB;color: #ABAAAD;border-bottom: 1px solid #C1D9F3">
            <td width="80px">　　收件人：</td>
            <td>2</td>
        </tr>
        <tr style="height: 180px;">
            <td colspan="2" style="padding-left: 25px" valign="top">　　${mailInfo.content}</td>
        </tr>
        <tr style="background-color: #C1D9F3;height: 35px">
            <td colspan="2" style="padding-left: 5px">
                <input type="button" value="彻底删除" class="btn" onclick="deleteBy('${mailInfo.state}')">

                <div class="btn-group">
                    <a class="btn dropdown-toggle " data-toggle="dropdown" href="#">
                        标记为
                            <span class="caret">
                            </span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#" onclick="read('${mailInfo.state}')">&nbsp; &nbsp;<img style="width:26px;height: 19px;"
                                                                           src="${ctxStatic}/tree/css/mailCss/img/mail020.png">已读邮件</a>
                        </li>
                        <li><a href="#" onclick="unread('${mailInfo.state}')">&nbsp; &nbsp;<img style="width:26px;height: 19px;"
                                                                             src="${ctxStatic}/tree/css/mailCss/img/mail010.png">未读邮件
                        </a>
                        </li>
                    </ul>
                </div>
                <div class="btn-group">
                    <a class="btn dropdown-toggle " data-toggle="dropdown" href="#">
                        移动到
                            <span class="caret">
                            </span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#" onclick="inbox('${mailInfo.state}')">&nbsp; &nbsp;<img
                                style="width:26px;height: 19px;"
                                src="${ctxStatic}/tree/css/mailCss/img/mail020.png">收件箱 </a>
                        </li>
                        <li><a href="#" onclick="send('${mailInfo.state}')">&nbsp; &nbsp;<img
                                style="width:26px;height: 19px;"
                                src="${ctxStatic}/tree/css/mailCss/img/mail020.png">已发送 </a>
                        </li>
                    </ul>
                </div>
            </td>
        </tr>
    </table>
</div>
</body>
</html>