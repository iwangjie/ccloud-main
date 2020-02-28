package xxxxxxxxxx;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Junfu on 2019-02-20
 */
class CodeGenerator {
    private static Properties dbProperties;
    private static String url;
    private static String DRIVER_CLASS;
    private static String USER_NAME;
    private static String PASSWORD;

    /**
     * mac系统需自己配置输出路径 "/dev/codeGen/ccloud"
     */
    private static final String OUT_DIR = "D://codeGen//ccloud-core";
    /**
     * 包路径配置
     */
    private static final String parentPath= "com.ccloud.main";

    public static void main(String[] args) throws IOException {
        dbProperties = new Properties();
        dbProperties.load(CodeGenerator.class.getClassLoader().getResourceAsStream("db.properties"));
        url = dbProperties.getProperty("url");
        DRIVER_CLASS = dbProperties.getProperty("driverClass");
        USER_NAME = dbProperties.getProperty("username");
        PASSWORD = dbProperties.getProperty("password");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(OUT_DIR);
        gc.setAuthor("ccloud");
        gc.setOpen(true);
        gc.setFileOverride(true);
        gc.setIdType(IdType.AUTO);
        //生成mapper.xml中的resultMap
        gc.setBaseResultMap(true);
        //在mapper.xml中生成基础列
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(DRIVER_CLASS);
        dsc.setUsername(USER_NAME);
        dsc.setPassword(PASSWORD);

        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();

        pc.setParent(parentPath);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return OUT_DIR + "/com/ccloud/main/mapper/xml/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("abc.java");
        // templateConfig.setService("...");
        templateConfig.setController(null);
        templateConfig.setXml(null);
        //mpg.setTemplate(templateConfig);

        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.ccloud.main.entity.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //strategy.setSuperControllerClass("BaseController");
        //strategy.setInclude(scanner());
        //strategy.setSuperEntityColumns("id");//sb
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
