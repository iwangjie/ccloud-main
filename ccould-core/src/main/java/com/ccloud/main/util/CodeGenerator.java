package com.ccloud.main.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Junfu on 2019-02-20
 */
class CodeGenerator {
    /**
     * 读取控制台内容
     */
    private static String scanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + "表名" + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + "表名" + "！");
    }

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();// 代码生成器

        GlobalConfig gc = new GlobalConfig();// 全局配置
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Generator");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setIdType(IdType.AUTO);
        gc.setBaseResultMap(true);//生成resultMap
        gc.setBaseColumnList(true);//在xml中生成基础列
        mpg.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();// 数据源配置
        dsc.setUrl("jdbc:mysql://localhost:3306/ccloud?useUnicode=true&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");

        mpg.setDataSource(dsc);

        PackageConfig pc = new PackageConfig();// 包配置
//        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.ccloud.main");
        mpg.setPackageInfo(pc);

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };// 自定义配置

        String templatePath = "/templates/mapper.xml.ftl";// 如果模板引擎是 freemarker
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        templateConfig.setEntity("abc.java");
//        templateConfig.setService("...");
        templateConfig.setController(null);

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.ccloud.main.entity.BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("BaseController");
        strategy.setInclude(scanner());
//        strategy.setSuperEntityColumns("id");//sb
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
