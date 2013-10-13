package halo.akwei.retry;

/**
 * 当重试次数到达最大不能再次重试或认为不需要再次重试，可抛出此异常
 * 
 * @author akwei
 */
public class RetryFailException extends Exception {

	private static final long serialVersionUID = 5496796038299786120L;

	public RetryFailException() {
	}

	public RetryFailException(String s) {
		super(s);
	}

	public RetryFailException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public RetryFailException(Throwable throwable) {
		super(throwable);
	}
}
