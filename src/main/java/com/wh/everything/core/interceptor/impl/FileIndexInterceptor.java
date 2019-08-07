package com.wh.everything.core.interceptor.impl;

import com.wh.everything.core.common.FileConvertThing;
import com.wh.everything.core.dao.FileIndexDao;
import com.wh.everything.core.interceptor.FileInterceptor;
import com.wh.everything.core.model.Thing;

import java.io.File;

public class FileIndexInterceptor implements FileInterceptor {

    private final FileIndexDao fileIndexDao;

    public FileIndexInterceptor(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
    }

    @Override
    public void apply(File file) {
        Thing thing = FileConvertThing.convert(file);
        System.out.println("Thing -->" + thing);
    }
}
