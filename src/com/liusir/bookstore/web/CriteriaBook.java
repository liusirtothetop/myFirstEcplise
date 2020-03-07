package com.liusir.bookstore.web;

	public class CriteriaBook {

		//通过价格来进行查询的操作
		private float minPrice = 0;
		//最大
		private float maxPrice = Integer.MAX_VALUE;
		
		//这里的pageNo和page中的pageNo是保持一致的，然后进行数据库的查询操作。
		private int pageNo;

		public float getMinPrice() {
			return minPrice;
		}

		public void setMinPrice(float minPrice) {
			this.minPrice = minPrice;
		}

		public float getMaxPrice() {
			return maxPrice;
		}

		public void setMaxPrice(float maxPrice) {
			this.maxPrice = maxPrice;
		}

		public int getPageNo() {
			return pageNo;
		}

		public void setPageNo(int pageNo) {
			this.pageNo = pageNo;
		}

		public CriteriaBook(float minPrice, float maxPrice, int pageNo) {
			super();
			this.minPrice = minPrice;
			this.maxPrice = maxPrice;
			this.pageNo = pageNo;
		}
		
		
	}


