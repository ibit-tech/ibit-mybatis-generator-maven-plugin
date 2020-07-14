package tech.ibit.mybatis.generator.plugin;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import tech.ibit.mybatis.generator.Generator;
import tech.ibit.mybatis.generator.ProjectInfo;

import java.io.File;

/**
 * 生成器
 *
 * @author IBIT程序猿
 */
@Mojo(name = "generate")
public class GeneratorMojo extends AbstractMojo {

    /**
     * jdbc driver name
     */
    @Parameter(name = "driverName", required = true)
    private String driverName;

    /**
     * jdbc url
     */
    @Parameter(name = "jdbcUrl", required = true)
    private String jdbcUrl;

    /**
     * 数据库用户
     */
    @Parameter(name = "username", required = true)
    private String username;

    /**
     * 数据库密码
     */
    @Parameter(name = "password", required = true)
    private String password;

    /**
     * 默认项目文件夹
     */
    @Parameter(name = "defaultProjectDir", defaultValue = "${project.basedir}")
    private File defaultProjectDir;

    /**
     * 默认包名
     */
    @Parameter(name = "defaultBasePackage")
    private String defaultBasePackage;

    /**
     * 实体项目文件夹
     */
    @Parameter(name = "entityProjectDir")
    private File entityProjectDir;

    /**
     * 实体包名
     */
    @Parameter(name = "entityBasePackage")
    private String entityBasePackage;

    /**
     * Mapper项目文件夹
     */
    @Parameter(name = "mapperProjectDir")
    private File mapperProjectDir;

    /**
     * Mapper包名
     */
    @Parameter(name = "mapperBasePackage")
    private String mapperBasePackage;

    /**
     * 作者
     */
    @Parameter(name = "author")
    private String author;

    /**
     * 生成全部（包含 entity 和 mapper）
     */
    @Parameter(name = "withAll", defaultValue = "false")
    private boolean withAll;

    /**
     * 生成实体
     */
    @Parameter(name = "withEntity", defaultValue = "false")
    private boolean withEntity;

    /**
     * 生成 Mapper
     */
    @Parameter(name = "withMapper", defaultValue = "false")
    private boolean withMapper;

    /**
     * 是否覆盖（false，文件不存在则不覆盖）
     */
    @Parameter(name = "override", defaultValue = "false")
    private boolean override;

    /**
     * 表对象
     */
    @Parameter(name = "tables", required = true)
    private String tables;

    @Override
    public void execute() {

        Generator generator = new Generator(driverName, jdbcUrl, username, password, tables);

        ProjectInfo defaultProject = new ProjectInfo(defaultProjectDir.getAbsolutePath(), defaultBasePackage);
        generator.setDefaultProject(defaultProject);

        // entity项目信息
        if (null != entityProjectDir || StringUtils.isNotBlank(entityBasePackage)) {
            generator.setEntityProject(
                    new ProjectInfo(
                            getPath(entityProjectDir, defaultProjectDir),
                            getString(entityBasePackage, defaultBasePackage)
                    ));
        }

        // mapper项目信息
        if (null != mapperProjectDir || StringUtils.isNotBlank(mapperBasePackage)) {
            generator.setMapperProject(
                    new ProjectInfo(
                            getPath(mapperProjectDir, defaultProjectDir),
                            getString(mapperBasePackage, defaultBasePackage)
                    ));
        }

        generator.setAuthor(StringUtils.trimToEmpty(author));
        generator.setOverride(override);

        if (withAll) {
            generator.setWithAll();
        } else {
            generator.setWithEntity(withEntity);
            generator.setWithMapper(withMapper);
        }

        // 生成文件
        generator.generateFiles();

    }

    private String getString(String value, String defaultValue) {
        return StringUtils.isNotBlank(value) ? value.trim() : defaultValue.trim();
    }


    private String getPath(File file, File defaultFile) {
        return null != file ? file.getAbsolutePath() : defaultFile.getAbsolutePath();
    }
}
