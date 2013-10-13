package halo.akwei.util;

/**
 * Created with IntelliJ IDEA. User: akwei Date: 13-6-8 Time: PM1:32 To change
 * this template use File | Settings | File Templates.
 */
public class PageHelper {

	private int totalPage;

	private int totalCount;

	private int size;

	private int showPageSize = 10;

	private int page;

	private int showBeginPage;

	private int showEndPage;

	private int begin;

	public PageHelper(int totalCount, int size, int showPageSize, int page) {
		this.totalCount = totalCount;
		this.size = size;
		this.showPageSize = showPageSize;
		this.page = page;
		this.begin = (page - 1) * size;
		this.totalPage = (this.totalCount + this.size - 1) / this.size;
		if (this.totalPage <= this.showPageSize) {
			this.showBeginPage = 1;
			this.showEndPage = this.totalPage;
		}
		else {
			int leftSize = (this.showPageSize - 1) / 2;
			int rightSize = leftSize;
			if ((this.showPageSize - 1) % 2 != 0) {
				rightSize++;
			}
			this.showBeginPage = this.page - leftSize;
			this.showEndPage = this.page + rightSize;
			int n_1 = 1 - this.showBeginPage;
			if (n_1 > 0) {
				this.showBeginPage = this.showBeginPage + n_1;
				this.showEndPage = this.showEndPage + n_1;
			}
			if (this.showEndPage > this.totalPage) {
				this.showBeginPage = this.showBeginPage - (this.showEndPage - this.totalPage);
				this.showEndPage = this.totalPage;
			}
			if (this.showBeginPage < 1) {
				this.showBeginPage = 1;
			}
		}
	}

	public int getSize() {
		return size;
	}

	public int getBegin() {
		return begin;
	}

	public int getShowBeginPage() {
		return showBeginPage;
	}

	public int getShowEndPage() {
		return showEndPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getPage() {
		return page;
	}
}
