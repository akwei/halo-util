package halo.akwei.retry;

public class RetryRobot {

	/**
	 * 当前重试次数
	 */
	private int retry = 0;

	private int maxRetry;

	public RetryRobot(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	public int getRetry() {
		return retry;
	}

	public <T> T invoke(RetryCall<T> retryCall) throws RetryFailException {
		while (true) {
			try {
				return retryCall.execute();
			}
			catch (Exception e) {
				if (e instanceof NotNeedRetryException) {
					if (e.getCause() == null) {
						throw new RetryFailException(e);
					}
					throw new RetryFailException(e.getCause());
				}
				if (this.retry >= this.maxRetry) {
					throw new RetryFailException(e);
				}
				this.retry++;
			}
		}
	}

	public static <T> T invoke(int maxRetry, RetryCall<T> retryCall) throws RetryFailException {
		RetryRobot retryRobot = new RetryRobot(maxRetry);
		return retryRobot.invoke(retryCall);
	}
}
