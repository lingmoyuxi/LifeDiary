package com.example.lifediary.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.example.lifediary.controller.BaseController;
import com.example.lifediary.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述:代码生成器
 * 日期: 2023/2/5 10:17:16
 *
 * @author MoYuXi
 */
public class AutoGenerator {
    private static final String URL = "jdbc:mysql://localhost:3306/lifediary?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();
        tables.add("user");
        tables.add("diary");
        tables.add("resource");

        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> builder.author(System.getProperty("user.name"))
                        .outputDir(System.getProperty("user.dir") + "\\src\\main\\java")
                        .enableSwagger()
                        .commentDate("yyyy-MM-dd")
                        .disableOpenDir()).packageConfig(builder -> builder.parent("com.example.lifediary")
                        .moduleName("")
                        .entity("entity")
                        .service("service")
                        .serviceImpl("serviceImpl")
                        .controller("controller")
                        .mapper("mapper")
                        .xml("mapper")

                        //自行移动至java下的mapper中
                        .pathInfo(Collections.singletonMap(OutputFile.mapper, System.getProperty("user.dir") + "\\src\\main\\resources\\mapper")))
                .strategyConfig(builder -> builder.addInclude(tables)
                        .serviceBuilder()
                        .enableFileOverride()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")

                        .entityBuilder()
                        .enableFileOverride()
                        .superClass(BaseEntity.class)
                        //                            .addTableFills(new Column("create_time"), FieldFill.INSERT)
                        //                            .addTableFills(new Column("create_time"), FieldFill.UPDATE)
                        .enableLombok()
                        .logicDeleteColumnName("deleted")
                        .enableTableFieldAnnotation()

                        .controllerBuilder()
                        //                            .enableFileOverride()
                        .superClass(BaseController.class)
                        .formatFileName("%sController")
                        .enableRestStyle()

                        .mapperBuilder()
                        .enableFileOverride()
                        .enableBaseColumnList()

                        .superClass(BaseMapper.class)
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                        .enableMapperAnnotation()).templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
