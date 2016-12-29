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

            $(".check").click(function (e) {
                e.stopPropagation()
            })

            $(".reTr").on('click', function () {
                var id = $(this).attr("data-id")
                window.location.href = '${ctx}/oa/mailInfo/find?id=' + id
            })


            $(".checkOut").click(function (e) {
                e.stopPropagation()
            })


            $(".reOut").on('click', function () {

                var uid = $(this).attr("data-id")
                var name = $(this).attr("data-drop")
                var state=document.getElementById("state").value
//                var receiveNames = $(this).attr("data-count")
//                var time = $(this).attr("data-items")
//                var d = new Date(time)
//                var date1 = d.pattern("yyyy-MM-dd HH:mm:ss");
//                var content=$(this).attr("data-content");
                window.location.href = '${ctx}/oa/mailInfo/findMail?id=' + uid + "&name=" + name+'&state='+state;
            })

            //格式化日期
            Date.prototype.pattern = function (fmt) {
                var o = {
                    "M+": this.getMonth() + 1, //月份
                    "d+": this.getDate(), //日
                    "h+": this.getHours() == 0 ? 12 : this.getHours(), //小时
                    "H+": this.getHours(), //小时
                    "m+": this.getMinutes(), //分
                    "s+": this.getSeconds(), //秒
                    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                    "S": this.getMilliseconds() //毫秒
                };
                var week = {
                    "0": "\u65e5",
                    "1": "\u4e00",
                    "2": "\u4e8c",
                    "3": "\u4e09",
                    "4": "\u56db",
                    "5": "\u4e94",
                    "6": "\u516d"
                };
                if (/(y+)/.test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                }
                if (/(E+)/.test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
                }
                for (var k in o) {
                    if (new RegExp("(" + k + ")").test(fmt)) {
                        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                    }
                }
                var m = {
                    "s": "m",
                    "d": "h"
                };
                return fmt;
            }

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
                form1.action = '${ctx}/oa/mailInfo/thoroughDelete?ids=' + chestr + '&state=INBOX';
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
            form1.action = '${ctx}/oa/mailInfo/move?ids=' + chestr + '&state=INBOX';
            form1.submit();
        }

        //已读邮件
        function read() {
            var checked = false;
            var chestr = ""
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
            form1.action = '${ctx}/oa/mailInfo/read?ids=' + chestr + '&readMark=1&state=INBOX';
            form1.submit();
        }

        //未读邮件
        function unread() {
            var checked = false;
            var chestr = ""
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
            form1.action = '${ctx}/oa/mailInfo/read?ids=' + chestr + '&readMark=0&state=INBOX';
            form1.submit();
        }

        //移动到已发送
        function send() {
            var checked = false;
            var ids = document.getElementsByName("checkbox");
            var chestr = ""
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

            form1.action = '${ctx}/oa/mailInfo/remove?ids=' + chestr + '&state=INBOX&state1=SENT';
            form1.submit();
        }
        //移动到收件箱
        function inbox() {
            var checked = false;
            var ids = document.getElementsByName("checkbox");
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    checked = true;
                }
            }
            if (!checked) {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;'>不能移动到相同的目录</div>";
                return;
            } else {
                document.getElementById("ss").innerHTML = "<div style='color: #ffffff;background-color: #EF8F00;width: 135px;height: 20px;text-align: center;'>不能移动到相同的目录</div>";
                return;
            }
        }


        /**
         *查询内部邮件或者外部邮件
         */
        function find(state) {
            var flag = document.getElementById("type").value
            if (flag == "1") {
                form1.action = '${ctx}/oa/mailInfo/findOut?state='+state;
                form1.submit();
            } else {
                form1.action = '${ctx}/oa/mailInfo/listBySend?state=INBOX';
                form1.submit();
            }
        }


        //循环定时删除
        window.setInterval(show, 5000);
        function show() {
            document.getElementById("ss").innerHTML = "";
        }

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            form1.action = '${ctx}/oa/mailInfo/listBySend?state=INBOX';
            $("#form1").submit();
            return false;
        }

    </script>
</head>
<body>
<div style="background-color: #ffffff">
    <form:form id="form1" modelAttribute="mailInfo" action="" method="post"
               class="form-horizontal">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <table class="table">
            <tr class="tr1">
                <td colspan="2" style="padding-left: 15px">收件箱 (<font>共 </font>&nbsp;${page.count} &nbsp;封,其中 <font
                        color="#30A5FF">未读邮件</font>&nbsp;${page.delete} &nbsp;封 )
                </td>
            </tr>
            <tr>
                <td class="reTd">
                    <input type="button" value="删除" class="btn btn-success"
                           onclick="deleteB()">
                    <input type="button" value="彻底删除" class="btn btn-warning" onclick="deleteBy()">

                    <div class="btn-group">
                        <a class="btn dropdown-toggle btn-success" data-toggle="dropdown" href="#">
                            标记为
                            <span class="caret">
                            </span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#" onclick="read()">&nbsp; &nbsp;<img style="width:26px;height: 19px;"
                                                                               src="${ctxStatic}/tree/css/mailCss/img/mail020.png">已读邮件</a>
                            </li>
                            <li><a href="#" onclick="unread()">&nbsp; &nbsp;<img style="width:26px;height: 19px;"
                                                                                 src="${ctxStatic}/tree/css/mailCss/img/mail010.png">未读邮件
                            </a>
                            </li>
                        </ul>
                    </div>
                    <div class="btn-group">
                        <a class="btn dropdown-toggle btn-success" data-toggle="dropdown" href="#">
                            移动到
                            <span class="caret">
                            </span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#" onclick="inbox()">&nbsp; &nbsp;<img style="width:26px;height: 19px;"
                                                                                src="${ctxStatic}/tree/css/mailCss/img/mail020.png">收件箱
                            </a>
                            </li>
                            <li><a href="#" onclick="send()">&nbsp; &nbsp;<img style="width:26px;height: 19px;"
                                                                               src="${ctxStatic}/tree/css/mailCss/img/mail020.png">已发送
                            </a>
                            </li>
                        </ul>
                    </div>
                    <form:form id="inputForm" modelAttribute="mailInfo" action="" method="post"
                               class="breadcrumb form-search">
                        邮件类型
                        <form:select path="flag" class="input-medium" cssStyle="width: 100px" id="type">
                            <form:option value="" label=""/>    <%--htmlEscape="false"--%>
                            <form:options items="${fns:getDictList('mail_info')}" itemLabel="label" itemValue="value"
                                          htmlEscape="true"/>
                        </form:select>
                        <input type="hidden" id="state" value="${mailInfo.state}">
                        <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="find('${mailInfo.state}')"/>
                    </form:form>
                </td>
                <td>
                    <div id="ss"></div>
                </td>
            </tr>
        </table>
        <table style="width:100%; ">
            <thead>
            <tr>
                <th></th>
                <th></th>
                <th align="left">发件人</th>
                <th align="left">主题</th>
                <th align="left">时间</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <form:form id="inputForm" modelAttribute="mailInfo" action="${ctx}/oa/mailInfo/move" method="post"
                       class="form-horizontal">

                <c:if test="${page.list.size() eq '0'}">
                    <tr>
                        <td colspan="6" valign="center" rowspan="2">
                            <div align="center"
                                 style="text-align: center;height: 50px;font-size: 20px;padding-top: 20px">没有邮件${page.mail}
                            </div>
                        </td>
                    </tr>
                </c:if>


                <c:if test="${not empty page.list}">
                    <c:forEach items="${page.list}" var="mailInfo">

                        <c:if test="${mailInfo.flag eq 1}">
                            <tr class="reOut" data-id="${mailInfo.UID}" data-drop="${mailInfo.name}">
                                <td class="reCheckbox">
                                    <input type="checkbox" name="checkbox" value="${mailInfo.id}" class="checkOut">
                                </td>
                                <td style=" width: 25px ;" align="left">
                                    <c:if test="${mailInfo.readMark eq 'true'}">
                                        <img src="${ctxStatic}/tree/css/mailCss/img/mail020.png"/>
                                    </c:if>
                                    <c:if test="${mailInfo.readMark eq 'false'}">
                                        <img src="${ctxStatic}/tree/css/mailCss/img/mail010.png"/>
                                    </c:if>
                                </td>
                                <td style="width:25%;">
                                        ${mailInfo.name}
                                </td>
                                <td style="width:40%;">
                                        ${fns:abbr(mailInfo.theme,50)}
                                </td>
                                <td style="width:15%;">
                                    <fmt:formatDate value="${mailInfo.time}" type="both" pattern="yyyy年MM月dd日 "/>
                                </td>
                                <td style="width:10%;" align="center">

                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${mailInfo.flag eq 0}">
                            <tr class="reTr" data-id="${mailInfo.id}">
                                <td class="reCheckbox">
                                    <input type="checkbox" name="checkbox" value="${mailInfo.id}" class="check">
                                </td>
                                <td style=" width: 25px ;" align="left">
                                    <c:if test="${mailInfo.readMark eq 1}">
                                        <img src="${ctxStatic}/tree/css/mailCss/img/mail020.png"/>
                                    </c:if>
                                    <c:if test="${mailInfo.readMark eq 0}">
                                        <img src="${ctxStatic}/tree/css/mailCss/img/mail010.png"/>
                                    </c:if>
                                </td>
                                <td style="width:25%;">
                                        ${mailInfo.name}
                                </td>
                                <td style="width:40%;">
                                        ${fns:abbr(mailInfo.theme,50)}
                                </td>
                                <td style="width:15%;">
                                    <fmt:formatDate value="${mailInfo.time}" type="both" pattern="yyyy年MM月dd日 "/>
                                </td>
                                <td style="width:10%;" align="center">
                                        <%-- <img src="${ctxStatic}/tree/css/mailCss/img/mail030.png"/>--%>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </form:form>
            </tbody>
        </table>
    </form:form>
</div>
<div class="pagination" align="center">${page}</div>
</body>
</html>