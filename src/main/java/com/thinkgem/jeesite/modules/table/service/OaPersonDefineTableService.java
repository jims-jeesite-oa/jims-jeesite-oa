/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.service;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.table.utils.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableColumnDao;

/**
 * 自定义数据源Service
 * @author chenxy
 * @version 2016-11-24
 */
@Service
@Transactional(readOnly = true)
public class OaPersonDefineTableService extends CrudService<OaPersonDefineTableDao, OaPersonDefineTable> {

	@Autowired
	private OaPersonDefineTableColumnDao oaPersonDefineTableColumnDao;

    @Autowired
    private OaPersonDefineTableDao oaPersonDefineTableDao;
	
	public OaPersonDefineTable get(String id) {
		OaPersonDefineTable oaPersonDefineTable = super.get(id);
		oaPersonDefineTable.setOaPersonDefineTableColumnList(oaPersonDefineTableColumnDao.findList(new OaPersonDefineTableColumn(oaPersonDefineTable)));
		return oaPersonDefineTable;
	}
	
	public List<OaPersonDefineTable> findList(OaPersonDefineTable oaPersonDefineTable) {
		return super.findList(oaPersonDefineTable);
	}
	
	public Page<OaPersonDefineTable> findPage(Page<OaPersonDefineTable> page, OaPersonDefineTable oaPersonDefineTable) {
		return super.findPage(page, oaPersonDefineTable);
	}
	
	@Transactional(readOnly = false)
	public void save(OaPersonDefineTable oaPersonDefineTable) {
		super.save(oaPersonDefineTable);
        int index = 1;
		for (OaPersonDefineTableColumn oaPersonDefineTableColumn : oaPersonDefineTable.getOaPersonDefineTableColumnList()){
			if (oaPersonDefineTableColumn.getId() == null){
				continue;
			}
			if (OaPersonDefineTableColumn.DEL_FLAG_NORMAL.equals(oaPersonDefineTableColumn.getDelFlag())){
                //字段为空时默认 COL+序列
//                if(StringUtils.isBlank(oaPersonDefineTableColumn.getColumnName())){
                    oaPersonDefineTableColumn.setColumnName("COL" + index++);

                if (StringUtils.isBlank(oaPersonDefineTableColumn.getId())){
					oaPersonDefineTableColumn.setTable(oaPersonDefineTable);
					oaPersonDefineTableColumn.preInsert();
					oaPersonDefineTableColumnDao.insert(oaPersonDefineTableColumn);
				}else{
					oaPersonDefineTableColumn.preUpdate();
					oaPersonDefineTableColumnDao.update(oaPersonDefineTableColumn);
				}
			}else{
				oaPersonDefineTableColumnDao.delete(oaPersonDefineTableColumn);
			}
		}
        dao.executeSql(getDeleteTableSql(oaPersonDefineTable.getTableName()));
        Map<String,Object> result = JdbcUtils.getSql(oaPersonDefineTable);
        dao.executeSql((String)result.get("tableSql"));
        List<String> comments = (List<String>) result.get("comment");
        if(comments != null && comments.size() > 0){
            for(String comment : comments){
                dao.executeSql(comment);
            }
        }
 	}
	
	@Transactional(readOnly = false)
	public void delete(OaPersonDefineTable oaPersonDefineTable) {
		super.delete(oaPersonDefineTable);
		oaPersonDefineTableColumnDao.delete(new OaPersonDefineTableColumn(oaPersonDefineTable));
        dao.executeSql(getDeleteTableSql(oaPersonDefineTable.getTableName()));
	}

    public OaPersonDefineTable findByTableName(String tableName, String officeId) {
       return  this.oaPersonDefineTableDao.findByTableName(tableName, officeId);
    }


    public List<OaPersonDefineTableColumn> findColumnListByTableId(String tableId) {
      return this.oaPersonDefineTableColumnDao.findColumnListByTableId(tableId);
    }

    public List<OaPersonDefineTableColumn> findColumnList(OaPersonDefineTableColumn column) {
        return this.oaPersonDefineTableColumnDao.findList(column);
    }

    public void deleteInfo(String tableName,String infoId){
        String sql = "delete from " + tableName + " where id='" + infoId + "'";
        oaPersonDefineTableDao.executeSql(sql);
    }

    private String getDeleteTableSql(String tableName){
        return "BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + tableName + "';EXCEPTION WHEN OTHERS THEN NULL;END;";
    }
}