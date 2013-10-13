package halo.akwei.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created with IntelliJ IDEA.
 * User: akwei
 * Date: 13-6-5
 * Time: PM5:40
 * To change this template use File | Settings | File Templates.
 */
public class SpringUtil implements ApplicationContextAware {

    private static SpringUtil ins;
    private ApplicationContext applicationContext;

    public static SpringUtil instance() {
        return ins;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
        ins = this;
    }

    public Object getBean(String name) {
        try {
            return applicationContext.getBean(name);
        } catch (BeansException e) {
            return null;
        }
    }


}
