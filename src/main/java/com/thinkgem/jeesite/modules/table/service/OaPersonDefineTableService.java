/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.service;

import java.util.List;

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
		for (OaPersonDefineTableColumn oaPersonDefineTableColumn : oaPersonDefineTable.getOaPersonDefineTableColumnList()){
			if (oaPersonDefineTableColumn.getId() == null){
				continue;
			}
			if (OaPersonDefineTableColumn.DEL_FLAG_NORMAL.equals(oaPersonDefineTableColumn.getDelFlag())){
				if (StringUtils.isBlank(oaPersonDefineTableColumn.getId())){
					oaPersonDefineTableColumn.setTableId(oaPersonDefineTable);
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
        JdbcUtils.createTable(oaPersonDefineTable);
 	}
	
	@Transactional(readOnly = false)
	public void delete(OaPersonDefineTable oaPersonDefineTable) {
		super.delete(oaPersonDefineTable);
		oaPersonDefineTableColumnDao.delete(new OaPersonDefineTableColumn(oaPersonDefineTable));
	}

    public OaPersonDefineTable findByTableName(String tableName, String officeId) {
       return  this.oaPersonDefineTableDao.findByTableName(tableName, officeId);
    }


    public List<OaPersonDefineTableColumn> findColumnListByTableId(String tableId) {
      return this.oaPersonDefineTableColumnDao.findColumnListByTableId(tableId);
    }
}