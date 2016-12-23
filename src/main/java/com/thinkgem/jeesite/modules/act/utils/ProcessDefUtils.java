package com.thinkgem.jeesite.modules.act.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyDao;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;

/**
 * 流程定义相关操作的封装
 * @author bluejoe2008@gmail.com
 */
public abstract class ProcessDefUtils {
	
	public static ActivityImpl getActivity(ProcessEngine processEngine, String processDefId, String activityId) {
		ProcessDefinitionEntity pde = getProcessDefinition(processEngine, processDefId);
		return (ActivityImpl) pde.findActivity(activityId);
	}

	public static ProcessDefinitionEntity getProcessDefinition(ProcessEngine processEngine, String processDefId) {
		return (ProcessDefinitionEntity) ((RepositoryServiceImpl) processEngine.getRepositoryService()).getDeployedProcessDefinition(processDefId);
	}

	public static void grantPermission(ActivityImpl activity, String assigneeExpression, String candidateGroupIdExpressions,
			String candidateUserIdExpressions) throws Exception {
		TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activity.getActivityBehavior()).getTaskDefinition();
		taskDefinition.setAssigneeExpression(assigneeExpression == null ? null : new FixedValue(assigneeExpression));
		FieldUtils.writeField(taskDefinition, "candidateUserIdExpressions", ExpressionUtils.stringToExpressionSet(candidateUserIdExpressions), true);
		FieldUtils
				.writeField(taskDefinition, "candidateGroupIdExpressions", ExpressionUtils.stringToExpressionSet(candidateGroupIdExpressions), true);

		Logger.getLogger(ProcessDefUtils.class).info(
				String.format("granting previledges for [%s, %s, %s] on [%s, %s]", assigneeExpression, candidateGroupIdExpressions,
						candidateUserIdExpressions, activity.getProcessDefinition().getKey(), activity.getProperty("name")));
	}

	/**
	 * 实现常见类型的expression的包装和转换
	 * 
	 * @author bluejoe2008@gmail.com
	 * 
	 */
	public static class ExpressionUtils {
		public static Expression stringToExpression(ProcessEngineConfigurationImpl conf, String expr) {
			return conf.getExpressionManager().createExpression(expr);
		}

		public static Expression stringToExpression(String expr) {
			return new FixedValue(expr);
		}

		public static Set<Expression> stringToExpressionSet(String exprs) {
			Set<Expression> set = new LinkedHashSet<Expression>();
			for (String expr : exprs.split(";")) {
				set.add(stringToExpression(expr));
			}

			return set;
		}
	}

    /**
     * 获取流程列表
     * @param category 流程分类
     */
    public static List<Object[]> processList(String category) {
        RepositoryService repositoryService = SpringContextHolder.getBean(RepositoryService.class);
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion().active().orderByProcessDefinitionKey().asc();

        if (StringUtils.isNotEmpty(category)){
            processDefinitionQuery.processDefinitionCategory(category);
        }
        List<Object[]> list = new ArrayList<>();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            list.add(new Object[]{processDefinition, deployment});
        }
        return list;
    }

    /**
     * 获取流程列表
     * @param category 流程分类
     */
    public static List<Object[]> getProcess(String category) {
        RepositoryService repositoryService = SpringContextHolder.getBean(RepositoryService.class);
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion().active().orderByProcessDefinitionKey().asc();

        if (StringUtils.isNotEmpty(category)){
            processDefinitionQuery.processDefinitionCategory(category);
        }
        List<Object[]> list = new ArrayList<>();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            list.add(new Object[]{processDefinition, deployment});
        }
        return list;
    }
}