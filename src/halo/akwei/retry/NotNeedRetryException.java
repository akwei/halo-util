package halo.akwei.retry;

/**
 * 不需要重试时，抛出此异常
 * 
 * @author akwei
 */
public class NotNeedRetryException extends Exception {

	private static final long serialVersionUID = 6246210513939449781L;

	/**
	 * @param cause 真正失败的原因异常
	 */
	public NotNeedRetryException(Throwable cause) {
		super(cause);
	}
}
