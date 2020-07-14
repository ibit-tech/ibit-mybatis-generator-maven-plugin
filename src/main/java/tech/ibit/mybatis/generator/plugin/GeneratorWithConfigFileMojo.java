package tech.ibit.mybatis.generator.plugin;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import tech.ibit.mybatis.generator.Generator;
import tech.ibit.mybatis.generator.ProjectInfo;
import tech.ibit.mybatis.generator.plugin.utils.PropertyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 生成器
 *
 * @author IBIT程序猿
 */
@Mojo(name = "generate-with-config")
public class GeneratorWithConfigFileMojo extends AbstractMojo {


    @Parameter(name = "configFile", required = true)
    private File configFile;

    /**
     * 获取配置文件
     *
     * @return 配置
     * @throws IOException IO异常
     */
    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fin = new FileInputStream(configFile)) {
            properties.load(fin);
            return properties;
        }
    }

    @Override
    public void execute() {

        try {
            Properties properties = getProperties();

            Generator generator = new Generator(
                    PropertyUtils.getString(properties, "driverName"),
                    PropertyUtils.getString(properties, "jdbcUrl"),
                    PropertyUtils.getString(properties, "username"),
                    PropertyUtils.getString(properties, "password"),
                    PropertyUtils.getString(properties, "tables")
            );

            String defaultProjectDir = PropertyUtils.getString(properties, "defaultProjectDir");
            String defaultBasePackage = PropertyUtils.getString(properties, "defaultBasePackage");

            ProjectInfo defaultProject = new ProjectInfo(defaultProjectDir, defaultBasePackage);
            generator.setDefaultProject(defaultProject);

            String entityProjectDir = PropertyUtils.getString(properties, "entityProjectDir");
            String entityBasePackage = PropertyUtils.getString(properties, "entityBasePackage");

            // entity项目信息
            if (StringUtils.isNotBlank(entityProjectDir) || StringUtils.isNotBlank(entityBasePackage)) {
                generator.setEntityProject(
                        new ProjectInfo(
                                getString(entityProjectDir, defaultProjectDir),
                                getString(entityBasePackage, defaultBasePackage)
                        ));
            }

            String mapperProjectDir = PropertyUtils.getString(properties, "mapperProjectDir");
            String mapperBasePackage = PropertyUtils.getString(properties, "mapperBasePackage");

            // mapper项目信息
            if (StringUtils.isNotBlank(mapperProjectDir) || StringUtils.isNotBlank(mapperBasePackage)) {
                generator.setMapperProject(
                        new ProjectInfo(
                                getString(mapperProjectDir, defaultProjectDir),
                                getString(mapperBasePackage, defaultBasePackage)
                        ));
            }

            generator.setAuthor(PropertyUtils.getString(properties, "author"));
            generator.setOverride(PropertyUtils.getBoolean(properties, "override"));

            boolean withAll = PropertyUtils.getBoolean(properties, "withAll");
            if (withAll) {
                generator.setWithAll();
            } else {
                generator.setWithEntity(PropertyUtils.getBoolean(properties, "withEntity"));
                generator.setWithMapper(PropertyUtils.getBoolean(properties, "withMapper"));
            }

            // 生成文件
            generator.generateFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getString(String value, String defaultValue) {
        return StringUtils.isNotBlank(value) ? value.trim() : defaultValue.trim();
    }


}
