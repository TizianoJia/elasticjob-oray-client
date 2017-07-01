package cn.jxh.job.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is the main class for using OrayClient.
 *
 * @author JiaXiaohei[i@jiaxiaohei.com]
 */
public class DdnsClient {

    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
//        context.start();
    }

}
