package halo.akwei.util;

public class PageHelper2 {

	private int size;

	private int page;

	private int begin;

	public PageHelper2(int size, int page) {
		this.size = size;
		this.page = page;
		this.begin = (page - 1) * size;
	}

	public int getSize() {
		return size;
	}

	public int getBegin() {
		return begin;
	}

	public int getPage() {
		return page;
	}
}
