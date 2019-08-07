package com.wh.everything.core.interceptor.impl;


import com.wh.everything.core.interceptor.FileInterceptor;

import java.io.File;

public class FilePrintInterceptor implements FileInterceptor {
    @Override
    public void apply(File file) {

        System.out.println(file.getAbsolutePath());
    }
}
