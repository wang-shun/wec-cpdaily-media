package com.ilovey.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 运行此方法生成mybatis代码
 * 生成代码自动放入对应目录
 * 配置文件targetProject应从项目名称开始到要生成到的classpath
 * Created by huhaichao on 2017/5/15.
 */
public class MyBatisGeneratorRun {

    public static void main(String[] args) throws Exception {
        MyBatisGeneratorRun app = new MyBatisGeneratorRun();
        app.generator();
    }

    public void generator() throws Exception {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("generatorConfig.xml");
        System.out.println("Use mybatis-generator config file:" + this.getClass().getClassLoader().getResource("generatorConfig.xml").getPath());

        List<String> warnings = new ArrayList<String>();

        Configuration config = new ConfigurationParser(warnings).parseConfiguration(resourceAsStream);
        DefaultShellCallback callback = new DefaultShellCallback(true);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        for (String warning : warnings) {
            System.out.println(warning);
        }

    }
}
