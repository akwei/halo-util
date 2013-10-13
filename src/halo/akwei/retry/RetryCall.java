package halo.akwei.retry;

public interface RetryCall<T> {

	/**
	 * 运行任务
	 * 
	 * @return
	 * @throws Exception
	 */
	T execute() throws Exception;
}
