package com.wh.everything.config;


import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.Getter;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public class EverythingPlusConfig {

    private static volatile EverythingPlusConfig config;

    //建立索引的路径
    private Set<String> includPath = new HashSet<>();

    //排除索引文件的路径
    private Set<String> excludPath = new HashSet<>();

    private EverythingPlusConfig(){}

    public static EverythingPlusConfig getInstance() {

        if (config == null) {
            synchronized (EverythingPlusConfig.class) {
                if (config == null) {
                    config = new EverythingPlusConfig();
                    //1. 获取文件系统
                    FileSystem fileSystem = FileSystems.getDefault();
                    //遍历的目录
                    Iterable<Path> iterable = fileSystem.getRootDirectories();
                    iterable.forEach(path -> config.includPath.add(path.toString()));
                    //排除的目录
                    // C:\Windows  C:\Program Files (x86)   C:\Program Files C:\ProgramData
                    String osname = System.getProperty("os.name");
                    if (osname.startsWith("Windows")) {
                        config.getExcludPath().add("C:\\Windows");
                        config.getExcludPath().add("C:\\Program Files (x86)");
                        config.getExcludPath().add("C:\\Program Files");
                        config.getExcludPath().add("C:\\ProgramData");
                    }else {
                        config.getExcludPath().add("/tmp");
                        config.getExcludPath().add("/etc");
                        config.getExcludPath().add("/root");
                    }
                }
            }
        }
        return config;
    }
}
