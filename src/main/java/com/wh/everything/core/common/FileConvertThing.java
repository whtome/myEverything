package com.wh.everything.core.common;

import com.wh.everything.core.model.FileType;
import com.wh.everything.core.model.Thing;

import java.io.File;

public final class FileConvertThing {

    public static Thing convert(File file) {
        Thing thing = new Thing();
        thing.setName(file.getName());
        thing.setPath(file.getAbsolutePath());
        thing.setDepth(computerFileDepth(file));
        thing.setFileType(computerFileType(file));
        return thing;
    }

    private static int computerFileDepth(File file) {

        int dept = 0;
        String[] segments = file.getAbsolutePath().split("\\\\");
        dept = segments.length;
        return dept;

    }

    private static FileType computerFileType(File file) {

        if (file.isDirectory()) {
            return FileType.OTHER;
        }
        String fileName = file.getName();
        int index = file.getName().lastIndexOf(".");
        if (index != -1 && index < fileName.length() - 1) {
            String extend = fileName.substring(index + 1);
            return FileType.lookup(extend);
        }else {
            return FileType.OTHER;
        }

    }

}
