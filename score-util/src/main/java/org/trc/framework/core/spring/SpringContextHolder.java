package org.trc.framework.core.spring;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 *@description 获取bean操作类
 * @author Created by hzwzhen on 2017/6/5.
 */

public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public SpringContextHolder() {
    }

    /**
     * @Description 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextHolder.applicationContext == null) {
            SpringContextHolder.applicationContext = applicationContext;
        }

    }

    /**
     * @Description 取得存储在静态变量中的ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * @Description 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
     */
    public static Object getBean(String name) {
        checkApplicationContext();
        return applicationContext.getBean(name);
    }

    /**
     * @Description 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBean(clazz);
    }

    public static <T> T getFirstBean(Class<T> clazz) {
        Map<String, T> map = getBeans(clazz);
        return map != null && !map.isEmpty() ? map.values().iterator().next() : null;
    }

    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBeansOfType(clazz);
    }

    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    /**
     * @Description 检查applicationContext是否注入
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义 ");
        }
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }

    public static DefaultListableBeanFactory getBeanFactory() {
        return (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }

    public static String[] getBeanNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    public static void registerBean(String beanName, Class<?> clazz, Map<String, Object> properties) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setPropertyValues(new MutablePropertyValues(properties));
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        acf.registerBeanDefinition(beanName, beanDefinition);
    }

    public static void removeBean(String beanName) {
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        acf.removeBeanDefinition(beanName);
    }

}
