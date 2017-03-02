package com.cvnavi.logistics.i51ehang.app.activity.employee.myutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by george on 2016/11/28.
 */

public class FileUtils {

    /**
     * 创建文件目录(文件的父文件夹)
     *
     * @param filePath
     */
    public static void createFileDir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            parentFile = null;
        }
        file = null;
    }

    /**
     * 写入文件(如有原文件,不删除原文件,新内容追加到原文件)
     * 写入文件（追加内容）；
     * @param path
     *            文件名
     * @param content
     *            内容
     */
    public static void writeFileOfNoDelExist(String path, String content) {
        FileUtils.createFileDir(path);

        String s = new String();
        String s1 = new String();
        // 构建文件路径
        // String filePath = getSDCardPath() + path;
        try {
            File f = new File(path);
            if (f.exists()) {
            } else {
                f.createNewFile();
            }

            FileInputStream ios = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(ios, "UTF-8");
            BufferedReader input = new BufferedReader(isr);
            while ((s = input.readLine()) != null) {
                s1 += s + "\r\n";
            }
            input.close();
            s1 += content;

            FileOutputStream fos = new FileOutputStream(f);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(s1);
            out.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件内容
     *
     * @param fs
     *            文件输入流
     * @param encoding
     *            文本文件打开的编码方式
     * @return 返回文本文件的内容
     */
    public static String readFile(String path, String encoding) {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        try {
            FileInputStream fs = new FileInputStream(path);
            InputStreamReader isr = null;
            if (encoding.equals("") || encoding == null) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data = "";
                while ((data = br.readLine()) != null) {
                    str.append(data + "\r\n");
                }
            } catch (Exception e) {
                str.append(e.toString());
            }
            st = str.toString();
            str = null;
        } catch (IOException es) {
            st = "";
        }
        return st;
    }

}
