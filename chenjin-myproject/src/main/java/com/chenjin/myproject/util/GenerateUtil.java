package com.chenjin.myproject.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 代码生成工具
 *
 * @author hefeng
 */
public class GenerateUtil {
    public static void main(String[] args) throws Exception {
        GenerateUtil g = new GenerateUtil("com.chenjin.myproject.entity", "111111");
        g.scanEntity();
    }

    String comPath;
    String comPathDot;
    String perPath = System.getProperty("user.dir")+"/chenjin-myproject" + "/src/main/java/";
    String jspPath = System.getProperty("user.dir") +"/chenjin-myproject"+"/src/main/webapp/WEB-INF/jsp/";
    String action = "111111";
    String entityPath = "com.chenjin.myproject.entity";
    String suf = "";
    /**
     * 指定生产的文件
     */
    private String fileName = "";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public GenerateUtil(String entityPath, String action, String suf) {
        if (entityPath != null)
            this.entityPath = entityPath;
        if (action != null)
            this.action = action;
        if (suf != null)
            this.suf = suf;
    }

    public GenerateUtil(String entityPath, String action) {
        if (entityPath != null)
            this.entityPath = entityPath;
        if (action != null)
            this.action = action;
    }

    public GenerateUtil(String entityPath) {
        if (entityPath != null)
            this.entityPath = entityPath;
    }

    /**
     * 读取某个文件夹下的所有文件
     */
    public boolean scanEntity() throws FileNotFoundException, IOException {
        try {
            String filepath = entityPath;
            //System.out.println(filepath);
            filepath = filepath.replaceAll("[.]", "/");
            comPath = filepath.substring(0, filepath.lastIndexOf("/")) + "/";
            comPathDot = comPath.replaceAll("[/]", ".");
            //System.out.println(comPath);
            //System.out.println(comPathDot);
            filepath = perPath + filepath;
            File file = new File(filepath + "/" + suf);
            if (!file.isDirectory()) {
                System.out.println("不是文件夹");
            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "/" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        String fileName = readfile.getName();
                        fileName = fileName.substring(0, fileName.indexOf("."));
                        System.out.println("fileName=" + fileName);
                        if (!this.fileName.equals("") && !this.fileName.equalsIgnoreCase(fileName)) {
                            continue;
                        }
                        if (action.substring(0, 1).equals("1")) {
                            generateDao(fileName);
                        }
                        if (action.substring(1, 2).equals("1")) {
                            generateDaoImpl(fileName);
                        }
                        if (action.substring(2, 3).equals("1")) {
                            generateService(fileName);
                        }
                        if (action.substring(3, 4).equals("1")) {
                            generateServiceImpl(fileName);
                        }
                        if (action.substring(4, 5).equals("1")) {
                            generateController(fileName);
                        }
                        if (action.substring(5, 6).equals("1")) {
                            generateJsp(fileName);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

    /**
     * 生成service实现文件
     *
     * @param name
     * @throws IOException
     */
    private void generateServiceImpl(String name) throws IOException {
        File serviceDir = new File(perPath + comPath + "service/impl/" + this.suf);
        if (!serviceDir.exists()) {
            System.out.println("生成service.impl包");
            serviceDir.mkdirs();
        }
        File serviceFile = new File(perPath + comPath + "service/impl/" + this.suf + "/" + name + "Service.java");
        if (!serviceFile.exists()) {
            System.out.println("生成" + name + "Service.java文件");
            serviceFile.createNewFile();

            FileOutputStream out = new FileOutputStream(serviceFile, false); //如果追加方式用true
            String sname = name.substring(0, 1).toLowerCase() + name.substring(1);
            StringBuffer sb = new StringBuffer();
            sb.append("package " + comPathDot + "service.impl" + this.suf + ";\n");
            sb.append("\n");
            sb.append("import javax.annotation.Resource;\n");
            sb.append("import org.springframework.stereotype.Service;\n");
            sb.append("import org.springframework.transaction.annotation.Transactional;\n");
            sb.append("import " + comPathDot + "dao" + this.suf + ".I" + name + "Dao;\n");
            sb.append("import " + comPathDot + "entity" + this.suf + "." + name + ";\n");
            sb.append("import " + comPathDot + "service" + this.suf + ".I" + name + "Service;\n");
            sb.append("\n");
            sb.append("@Service\n");
            sb.append("@Transactional(readOnly=true)\n");
            sb.append("public class " + name + "Service  implements I" + name + "Service{\n");
            sb.append("	private I" + name + "Dao " + sname + "Dao;\n");
            sb.append("\n");
            sb.append("	public I" + name + "Dao get" + name + "Dao() {\n");
            sb.append("		return " + sname + "Dao;\n");
            sb.append("	}\n");
            sb.append("\n");
            sb.append("	@Resource\n");
            sb.append("	public void set" + name + "Dao(I" + name + "Dao " + sname + "Dao) {\n");
            sb.append("		this." + sname + "Dao = " + sname + "Dao;\n");
            sb.append("	}\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }

    /**
     * 生成service接口文件
     *
     * @param name
     * @throws IOException
     */
    private void generateService(String name) throws IOException {
        File serviceDir = new File(perPath + comPath + "service/" + this.suf);
        if (!serviceDir.exists()) {
            System.out.println("生成service包");
            serviceDir.mkdirs();
        }
        File serviceFile = new File(perPath + comPath + "service/" + this.suf + "/I" + name + "Service.java");
        if (!serviceFile.exists()) {
            System.out.println("生成I" + name + "Service.java文件");
            serviceFile.createNewFile();

            FileOutputStream out = new FileOutputStream(serviceFile, false); //如果追加方式用true
            StringBuffer sb = new StringBuffer();
            sb.append("package " + comPathDot + "service" + this.suf + ";\n");
            sb.append("\n");
            //sb.append("import com.chenjin.common.framework.annotation.ProjectCodeFlag;\n");
            sb.append("import " + comPathDot + "entity" + this.suf + "." + name + ";\n");
            sb.append("\n");
            sb.append("public interface I" + name + "Service {\n");
            sb.append("\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }

    /**
     * 生成dao接口文件
     *
     * @param name
     * @throws IOException
     */
    private void generateDao(String name) throws IOException {
        File daoDir = new File(perPath + comPath + "dao/" + this.suf);

        if (!daoDir.exists()) {
            System.out.println("生成dao包");
            daoDir.mkdirs();
        }
        File daoFile = new File(perPath + comPath + "dao/" + this.suf + "/I" + name + "Dao.java");

        if (!daoFile.exists()) {
            System.out.println("生成I" + name + "Dao.java文件");
            daoFile.createNewFile();

            FileOutputStream out = new FileOutputStream(daoFile, false); //如果追加方式用true
            StringBuffer sb = new StringBuffer();
            sb.append("package " + comPathDot + "dao" + this.suf + ";\n");
            sb.append("\n");
            sb.append("import " + comPathDot + "entity."  + name + ";\n");
            sb.append("\n");
            sb.append("public interface I" + name + "Dao {\n");
            sb.append("\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }

    /**
     * 生成dao实现文件
     *
     * @param name
     * @throws IOException
     */
    private void generateDaoImpl(String name) throws IOException {
        File daoDir = new File(perPath + comPath + "dao/impl/" + this.suf);
        if (!daoDir.exists()) {
            System.out.println("生成dao.impl包");
            daoDir.mkdirs();
        }
        File daoFile = new File(perPath + comPath + "dao/impl/" + this.suf + "/" + name + "Dao.java");
        if (!daoFile.exists()) {
            System.out.println("生成" + name + "Dao.java文件");
            daoFile.createNewFile();

            FileOutputStream out = new FileOutputStream(daoFile, false); //如果追加方式用true
            StringBuffer sb = new StringBuffer();
            sb.append("package " + comPathDot + "dao.impl"+ ";\n");
            sb.append("\n");
            sb.append("import org.springframework.stereotype.Repository;\n");
            sb.append("import " + comPathDot + "entity"+"." + name + ";\n");
            sb.append("import " + comPathDot + "dao.I" + name + "Dao;\n");
            sb.append("\n");
            sb.append("@Repository");
            sb.append("\n");
            sb.append("public class " + name + "Dao  implements I" + name + "Dao{\n");
            sb.append("\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }

    /**
     * 生成controller文件
     *
     * @param name
     * @throws IOException
     */
    private void generateController(String name) throws IOException {
        File ctrlDir = new File(perPath + comPath + "controller/" + this.suf);
        if (!ctrlDir.exists()) {
            System.out.println("生成controller包");
            ctrlDir.mkdirs();
        }
        File ctrlFile = new File(perPath + comPath + "controller/" + this.suf + "/" + name + "Controller.java");
        if (!ctrlFile.exists()) {
            System.out.println("生成" + name + "Controller.java文件");
            ctrlFile.createNewFile();

            FileOutputStream out = new FileOutputStream(ctrlFile, false); //如果追加方式用true
            String sname = name.substring(0, 1).toLowerCase() + name.substring(1);
            StringBuffer sb = new StringBuffer();
            sb.append("package " + comPathDot + "controller" + this.suf + ";\n");
            sb.append("\n");
            sb.append("import java.util.List;\n");
            sb.append("import javax.annotation.Resource;\n");
            sb.append("import org.springframework.stereotype.Controller;\n");
            sb.append("import org.springframework.web.bind.WebDataBinder;\n");
            sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
            sb.append("import org.springframework.web.bind.annotation.RequestMethod;\n");
            sb.append("import org.springframework.web.bind.annotation.ResponseBody;\n");
            sb.append("import " + comPathDot + "entity." +  name + ";\n");
            sb.append("import " + comPathDot + "service." +"I" + name + "Service;\n");
            sb.append("\n");
            sb.append("@Controller");
            sb.append("\n");
            sb.append("@RequestMapping(\"/user/" + this.suf + "/" + sname + "\")\n");
            sb.append("public class " + name + "Controller {\n");
            sb.append("\n");
            sb.append("	@Resource\n");
            sb.append("	private I" + name + "Service " + sname + "Service;\n");
            sb.append("\n");
            sb.append("	/**\n");
            sb.append("	 * 主页\n");
            sb.append("	* @return\n");
            sb.append("	*/\n");
            sb.append("	@RequestMapping(\"\")\n");
            sb.append("	public String home(){\n");
            sb.append("		return \"" + this.suf  + sname + "/list\";\n");
            sb.append("}\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }

    /**
     * 生成jsp文件
     *
     * @param name
     * @throws IOException
     */
    private void generateJsp(String name) throws IOException {
        String sname = name.substring(0, 1).toLowerCase() + name.substring(1);
        File jspDir = new File(jspPath + sname);
        if (!jspDir.exists()) {
            System.out.println("生成jsp包");
            jspDir.mkdirs();
        }
        File jspFile = new File(jspPath + sname + "/list.jsp");
        if (!jspFile.exists()) {
            System.out.println("生成" + sname + "/list.jsp文件");
            jspFile.createNewFile();

            FileOutputStream out = new FileOutputStream(jspFile, false); //如果追加方式用true

            StringBuffer sb = new StringBuffer();
            sb.append("<!DOCTYPE HTML>\n");
            sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\" %>\n");
            sb.append("<%@taglib prefix=\"shiro\" uri=\"http://shiro.apache.org/tags\" %>\n");
            sb.append("<%@taglib prefix=\"tag\" tagdir=\"/WEB-INF/tags\" %>\n");
            sb.append("<tag:head title=\"\" />\n");
            sb.append("\n");
            sb.append("<table  id=\"tg\" ></table>\n");
            sb.append("\n");
            sb.append("</body></html>\n");
            sb.append("\n");
            sb.append("<script>\n");
            sb.append("//初始化\n");
            sb.append("$(function(){\n");
            sb.append("\n");
            sb.append("});\n");
            sb.append("\n");
            sb.append("//=============ajax===============\n");
            sb.append("</script>\n");

            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }
}
