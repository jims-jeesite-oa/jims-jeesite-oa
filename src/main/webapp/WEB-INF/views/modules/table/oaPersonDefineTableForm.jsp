<%@ page import="com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义数据源管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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
		function addRow(list, idx, tpl, row){
            if(!row) row = {}
            if(!row.isAudit) row.isAudit = '0'
            if(!row.isShow) row.isShow = '0'
            if(!row.tableStatus) row.tableStatus = 100
            if(!row.columnType) row.columnType = 'text'
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
            changeAudit('oaPersonDefineTableColumnList' + idx + '_auditPost',row.isAudit)
            changeColumnType('oaPersonDefineTableColumnList' + idx + '_columnType','oaPersonDefineTableColumnList' + idx + '_tableStatus')
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
        function changeAudit(auditMan,val){
            if(val == '1') {
                $('#' + auditMan + 'Name').removeClass('disabled')
                $('#' + auditMan + 'Button').removeClass('disabled')
            } else {
                $('#' + auditMan + 'Id').val('')
                $('#' + auditMan + 'Name').val('')
                $('#' + auditMan + 'Name').addClass('disabled')
                $('#' + auditMan + 'Button').addClass('disabled')
            }
        }
        function changeColumnType(id, s){
            var v = $('#' + id).val()
            if(v != 'VARCHAR2' && v != 'REMARK') {
                $('#' + s).val('')
                $('#' + s).attr('disabled',true)
            } else {
                if($('#' + s).val() == '') $('#' + s).val(100)
                $('#' + s).removeAttr('disabled')
            }
        }

	</script>
</head>
<body>
    <ul class="breadcrumb">
        <li id="levelMenu1"><a href="#"></a> <span class="divider">/</span></li>
        <li id="levelMenu2"><a href="#"></a> <span class="divider">/</span></li>
        <li class="active">
            <shiro:hasPermission name="table:oaPersonDefineTable:edit">${not empty oaPersonDefineTable.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="table:oaPersonDefineTable:edit">查看</shiro:lacksPermission>
        </li>
    </ul>
	<form:form id="inputForm" modelAttribute="oaPersonDefineTable" action="${ctx}/table/oaPersonDefineTable/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <fieldset>
            <legend>基本信息</legend>
            <%--<div class="control-group">--%>
                <%--<label class="control-label">所属机构：</label>--%>
                <%--<div class="controls">--%>
                    <%--<sys:treeselect id="office" name="office.id" value="${oaPersonDefineTable.office.id}" labelName="office.name" labelValue="${oaPersonDefineTable.office.name}"--%>
                        <%--title="部门" url="/sys/office/treeData?type=1" cssClass="" allowClear="true" notAllowSelectParent="true"/>--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="control-group">
                <label class="control-label">表名(以英文开头)：</label>
                <div class="controls">
                    <form:input path="tableName" htmlEscape="false" cssClass="required abc startEn" maxlength="200" class="input-xlarge" readonly="${oaPersonDefineTable.id == null ? '': 'true'}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">注释：</label>
                <div class="controls">
                    <form:input path="tableComment" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
                </div>
            </div>
            <%--<div class="control-group">--%>
                <%--<label class="control-label">状态：</label>--%>
                <%--<div class="controls">--%>
                    <%--<form:select path="tableStatus" class="input-xlarge ">--%>
                        <%--<form:option value="" label=""/>--%>
                        <%--<form:options items="${fns:getDictList('table_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                    <%--</form:select>--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="control-group">
                <label class="control-label">备注信息：</label>
                <div class="controls">
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
                </div>
            </div>
            <legend>字段列表</legend>
            <p>提示：数据类型为‘文本’、‘备注’时输入‘列的长度’，审核字段为‘是’时选择‘审核人’。</p>
            <div class="control-group">
            <table id="contentTable" class="table table-striped table-bordered table-condensed"  >
                <thead>
                    <tr>
                        <th class="hide"></th>
                        <th>数据列名</th>
                        <th>数据类型</th>
                        <th>列的长度</th>
                        <th>控件显示方式</th>
                        <th>审核字段</th>
                        <th>审核人</th>
                        <th>列表显示</th>
                        <th>显示顺序</th>
                        <th>备注信息</th>
                        <shiro:hasPermission name="table:oaPersonDefineTable:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
                    </tr>
                </thead>
                <tbody id="oaPersonDefineTableColumnList">
                </tbody>
                <shiro:hasPermission name="table:oaPersonDefineTable:edit"><tfoot>
                    <tr><td colspan="11"><a href="javascript:" onclick="addRow('#oaPersonDefineTableColumnList', oaPersonDefineTableColumnRowIdx, oaPersonDefineTableColumnTpl);oaPersonDefineTableColumnRowIdx = oaPersonDefineTableColumnRowIdx + 1;" class="btn">新增</a></td></tr>
                </tfoot></shiro:hasPermission>
            </table>
            <script type="text/template" id="oaPersonDefineTableColumnTpl">//<!--
                <tr id="oaPersonDefineTableColumnList{{idx}}">
                    <td class="hide">
                        <input id="oaPersonDefineTableColumnList{{idx}}_id" name="oaPersonDefineTableColumnList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
                        <input id="oaPersonDefineTableColumnList{{idx}}_delFlag" name="oaPersonDefineTableColumnList[{{idx}}].delFlag" type="hidden" value="0"/>
                        <input id="oaPersonDefineTableColumnList{{idx}}_columnName" name="oaPersonDefineTableColumnList[{{idx}}].columnName" type="hidden" value="{{row.columnName}}"/>
                    </td>
                    <td nowrap="nowrap">
                        <input id="oaPersonDefineTableColumnList{{idx}}_columnComment" name="oaPersonDefineTableColumnList[{{idx}}].columnComment" type="text" value="{{row.columnComment}}" maxlength="200" class="input-small required"/>
                    </td>
                    <td>
                        <select id="oaPersonDefineTableColumnList{{idx}}_columnType" name="oaPersonDefineTableColumnList[{{idx}}].columnType" data-value="{{row.columnType}}" class="input-small " onchange="changeColumnType(this.id,'oaPersonDefineTableColumnList{{idx}}_tableStatus')">
                            <c:forEach items="${fns:getDictList('column_type')}" var="dict">
                                <option value="${dict.value}">${dict.label}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td nowrap="nowrap">
                        <input id="oaPersonDefineTableColumnList{{idx}}_tableStatus" name="oaPersonDefineTableColumnList[{{idx}}].tableStatus" type="text" value="{{row.tableStatus}}" maxlength="11" class="input-small number"/>
                    </td>
                    <td>
                         <select id="oaPersonDefineTableColumnList{{idx}}_controlTypeId" name="oaPersonDefineTableColumnList[{{idx}}].controlTypeId" data-value="{{row.controlTypeId}}" class="input-small ">
                            <c:forEach items="${fns:getControl()}" var="dict">
                                <option value="${dict.value}">${dict.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td nowrap="nowrap">
                        <c:forEach items="${fns:getDictList('yes_no')}" var="dict" varStatus="dictStatus">
                            <span onclick="changeAudit('oaPersonDefineTableColumnList{{idx}}_auditPost',${dict.value})"><input id="oaPersonDefineTableColumnList{{idx}}_isAudit${dictStatus.index}" name="oaPersonDefineTableColumnList[{{idx}}].isAudit" type="radio" value="${dict.value}" data-value="{{row.isAudit}}"><label for="oaPersonDefineTableColumnList{{idx}}_isAudit${dictStatus.index}">${dict.label}</label></span>
                        </c:forEach>
                    </td>
                    <td>
                        <sys:treeselect id="oaPersonDefineTableColumnList{{idx}}_auditPost" name="oaPersonDefineTableColumnList[{{idx}}].auditPost" value="{{row.auditPost}}" labelName="${row.auditPost}" labelValue="{{row.auditPostName}}"
                            title="角色" url="/sys/office/treeData?type=3&child=role" cssStyle="width:100px" allowClear="true" notAllowSelectParent="true"/>
                    </td>
                    <td nowrap="nowrap">
                        <c:forEach items="${fns:getDictList('yes_no')}" var="dict" varStatus="dictStatus">
                            <span><input id="oaPersonDefineTableColumnList{{idx}}_isShow${dictStatus.index}" name="oaPersonDefineTableColumnList[{{idx}}].isShow" type="radio" value="${dict.value}" data-value="{{row.isShow}}"><label for="oaPersonDefineTableColumnList{{idx}}_isShow${dictStatus.index}">${dict.label}</label></span>
                        </c:forEach>
                    </td>
                    <td nowrap="nowrap">
                        <input id="oaPersonDefineTableColumnList{{idx}}_sort" name="oaPersonDefineTableColumnList[{{idx}}].sort" type="text" value="{{row.sort}}" maxlength="11" class="input-small required number"/>
                    </td>
                    <td nowrap="nowrap">
                        <input id="oaPersonDefineTableColumnList{{idx}}_remarks" name="oaPersonDefineTableColumnList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="255" class="input-small"/>
                    </td>
                    <shiro:hasPermission name="table:oaPersonDefineTable:edit"><td class="text-center" width="10">
                        {{#delBtn}}<span class="close" onclick="delRow(this, '#oaPersonDefineTableColumnList{{idx}}')" title="删除">
                        &times</span>
                        {{/delBtn}}
                    </td></shiro:hasPermission>
                </tr>//-->
            </script>
            <script type="text/javascript">
						var oaPersonDefineTableColumnRowIdx = 0, oaPersonDefineTableColumnTpl = $("#oaPersonDefineTableColumnTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(oaPersonDefineTable.oaPersonDefineTableColumnList)};
							for (var i=0; i<data.length; i++){
								addRow('#oaPersonDefineTableColumnList', oaPersonDefineTableColumnRowIdx, oaPersonDefineTableColumnTpl, data[i]);
								oaPersonDefineTableColumnRowIdx = oaPersonDefineTableColumnRowIdx + 1;
							}
						});
					</script>
        </div>
        </fieldset>
		<div class="form-actions">
			<shiro:hasPermission name="table:oaPersonDefineTable:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>