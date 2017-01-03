<%@ page import="com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义数据源管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        var updateFlag = '${oaPersonDefineTable.updateType}';
        var validator ;
        jQuery.validator.addMethod("existed",function(value, element) {
            return this.optional(element) || notExisted(value);
        },"关键字已存在");
        function notExisted(v) {
            var len = $('#oaPersonDefineTableColumnList .keyTD input').length;
            var temp = 0;
            for(var i = 0; i < len; i++) {
                if($('.keyTD input:eq(' + i +')').val() == v
                        && !$('.keyTD:eq(' + i +')').parent().hasClass('error')) temp++;
                if(temp == 2) return false;
            }
            return true;
        }
		$(document).ready(function() {
            validator = $("#inputForm").validate({
                rules: {
                    tableName: {remote: "${ctx}/table/oaPersonDefineTable/checkTableName?oldTableName=" + encodeURIComponent('${oaPersonDefineTable.tableName}')}
                },
                messages: {
                    tableName: {remote: "表名已存在"}
                },
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
            if(row.id && updateFlag != 'reset') {
                $(list+idx+'_columnComment').attr('disabled',true)
                $(list+idx+'_columnType').attr('disabled',true)
                $(list+idx+'_tableStatus').attr('disabled',true)
                $(list+idx+'_columnName').attr('disabled',true)
            } else {
                changeColumnType('oaPersonDefineTableColumnList' + idx + '_columnType','oaPersonDefineTableColumnList' + idx + '_tableStatus')
            }
            changeAudit('oaPersonDefineTableColumnList' + idx + '_auditPost',row.isAudit)
            changeControl(row.controlTypeId,'oaPersonDefineTableColumnList' + idx + '_remarks')
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
                validator.form()
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
                validator.form()
			}
		}
        function changeAudit(id,val){
            if(val == '1') {
                $('#' + id).removeAttr('disabled')
                $('#' + id).addClass('required')
            } else {
                $('#' + id).val('')
                $('#' + id).attr('disabled',true)
                $('#' + id).removeClass('required')
            }
        }
        function changeColumnType(id, s){
            var v = $('#' + id).val()
            if(v != 'VARCHAR2' && v != 'REMARK') {
                $('#' + s).val('')
                $('#' + s).attr('disabled',true)
                $('#' + s).removeClass('required number')
            } else {
                if($('#' + s).val() == '') $('#' + s).val(100)
                $('#' + s).removeAttr('disabled')
                $('#' + s).addClass('required number')
            }
        }

        function changeControl(value,id){
            if(value == 'radio' || value == 'checkbox' || value == 'select') {
                $('#' + id).addClass('required')
            } else {
                $('#' + id).removeClass('required')
            }
        }

        function initPY(prefix,val) {
            var obj = $('#' + prefix + 'columnName')
            if(!obj || obj.val() == '' && val && val != '') {
                try {
                    $.get('${ctx}/table/oaPersonDefineTable/getShortPinYin?str='+val,function(str){
                        $('#' + prefix + 'columnName').val(str.toUpperCase())
                        validator.form()
                    })
                } catch (e) {
                    obj.val('')
                }
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
        <form:hidden path="updateType"/>
		<sys:message content="${message}"/>
        <fieldset>
            <legend>基本信息</legend>
            <div class="control-group">
                <label class="control-label">表名(以英文开头)：</label>
                <div class="controls">
                    <form:input id="tableName" path="tableName" htmlEscape="false" cssClass="required abc startEn" maxlength="200" class="input-xlarge" readonly="${oaPersonDefineTable.id == null ? '': 'true'}" cssStyle="text-transform:uppercase" onkeyup="this.value=this.value.toUpperCase()"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">注释：</label>
                <div class="controls">
                    <form:input path="tableComment" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">备注信息：</label>
                <div class="controls">
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
                </div>
            </div>
            <legend>字段列表</legend>
            <span class="help-inline">提示：数据类型为‘文本’、‘备注’时输入‘列的长度’，审核字段为‘是’时输入‘审核节点ID’。</span>
            <div class="control-group">
            <table id="contentTable" class="table table-striped table-bordered table-condensed"  >
                <thead>
                    <tr>
                        <th class="hide"></th>
                        <th>关键字</th>
                        <th>数据列名</th>
                        <th>数据类型</th>
                        <th>数据长度</th>
                        <th>列表显示</th>
                        <th>显示顺序</th>
                        <th>审核字段</th>
                        <th>审核节点ID</th>
                        <th>控件显示方式</th>
                        <th>控件参数</th>
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
                    </td>
                    <td nowrap="nowrap" class="keyTD">
                        <input id="oaPersonDefineTableColumnList{{idx}}_columnName" name="oaPersonDefineTableColumnList[{{idx}}].columnName" type="text" value="{{row.columnName}}" maxlength="200" class="input-small required abc startEn existed" cssStyle="text-transform:uppercase" onkeyup="this.value=this.value.toUpperCase()"/>
                    </td>
                    <td nowrap="nowrap">
                        <input onblur="initPY('oaPersonDefineTableColumnList{{idx}}_',this.value)" id="oaPersonDefineTableColumnList{{idx}}_columnComment" name="oaPersonDefineTableColumnList[{{idx}}].columnComment" type="text" value="{{row.columnComment}}" maxlength="200" class="input-small required"/>
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
                    <td nowrap="nowrap">
                        <c:forEach items="${fns:getDictList('yes_no')}" var="dict" varStatus="dictStatus">
                            <span><input id="oaPersonDefineTableColumnList{{idx}}_isShow${dictStatus.index}" name="oaPersonDefineTableColumnList[{{idx}}].isShow" type="radio" value="${dict.value}" data-value="{{row.isShow}}"><label for="oaPersonDefineTableColumnList{{idx}}_isShow${dictStatus.index}">${dict.label}</label></span>
                        </c:forEach>
                    </td>
                    <td nowrap="nowrap">
                        <input id="oaPersonDefineTableColumnList{{idx}}_sort" name="oaPersonDefineTableColumnList[{{idx}}].sort" type="text" value="{{row.sort}}" maxlength="11" class="input-small required number"/>
                    </td>
                    <td nowrap="nowrap">
                        <c:forEach items="${fns:getDictList('yes_no')}" var="dict" varStatus="dictStatus">
                            <span onclick="changeAudit('oaPersonDefineTableColumnList{{idx}}_auditPost',${dict.value})"><input id="oaPersonDefineTableColumnList{{idx}}_isAudit${dictStatus.index}" name="oaPersonDefineTableColumnList[{{idx}}].isAudit" type="radio" value="${dict.value}" data-value="{{row.isAudit}}"><label for="oaPersonDefineTableColumnList{{idx}}_isAudit${dictStatus.index}">${dict.label}</label></span>
                        </c:forEach>
                    </td>
                    <td nowrap="nowrap">
                        <input id="oaPersonDefineTableColumnList{{idx}}_auditPost" name="oaPersonDefineTableColumnList[{{idx}}].auditPost" type="text" value="{{row.auditPost}}" maxlength="255" class="input-small"/>
                    </td>
                    <td>
                         <select onchange="changeControl(this.value,'oaPersonDefineTableColumnList{{idx}}_remarks')" id="oaPersonDefineTableColumnList{{idx}}_controlTypeId" name="oaPersonDefineTableColumnList[{{idx}}].controlTypeId" data-value="{{row.controlTypeId}}" class="input-small ">
                            <c:forEach items="${fns:getControl()}" var="dict">
                                <option value="${dict.value}">${dict.name}</option>
                            </c:forEach>
                        </select>
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