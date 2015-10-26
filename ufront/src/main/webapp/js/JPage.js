function JPage(pageId) {
	//当前页前和后显示的页数
	this.leftRightPage = 2;
	// 总记录数
	this.totalRows;
	// 总页数
	this.pageNum;
	// 分页在最前端和最后端要显示的页数
	this.step = 7;
	// 每页记录数
	this.pageSize = 10;
	// 当前页
	this.currentPage = 1;
	// 查询
	this.query = {};
	// 公用字符
	this.pageId = pageId;
	
	//区分页码样式
	this.pageStyle=0;

	this.fun;
	this.init = function(totalRows) {
		// 设置总记录数
		this.totalRows = totalRows;
		// 计算总页数
		this.pageNum = Math.ceil(this.totalRows / this.pageSize);
	};

	// 获得起始记录索引
	this.getFirst = function() {
		return this.pageSize * (this.currentPage - 1);
	};

	// 跳转到页
	this.goToPage = function(currentPage) {
		this.currentPage = currentPage;
		if(this.pageStyle == 0){
			this.createNavigator();
		}else if(this.pageStyle == 1){
			this.createGoodsNavigator();
		}else{
			this.createNewNavigator();
		}
		
		if (this.fun != null) {
			this.fun();
		}
	};

	
	
	
	this.createNavigator = function() {
		if (this.pageNum == 0) {
			return "";
		}
		var sb = [];
		

		if (this.currentPage <= 1) {
			sb.push("<span class=\"left_g\">上一页</span>");
		} else {
			sb.push("<span class=\"left_g\" " +
					"onclick='" + this.pageId + ".goToPage("
					+ (this.currentPage - 1) + ")' >上一页</span>");
		}
		sb.push(" <span>");
		
		if (this.pageNum <= 8) {
			for (var i = 1; i <= this.pageNum; i++) {
				if (i == this.currentPage) {
					sb.push("<a>" + i + "</a>");
				} else {
					sb.push("<a onclick='" + this.pageId + ".goToPage(" + i + ")'>" + i
							+ "</a>");
				}
			}
		} else {
			

				if (this.currentPage > this.pageNum - this.step) {
					
					sb.push("<a onclick='" + this.pageId + ".goToPage(1)'>1</a>...");
					for (var i = this.pageNum - this.step; i <= this.pageNum; i++) {
						if (i == this.currentPage) {
							sb.push("<a>" + i + "</a>");
						} else {
							sb.push("<a onclick='" + this.pageId + ".goToPage(" + i + ")'>" + i
									+ "</a>");
						}
					}
				}
				else if(this.currentPage < this.step){
					for ( var i = 1; i <= this.step + 1; i++) {
						if (i == this.currentPage) {
							sb.push("<a>" + i + "</a>");
						} else {
							sb.push("<a onclick='" + this.pageId + ".goToPage(" + i + ")'>" + i
									+ "</a>");
						}
					}
					sb.push("<a onclick='" + this.pageId + ".goToPage(" + this.pageNum + ")'>" + this.pageNum
							+ "</a>");
				}
				else {
					sb.push("<a onclick='" + this.pageId + ".goToPage(1)'>1</a>...");
					for (var i = this.currentPage - this.leftRightPage; i <= this.currentPage + this.leftRightPage; i++) {
						if (i == this.currentPage) {
							sb.push("<a>" + i + "</a>");

						} else {
							sb.push("<a onclick='" + this.pageId + ".goToPage(" + i + ")'>" + i
									+ "</a>");
						}
					}
					sb.push("<a onclick='" + this.pageId + ".goToPage(" + this.pageNum + ")'>" + this.pageNum
							+ "</a>");
				}
		}
		
		sb.push(" </span>");
		// 下一页
		if (this.currentPage == this.pageNum) {
			sb.push("<span class=\"left_d\">下一页</span>");
		} else {
			
			sb.push("<span class=\"left_d\" " +
					"onclick='" + this.pageId + ".goToPage("
					+ (this.currentPage + 1) + ")'>下一页</span>");
		}
		document.getElementById(this.pageId).innerHTML = sb.join("");

	};
	
	// 刷新页面数据
	this.refreshData = function(fun) {
		this.fun = fun;
	};
	
	
	this.createNewNavigator = function() {
		if (this.pageNum == 0) {
			return "";
		}
		var sb = [];

		if (this.currentPage <= 1) {
			sb.push("<a href=\"javascript:;\" class=\"pagesHome\" >首页</a>");
			sb.push("<a href=\"javascript:;\" class=\"pagesPav\" >上一页</a>");
		} else {
			
			
			sb.push("<a class=\"pagesHome\" " +
					"onclick='" + this.pageId + ".goToPage("
					+ (1) + ")' >首页</a>");
			sb.push("<a class=\"pagesPav\" " +
					"onclick='" + this.pageId + ".goToPage("
					+ (this.currentPage - 1) + ")' >上一页</a>");
		}
		//sb.push("<ul class=\"pagesList\">");
		
		if (this.pageNum <= 8) {
			
			for (var i = 1; i <= this.pageNum; i++) {
				if (i == this.currentPage) {
					sb.push("<a  class=\"tzjl_pages_dangqianye gerneralPage\" >"+i+"</a>")
				} else {
					sb.push("<a   onclick='" + this.pageId + ".goToPage(" + i + ")' class=\" gerneralPage\" >"+i+"</a>");
				}
			}
		} else {
			

				if (this.currentPage > this.pageNum - this.step) {
					
					sb.push("<a onclick='" + this.pageId + ".goToPage(1)' class=\" gerneralPage\" >1</a>...");
					for (var i = this.pageNum - this.step; i <= this.pageNum; i++) {
						if (i == this.currentPage) {
							sb.push("<a  class=\"tzjl_pages_dangqianye gerneralPage\" >"+i+"</a>")
						} else {
							sb.push("<a   onclick='" + this.pageId + ".goToPage(" + i + ")' class=\" gerneralPage\" >"+i+"</a>");
						}
					}
				}
				else if(this.currentPage < this.step){
					for ( var i = 1; i <= this.step + 1; i++) {
						if (i == this.currentPage) {
							sb.push("<a  class=\"tzjl_pages_dangqianye gerneralPage\" >"+i+"</a>")
						} else {
							sb.push("<a   onclick='" + this.pageId + ".goToPage(" + i + ")' class=\" gerneralPage\" >"+i+"</a>");
						}
					}
					sb.push("...<a   onclick='" + this.pageId + ".goToPage(" +this.pageNum + ")' class=\" gerneralPage\" >"+this.pageNum+"</a>");
				}
				else {
					sb.push("<a onclick='" + this.pageId + ".goToPage(1)' class=\" gerneralPage\" >1</a>...");
					for (var i = this.currentPage - this.leftRightPage; i <= this.currentPage + this.leftRightPage; i++) {
						if (i == this.currentPage) {
							sb.push("<a  class=\"tzjl_pages_dangqianye gerneralPage\" >"+i+"</a>")

						} else {
							sb.push("<a   onclick='" + this.pageId + ".goToPage(" + i + ")' class=\" gerneralPage\" >"+i+"</a>");
						}
					}
					sb.push("<a   onclick='" + this.pageId + ".goToPage(" +this.pageNum + ")' class=\" gerneralPage\" >"+this.pageNum+"</a>");
				}
		}
		
		//sb.push(" </ul>");
		
		// 下一页
		if (this.currentPage == this.pageNum) {
			sb.push("<a  class=\"pagesNext\" >下一页</a>");
			sb.push("<a class=\"pagesLast\" >尾页</a>");
		} else {
			
			sb.push("<a class=\"pagesNext\" " +
					"onclick='" + this.pageId + ".goToPage("
					+ (this.currentPage + 1) + ")'>下一页</a>");
			sb.push("<a class=\"pagesLast\" " +
					"onclick='" + this.pageId + ".goToPage("
					+ (this.pageNum) + ")'>尾页</a>");
		}
		document.getElementById(this.pageId).innerHTML = sb.join("");

	};
	

	
this.createGoodsNavigator = function() {
	if (this.pageNum == 0) {
		return "";
	}
	var sb = [];
	

	if (this.currentPage <= 1) {
		sb.push("<a  class=\"page-prev\">上一页</a>");
	} else {
		sb.push("<a  class=\"page-prev\" " +
				"onclick='" + this.pageId + ".goToPage("
				+ (this.currentPage - 1) + ")' >上一页</a>");
	}
	
	if (this.pageNum <= 8) {
		for (var i = 1; i <= this.pageNum; i++) {
			if (i == this.currentPage) {
				sb.push("<a class=\"current\">" + i + "</a>");
			} else {
				sb.push("<a onclick='" + this.pageId + ".goToPage(" + i + ")'>" + i
						+ "</a>");
			}
		}
	} else {
		

			if (this.currentPage > this.pageNum - this.step) {
				
				sb.push("<a onclick='" + this.pageId + ".goToPage(1)'>1</a>...");
				for (var i = this.pageNum - this.step; i <= this.pageNum; i++) {
					if (i == this.currentPage) {
						sb.push("<a class=\"current\">" + i + "</a>");
					} else {
						sb.push("<a onclick='" + this.pageId + ".goToPage(" + i + ")'>" + i
								+ "</a>");
					}
				}
			}
			else if(this.currentPage < this.step){
				for ( var i = 1; i <= this.step + 1; i++) {
					if (i == this.currentPage) {
						sb.push("<a class=\"current\" >" + i + "</a>");
					} else {
						sb.push("<a onclick='" + this.pageId + ".goToPage(" + i + ")'>" + i
								+ "</a>");
					}
				}
				sb.push("<a onclick='" + this.pageId + ".goToPage(" + this.pageNum + ")'>" + this.pageNum
						+ "</a>");
			}
			else {
				sb.push("<a onclick='" + this.pageId + ".goToPage(1)'>1</a>...");
				for (var i = this.currentPage - this.leftRightPage; i <= this.currentPage + this.leftRightPage; i++) {
					if (i == this.currentPage) {
						sb.push("<a class=\"current\">" + i + "</a>");

					} else {
						sb.push("<a onclick='" + this.pageId + ".goToPage(" + i + ")'>" + i
								+ "</a>");
					}
				}
				sb.push("<a onclick='" + this.pageId + ".goToPage(" + this.pageNum + ")'>" + this.pageNum
						+ "</a>");
			}
	}
	// 下一页
	if (this.currentPage == this.pageNum) {
		sb.push("<a  class=\"page-next\">下一页</span>");
	} else {
		
		sb.push("<a  class=\"page-next\"" +
				"onclick='" + this.pageId + ".goToPage("
				+ (this.currentPage + 1) + ")'>下一页</a>");
	}
	document.getElementById(this.pageId).innerHTML = sb.join("");

};


}
