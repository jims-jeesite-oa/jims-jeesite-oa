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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/table/oaPersonDefineTable/">自定义数据源列表</a></li>
		<li class="active"><a href="${ctx}/table/oaPersonDefineTable/form?id=${oaPersonDefineTable.id}">自定义数据源<shiro:hasPermission name="table:oaPersonDefineTable:edit">${not empty oaPersonDefineTable.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="table:oaPersonDefineTable:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaPersonDefineTable" action="${ctx}/table/oaPersonDefineTable/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所在机构：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${oaPersonDefineTable.office.id}" labelName="office.name" labelValue="${oaPersonDefineTable.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表名：</label>
			<div class="controls">
				<form:input path="tableName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注释：</label>
			<div class="controls">
				<form:input path="tableComment" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">属性：</label>
			<div class="controls">
				<form:radiobuttons path="tableProperty" items="${fns:getDictList('table_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
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
			<label class="control-label">是否主表：</label>
			<div class="controls">
				<form:radiobuttons path="isMaster" items="${fns:getDictList('is_master')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否从表：</label>
			<div class="controls">
				<form:radiobuttons path="isDetail" items="${fns:getDictList('is_detail')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">选择主表：</label>
            <div class="controls">
                <!--todo 选择主表(调出当前用户所在机构的所有的表信息--org_table_infos)-->
                <%--<form:radiobuttons path="isDetail" items="${fns:getDictList('is_detail')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>--%>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">列信息：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>列名</th>
								<th>注释</th>
								<th>列的类型</th>
								<th>列的长度</th>
								<th>&nbsp;&nbsp;是否必填&nbsp;&nbsp;</th>
								<th>是否显示到列表</th>
								<th>是否流程变量</th>
								<th>备注信息</th>
								<shiro:hasPermission name="table:oaPersonDefineTable:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="oaPersonDefineTableColumnList">
						</tbody>
						<shiro:hasPermission name="table:oaPersonDefineTable:edit"><tfoot>
							<tr><td colspan="10"><a href="javascript:" onclick="addRow('#oaPersonDefineTableColumnList', oaPersonDefineTableColumnRowIdx, oaPersonDefineTableColumnTpl);oaPersonDefineTableColumnRowIdx = oaPersonDefineTableColumnRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="oaPersonDefineTableColumnTpl">
                        //<!--
						<tr id="oaPersonDefineTableColumnList{{idx}}">
							<td class="hide">
								<input id="oaPersonDefineTableColumnList{{idx}}_id" name="oaPersonDefineTableColumnList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="oaPersonDefineTableColumnList{{idx}}_delFlag" name="oaPersonDefineTableColumnList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="oaPersonDefineTableColumnList{{idx}}_columnName" name="oaPersonDefineTableColumnList[{{idx}}].columnName" type="text" value="{{row.columnName}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="oaPersonDefineTableColumnList{{idx}}_columnComment" name="oaPersonDefineTableColumnList[{{idx}}].columnComment" type="text" value="{{row.columnComment}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<select id="oaPersonDefineTableColumnList{{idx}}_columnType" name="oaPersonDefineTableColumnList[{{idx}}].columnType" data-value="{{row.columnType}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('column_type')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="oaPersonDefineTableColumnList{{idx}}_tableStatus" name="oaPersonDefineTableColumnList[{{idx}}].tableStatus" type="text" value="{{row.tableStatus}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<c:forEach items="${fns:getDictList('is_required')}" var="dict" varStatus="dictStatus">
									<span><input id="oaPersonDefineTableColumnList{{idx}}_isRequired${dictStatus.index}" name="oaPersonDefineTableColumnList[{{idx}}].isRequired" type="radio" value="${dict.value}" data-value="{{row.isRequired}}"><label for="oaPersonDefineTableColumnList{{idx}}_isRequired${dictStatus.index}">${dict.label}</label></span>
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${fns:getDictList('is_show')}" var="dict" varStatus="dictStatus">
									<span><input id="oaPersonDefineTableColumnList{{idx}}_isShow${dictStatus.index}" name="oaPersonDefineTableColumnList[{{idx}}].isShow" type="radio" value="${dict.value}" data-value="{{row.isShow}}"><label for="oaPersonDefineTableColumnList{{idx}}_isShow${dictStatus.index}">${dict.label}</label></span>
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${fns:getDictList('is_process')}" var="dict" varStatus="dictStatus">
									<span><input id="oaPersonDefineTableColumnList{{idx}}_isProcess${dictStatus.index}" name="oaPersonDefineTableColumnList[{{idx}}].isProcess" type="radio" value="${dict.value}" data-value="{{row.isProcess}}"><label for="oaPersonDefineTableColumnList{{idx}}_isProcess${dictStatus.index}">${dict.label}</label></span>
								</c:forEach>
							</td>
							<td>
								<input id="oaPersonDefineTableColumnList{{idx}}_remarks" name="oaPersonDefineTableColumnList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="255" class="input-small "/>
							</td>
							<shiro:hasPermission name="table:oaPersonDefineTable:edit">
							<td class="text-center" width="10">
								{{#delBtn}}
								<span class="close" onclick="delRow(this, '#oaPersonDefineTableColumnList{{idx}}')" title="删除">&times</span>
 								{{/delBtn}}
							</td>
							</shiro:hasPermission>
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
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="table:oaPersonDefineTable:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>