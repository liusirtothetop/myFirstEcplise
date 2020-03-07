package com.liusir.bookstore.web;

import java.util.List;
public class Page<T> {

	//得到当前的页数
	private int pageNo;
    //page本身的list ，一页内书的信息
	private List<T> list;
   //每页的最大数量
	private int pageSize=3;
   //总的条数
	private long toltaItemNumber;

	public Page(int pageNo) {

		this.pageNo = pageNo;
	}

	//需要校验一下当前的页码
	public int getPageNo() {
		if (pageNo < 0) {
			pageNo = 1;
		}

		if (pageNo > toltaItemNumber) {
			pageNo = getTotalPageNumber();
		}

		return pageNo;
	}

	 public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
	public int getPageSize() {
		return pageSize;
	}

	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	//获取总的页数
	public int getTotalPageNumber() {

		int totalPageNumber = (int) toltaItemNumber / pageSize;
		if (toltaItemNumber % pageSize != 0) {
			totalPageNumber++;
		}
		return totalPageNumber;
	}

	public void setToltaItemNumber(long toltaItemNumber) {
		this.toltaItemNumber = toltaItemNumber;
	}

	public boolean isHasNext() {
		if (getPageNo() < getTotalPageNumber()) {
			return true;
		}
		return false;
	}

	public boolean isHasPrev() {
		if (getPageNo() > 1) {
			return true;
		}

		return false;
	}

	public int getPrevPage() {
		if (isHasPrev()) {
			return getPageNo() - 1;
		}

		return getPageNo();
	}

	public int getNextPage() {
		if (isHasNext()) {
			return getPageNo() + 1;
		}

		return getPageNo();
	}

}
