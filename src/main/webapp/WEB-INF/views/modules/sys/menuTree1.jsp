<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    .menu2 {
        font-size: 12px;
    }
</style>
<div>
    <c:set var="menuList" value="${fns:getMenuList()}"/>
    <c:set var="firstMenu" value="true"/>
    <ul>
        <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
            <c:if test="${menu.parent.id eq (not empty param.parentId ? param.parentId : 1 ) && menu.isShow eq '1'}">
                <li class="menu1" data-id="${menu.id}">
                    <a href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${not empty menu.href ? menu.href : '/404'}" target="${not empty menu.target ? menu.target : 'mainFrame'}" >
                        <i class="icon-${not empty menu.icon ? menu.icon : 'circle-arrow-right'}"></i>&nbsp;${menu.name}</a>
                </li>
                <c:forEach items="${menuList}" var="menu2">
                    <c:if test="${menu2.parent.id eq menu.id && menu2.isShow eq '1'}">
                        <li class="menu2 parent-${menu.id}">
                        <a href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}" target="${not empty menu2.target ? menu2.target : 'mainFrame'}" >
                            <i class="icon-${not empty menu2.icon ? menu2.icon : 'circle-arrow-right'}"></i>&nbsp;${menu2.name}</a>
                        </li>
                    </c:if>
                </c:forEach>
            </c:if>
        </c:forEach>
    </ul>
</div>