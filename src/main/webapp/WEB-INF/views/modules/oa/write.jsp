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
        });


        //存为草稿箱
        function deleteBy() {
            form1.action = '${ctx}/oa/mailInfo/saveDrafts';
            form1.submit();
        }
        //发送
        function send() {
            var theme=document.getElementById("theme").value
            if(theme.length>240){
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 190px;height: 20px;text-align: center;'>主题长度不能超过120个中文字符</div>";
                return;
            } else{
                var rr=document.getElementById("rr").value
                if(rr!=null && rr !=""){
                    form1.action = '${ctx}/oa/mailInfo/sendOut';
                    form1.submit();
                } else{
                    form1.action = '${ctx}/oa/mailInfo/send';
                    form1.submit();
                }
            }


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
    <table class="table">
        <tr class="tr1">
            <td colspan="2" style="padding-left: 15px;">写信</td>
        </tr>
        <tr>
            <td  style="padding-left: 69px ; padding-top: 7px">
                <input type="submit" class="btn btn-success" value="发送" onclick="send()">　
                <input type="submit" class="btn btn-success" value="存草稿" onclick="deleteBy()">
            </td>
            <td>
                <div id="ss"></div>
            </td>
        </tr>
    </table>
    <table style="width: 98.5%;" id="mytable" id="tb01">
        <form:form modelAttribute="mailInfo" action="" method="post" id="form1"
                   class="form-horizontal">
            <form:hidden path="id"></form:hidden>
            <tr>
                <td class="td1">外部收件人</td>
                <td class="td">
                    <div class="controls">
                        <form:input path="outSide" htmlEscape="true" type="text"
                                    style="width:800px;" id="rr" ></form:input>
                    </div>
            </tr>
            <tr>
                <td class="td1" id="person">收件人</td>
                <td class="td">
                    <div class="controls">
                        <sys:treeselect id="receiverId" name="receiverId"
                                        value="${mailInfo.receiverId}" labelName="name" labelValue="${mailInfo.receiverNames}"
                                        title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required" notAllowSelectParent="true" checked="true" cssStyle="width:800px"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </div>
            </tr>
            <tr>
                <td class="td1">抄送人</td>
                <td class="td">
                        <sys:treeselect id="ccId" name="ccId"
                                        value="${mailInfo.ccId}" labelName="name" labelValue="${mailInfo.ccNames}"
                                        title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required" notAllowSelectParent="true" checked="true" cssStyle="width:800px"/>
                    <span class="help-inline"><font color="red">*</font> </span>
            </tr>
            <tr>
                <td class="td1">主题</td>
                <td class="td"><form:input path="theme" htmlEscape="false" type="text"
                                           style="width:800px" value="${mailInfo.theme}" id="theme"></form:input></td>
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
                                   class="input-xxlarge"/>
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
                    <input type="submit" class="btn btn-success" value="发送" onclick="send()">　
                    <input type="submit" class="btn btn-success" value="存草稿" onclick="deleteBy()">
                </td>
            </tr>
        </form:form>
    </table>
</div>

</body>
</html>