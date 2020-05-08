<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0043)http://localhost:8080/mango/cart/list.jhtml -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>订单页面</title>
<link href="${pageContext.request.contextPath}/css/common.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/cart.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/product.css"
	rel="stylesheet" type="text/css" />

</head>
<body>
<div class="back2">
	<div class="container header">
		<div class="span5">
			<div class="logo">
				<a href="./网上商城/index.htm">
			<img src="picturess/logo.gif"></img>
			</a>
			</div>
		</div>
		<div class="span9">
			<div class="headerAd">
				<%-- <img src="${pageContext.request.contextPath}/image/header.jpg"
					width="320" height="50" alt="正品保障" title="正品保障" /> --%>
			</div>
		</div>

		<%@ include file="menu.jsp"%>

	</div>

	<div class="container cart">

		<div class="span24">

			<div class="step step1">
				<ul>

					<li class="current"></li>
					<li>我的订单</li>
				</ul>
			</div>


			<table>
				<tbody>
					&nbsp;&nbsp;&nbsp;<a href="${ pageContext.request.contextPath }/order_findByPay.action?state=1&page=1">查看未付款订单</a>&nbsp;&nbsp;&nbsp;
					<a href="${ pageContext.request.contextPath }/order_findByPay.action?state=2&page=1">查看已付款订单</a>&nbsp;&nbsp;&nbsp;
					<a href="${ pageContext.request.contextPath }/order_findByPay.action?state=3&page=1">查看未收货订单</a>&nbsp;&nbsp;&nbsp;
					<a href="${ pageContext.request.contextPath }/order_findByPay.action?state=4&page=1">查看完成订单</a>
					<tr><td></td></tr>
					<s:iterator var="order" value="pageBean2.list">
						<tr>
							<th colspan="5">订单编号:<s:property value="#order.oid" />&nbsp;&nbsp;&nbsp;&nbsp;订单金额:<font
								color="red"><s:property value="#order.total" />
							</font>
							&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">
								<s:if test="#order.state == 1">
									<a href="${ pageContext.request.contextPath }/order_findByOid.action?oid=<s:property value="#order.oid" />">付款</a>
								</s:if>
								<s:if test="#order.state == 2">
									已付款
								</s:if>
								<s:if test="#order.state == 3">
									<a href="${ pageContext.request.contextPath }/order_updateState.action?oid=<s:property value="#order.oid" />">确认收货</a>
								</s:if>
								<s:if test="#order.state == 4">
									交易成功
								</s:if>
							</font>
							</th>
						</tr>
						<tr>
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
						<s:iterator var="orderItem" value="#order.orderItems">
							<tr>
								<td width="60"><img
									src="${ pageContext.request.contextPath }/<s:property value="#orderItem.product.image"/>" />
								</td>
								<td><s:property value="#orderItem.product.pname" /></td>
								<td><s:property value="#orderItem.product.shop_price" /></td>
								<td class="quantity" width="60"><s:property
										value="#orderItem.count" /></td>
								<td width="140"><span class="subtotal">￥<s:property
											value="#orderItem.subtotal" />
								</span></td>
							</tr>
						</s:iterator>
					</s:iterator>
					<tr>
						<th colspan="5">
						<div class="pagination">
							<span>第<s:property value="pageBean2.page" />/<s:property
								value="pageBean2.totalPage" />页 </span>
								<s:if test="pageBean2.page != 1">
								<a
									href="${ pageContext.request.contextPath }/order_findByPay.action?page=1&state=<s:property value="pageBean2.state"/>"
									class="firstPage">&nbsp;</a>
								<a
									href="${ pageContext.request.contextPath }/order_findByPay.action?page=<s:property value="pageBean2.page-1"/>&state=<s:property value="pageBean2.state"/>"
									class="previousPage">&nbsp;</a>
							</s:if> <s:iterator var="i" begin="1" end="pageBean2.totalPage">
								<s:if test="pageBean2.page != #i">
									<a
										href="${ pageContext.request.contextPath }/order_findByPay.action?page=<s:property value="#i"/>&state=<s:property value="pageBean2.state"/>"><s:property
											value="#i" />
									</a>
								</s:if>
								<s:else>
									<span class="currentPage"><s:property value="#i" />
									</span>
								</s:else>
							</s:iterator> <s:if test="pageBean2.page != pageBean2.totalPage">
								<a class="nextPage"
									href="${ pageContext.request.contextPath }/order_findByPay.action?page=<s:property value="pageBean2.page+1"/>&state=<s:property value="pageBean2.state"/>">&nbsp;</a>
								<a class="lastPage"
									href="${ pageContext.request.contextPath }/order_findByPay.action?page=<s:property value="pageBean2.totalPage"/>&state=<s:property value="pageBean2.state"/>">&nbsp;</a>
							</s:if>
							</div>
							</th>
					</tr>
				</tbody>
			</table>


		</div>

	</div>
	<!-- <div class="container footer">
		<div class="span24">
			<div class="footerAd">
				<img src="image\r___________renleipic_01/footer.jpg" alt="我们的优势"
					title="我们的优势" height="52" width="950">
			</div>
		</div>
		<div class="span24">
			<ul class="bottomNav">
				<li><a href="#">关于我们</a> |</li>
				<li><a href="#">联系我们</a> |</li>
				<li><a href="#">诚聘英才</a> |</li>
				<li><a href="#">法律声明</a> |</li>
				<li><a>友情链接</a> |</li>
				<li><a target="_blank">支付方式</a> |</li>
				<li><a target="_blank">配送方式</a> |</li>
				<li><a>SHOP++官网</a> |</li>
				<li><a>SHOP++论坛</a></li>
			</ul>
		</div>
		<div class="span24">
			<div class="copyright">Copyright © 2005-2015 网上商城 版权所有</div>
		</div> -->
	</div></div>
</body>
</html>