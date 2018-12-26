package com.chenjin.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GenerateUtil
{
    String comPath;
    String comPathDot;
    String perPath = System.getProperty("user.dir") + "/src/main/java/";
    String jspPath = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/jsp/";
    String action = "111111";
    String entityPath = "com.chenjin.pmc.entity";

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

    public static void main(String[] args) {
        try {
            GenerateUtil g = new GenerateUtil("com.chenjin.pmc.entity", "111111");
            g.scanEntity();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean scanEntity()
            throws FileNotFoundException, IOException
    {
        try
        {
            String filepath = this.entityPath;

            filepath = filepath.replaceAll("[.]", "/");
            this.comPath = (filepath.substring(0, filepath.lastIndexOf("/")) + "/");
            this.comPathDot = this.comPath.replaceAll("[/]", ".");

            filepath = this.perPath + filepath;
            File file = new File(filepath);
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
                        if (this.action.substring(0, 1).equals("1")) {
                            generateDao(fileName);
                        }
                        if (this.action.substring(1, 2).equals("1")) {
                            generateDaoImpl(fileName);
                        }
                        if (this.action.substring(2, 3).equals("1")) {
                            generateService(fileName);
                        }
                        if (this.action.substring(3, 4).equals("1")) {
                            generateServiceImpl(fileName);
                        }
                        if (this.action.substring(4, 5).equals("1")) {
                            generateController(fileName);
                        }
                        if (this.action.substring(5, 6).equals("1"))
                            generateJsp(fileName);
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

    private void generateServiceImpl(String name)
            throws IOException
    {
        File serviceDir = new File(this.perPath + this.comPath + "service/impl");
        if (!serviceDir.exists()) {
            System.out.println("生成service.impl包");
            serviceDir.mkdirs();
        }
        File serviceFile = new File(this.perPath + this.comPath + "service/impl/" + name + "Service.java");
        if (!serviceFile.exists()) {
            System.out.println("生成" + name + "Service.java文件");
            serviceFile.createNewFile();

            FileOutputStream out = new FileOutputStream(serviceFile, false);
            String sname = name.substring(0, 1).toLowerCase() + name.substring(1);
            StringBuffer sb = new StringBuffer();
            sb.append("package " + this.comPathDot + "service.impl;\n");
            sb.append("\n");
            sb.append("import javax.annotation.Resource;\n");
            sb.append("import org.springframework.stereotype.Service;\n");
            sb.append("import org.springframework.transaction.annotation.Transactional;\n");
            sb.append("import com.chenjin.common.framework.service.BaseService;\n");
            sb.append("import " + this.comPathDot + "dao.I" + name + "Dao;\n");
            sb.append("import " + this.comPathDot + "entity." + name + ";\n");
            sb.append("import " + this.comPathDot + "service.I" + name + "Service;\n");
            sb.append("\n");
            sb.append("@Service\n");
            sb.append("@Transactional(readOnly=true)\n");
            sb.append("public class " + name + "Service extends BaseService<" + name + ",Long> implements I" + name + "Service{\n");
            sb.append("\tprivate I" + name + "Dao " + sname + "Dao;\n");
            sb.append("\n");
            sb.append("\tpublic I" + name + "Dao get" + name + "Dao() {\n");
            sb.append("\t\treturn " + sname + "Dao;\n");
            sb.append("\t}\n");
            sb.append("\n");
            sb.append("\t@Resource\n");
            sb.append("\tpublic void set" + name + "Dao(I" + name + "Dao " + sname + "Dao) {\n");
            sb.append("\t\tthis." + sname + "Dao = " + sname + "Dao;\n");
            sb.append("\t\tsuper.setBaseDao(" + sname + "Dao);\n");
            sb.append("\t}\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
    }

    private void generateService(String name)
            throws IOException
    {
        File serviceDir = new File(this.perPath + this.comPath + "service");
        if (!serviceDir.exists()) {
            System.out.println("生成service包");
            serviceDir.mkdirs();
        }
        File serviceFile = new File(this.perPath + this.comPath + "service/I" + name + "Service.java");
        if (!serviceFile.exists()) {
            System.out.println("生成I" + name + "Service.java文件");
            serviceFile.createNewFile();

            FileOutputStream out = new FileOutputStream(serviceFile, false);
            StringBuffer sb = new StringBuffer();
            sb.append("package " + this.comPathDot + "service;\n");
            sb.append("\n");

            sb.append("import com.chenjin.common.framework.service.IBaseService;\n");
            sb.append("import " + this.comPathDot + "entity." + name + ";\n");
            sb.append("\n");
            sb.append("public interface I" + name + "Service extends IBaseService<" + name + ",Long>{\n");
            sb.append("\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
    }

    private void generateDao(String name)
            throws IOException
    {
        File daoDir = new File(this.perPath + this.comPath + "dao");
        if (!daoDir.exists()) {
            System.out.println("生成dao包");
            daoDir.mkdirs();
        }
        File daoFile = new File(this.perPath + this.comPath + "dao/I" + name + "Dao.java");
        if (!daoFile.exists()) {
            System.out.println("生成I" + name + "Dao.java文件");
            daoFile.createNewFile();

            FileOutputStream out = new FileOutputStream(daoFile, false);
            StringBuffer sb = new StringBuffer();
            sb.append("package " + this.comPathDot + "dao;\n");
            sb.append("\n");
            sb.append("import com.chenjin.common.framework.dao.IBaseDao;\n");
            sb.append("import " + this.comPathDot + "entity." + name + ";\n");
            sb.append("\n");
            sb.append("public interface I" + name + "Dao extends IBaseDao<" + name + ",Long>{\n");
            sb.append("\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
    }

    private void generateDaoImpl(String name)
            throws IOException
    {
        File daoDir = new File(this.perPath + this.comPath + "dao/impl");
        if (!daoDir.exists()) {
            System.out.println("生成dao.impl包");
            daoDir.mkdirs();
        }
        File daoFile = new File(this.perPath + this.comPath + "dao/impl/" + name + "Dao.java");
        if (!daoFile.exists()) {
            System.out.println("生成" + name + "Dao.java文件");
            daoFile.createNewFile();

            FileOutputStream out = new FileOutputStream(daoFile, false);
            StringBuffer sb = new StringBuffer();
            sb.append("package " + this.comPathDot + "dao.impl;\n");
            sb.append("\n");
            sb.append("import org.springframework.stereotype.Repository;\n");
            sb.append("import com.chenjin.common.framework.dao.BaseDao;\n");
            sb.append("import " + this.comPathDot + "entity." + name + ";\n");
            sb.append("import " + this.comPathDot + "dao.I" + name + "Dao;\n");
            sb.append("\n");
            sb.append("@Repository");
            sb.append("\n");
            sb.append("public class " + name + "Dao extends BaseDao<" + name + ",Long> implements I" + name + "Dao{\n");
            sb.append("\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
    }

    private void generateController(String name)
            throws IOException
    {
        File ctrlDir = new File(this.perPath + this.comPath + "controller");
        if (!ctrlDir.exists()) {
            System.out.println("生成controller包");
            ctrlDir.mkdirs();
        }
        File ctrlFile = new File(this.perPath + this.comPath + "controller/" + name + "Controller.java");
        if (!ctrlFile.exists()) {
            System.out.println("生成" + name + "Controller.java文件");
            ctrlFile.createNewFile();

            FileOutputStream out = new FileOutputStream(ctrlFile, false);
            String sname = name.substring(0, 1).toLowerCase() + name.substring(1);
            StringBuffer sb = new StringBuffer();
            sb.append("package " + this.comPathDot + "controller;\n");
            sb.append("\n");
            sb.append("import java.util.List;\n");
            sb.append("import javax.annotation.Resource;\n");
            sb.append("import org.springframework.stereotype.Controller;\n");
            sb.append("import org.springframework.web.bind.WebDataBinder;\n");
            sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
            sb.append("import org.springframework.web.bind.annotation.RequestMethod;\n");
            sb.append("import org.springframework.web.bind.annotation.ResponseBody;\n");
            sb.append("import com.chenjin.common.entity.DataGrid;\n");
            sb.append("import com.chenjin.common.entity.PageRequest;\n");
            sb.append("import com.chenjin.common.web.controller.BaseController;\n");
            sb.append("import com.chenjin.sys.dto.Message;\n");
            sb.append("import " + this.comPathDot + "entity." + name + ";\n");
            sb.append("import " + this.comPathDot + "service.I" + name + "Service;\n");
            sb.append("\n");
            sb.append("@Controller");
            sb.append("\n");
            sb.append("@RequestMapping(\"/" + sname + "\")\n");
            sb.append("public class " + name + "Controller extends BaseController{\n");
            sb.append("\n");
            sb.append("\t@Resource\n");
            sb.append("\tprivate I" + name + "Service " + sname + "Service;\n");
            sb.append("\n");
            sb.append("\t/**\n");
            sb.append("\t * 主页\n");
            sb.append("\t* @return\n");
            sb.append("\t*/\n");
            sb.append("\t@RequestMapping(\"\")\n");
            sb.append("\tpublic String home(){\n");
            sb.append("\t\treturn \"" + sname + "/list\";\n");
            sb.append("\t}\n");
            sb.append("\n");
            sb.append("\t/**\n");
            sb.append("\t * 分页查询\n");
            sb.append("\t * @param pageable\n");
            sb.append("\t * @return\n");
            sb.append("\t */\n");
            sb.append("\t@RequestMapping(\"/page\")\n");
            sb.append("\t@ResponseBody\n");
            sb.append("\tpublic DataGrid<" + name + "> page(PageRequest pageable){\n");
            sb.append("\t\tDataGrid<" + name + "> page = " + sname + "Service.query(\"\",pageable);\n");
            sb.append("\t\treturn page;\n");
            sb.append("\t}\n");
            sb.append("\n");
            sb.append("\t/**\n");
            sb.append("\t * 全部查询\n");
            sb.append("\t * @param pageable\n");
            sb.append("\t * @return\n");
            sb.append("\t */\n");
            sb.append("\t@RequestMapping(\"/list\")\n");
            sb.append("\t@ResponseBody\n");
            sb.append("\tpublic List<" + name + "> list(PageRequest pageable){\n");
            sb.append("\t\tList<" + name + "> list = " + sname + "Service.list(\"\",pageable);\n");
            sb.append("\t\treturn list;\n");
            sb.append("\t}\n");
            sb.append("\n");
            sb.append("\t/**\n");
            sb.append("\t * 新增画面\n");
            sb.append("\t * @return\n");
            sb.append("\t */\n");
            sb.append("\t@RequestMapping(value = \"/add\", method = RequestMethod.GET)\n");
            sb.append("\tpublic String add(){\n");
            sb.append("\t\treturn \"" + sname + "/add\";\n");
            sb.append("\t}\n");
            sb.append("\n");
            sb.append("\t/**\n");
            sb.append("\t * 新增\n");
            sb.append("\t * @param " + sname + "\n");
            sb.append("\t * @return\n");
            sb.append("\t*/\n");
            sb.append("\t@RequestMapping(value = \"/add\", method = RequestMethod.POST)\n");
            sb.append("\t@ResponseBody\n");
            sb.append("\tpublic Message add(" + name + " " + sname + "){\n");
            sb.append("\t\tMessage message = new Message();\n");
            sb.append("\t\ttry{\n");
            sb.append("\t\t\t" + sname + "Service.save(\"\"," + sname + ");\n");
            sb.append("\t\t}catch(Exception e){\n");
            sb.append("\t\t\te.printStackTrace();\n");
            sb.append("\t\t\tmessage.setSuccess(false);\n");
            sb.append("\t\t\tmessage.setMsg(e.getMessage());\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn  message;\n");
            sb.append("\t}\n");
            sb.append("\t\n");
            sb.append("\t/**\n");
            sb.append("\t * 修改画面\n");
            sb.append("\t * @return\n");
            sb.append("\t */\n");
            sb.append("\t@RequestMapping(value = \"/edit\", method = RequestMethod.GET)\n");
            sb.append("\tpublic String edit(){\n");
            sb.append("\t\treturn \"" + sname + "/edit\";\n");
            sb.append("\t}\n");
            sb.append("\t\n");
            sb.append("\t/**\n");
            sb.append("\t * 修改\n");
            sb.append("\t * @param " + sname + "\n");
            sb.append("\t * @return\n");
            sb.append("\t */\n");
            sb.append("\t@RequestMapping(value = \"/edit\", method = RequestMethod.POST)\n");
            sb.append("\t@ResponseBody\n");
            sb.append("\tpublic Message edit(" + name + " " + sname + "){\n");
            sb.append("\t\tMessage message = new Message();\n");
            sb.append("\t\ttry{\n");
            sb.append("\t\t\t" + sname + "Service.update(\"\"," + sname + ");\n");
            sb.append("\t\t}catch(Exception e){\n");
            sb.append("\t\t\te.printStackTrace();\n");
            sb.append("\t\t\tmessage.setSuccess(false);\n");
            sb.append("\t\t\tmessage.setMsg(e.getMessage());\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn  message;\n");
            sb.append("\t}\n");
            sb.append("\t\n");
            sb.append("\t/**\n");
            sb.append("\t * 删除\n");
            sb.append("\t * @param id\n");
            sb.append("\t * @return\n");
            sb.append("\t */\n");
            sb.append("\t@RequestMapping(value = \"/del\", method = RequestMethod.POST)\n");
            sb.append("\t@ResponseBody\n");
            sb.append("\tpublic Message del(Long id){\n");
            sb.append("\t\tMessage message = new Message();\n");
            sb.append("\t\ttry{\n");
            sb.append("\t\t\t" + sname + "Service.delete(\"\",id);\n");
            sb.append("\t\t}catch(Exception e){\n");
            sb.append("\t\t\te.printStackTrace();\n");
            sb.append("\t\t\tmessage.setSuccess(false);\n");
            sb.append("\t\t\tmessage.setMsg(e.getMessage());\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn  message;\n");
            sb.append("\t}\n");
            sb.append("\t\n");
            sb.append("\t@Override\n");
            sb.append("\tprotected void init(WebDataBinder arg0) {}\n");
            sb.append("\t\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
    }

    private void generateJsp(String name)
            throws IOException
    {
        String sname = name.substring(0, 1).toLowerCase() + name.substring(1);
        File jspDir = new File(this.jspPath + sname);
        if (!jspDir.exists()) {
            System.out.println("生成jsp包");
            jspDir.mkdirs();
        }
        File jspFile = new File(this.jspPath + sname + "/list.jsp");
        if (!jspFile.exists()) {
            System.out.println("生成" + sname + "/list.jsp文件");
            jspFile.createNewFile();

            FileOutputStream out = new FileOutputStream(jspFile, false);

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
            sb.append("function delAjax(){\n");
            sb.append("\tvar selrow = $('#dg').datagrid('getSelected');\n");
            sb.append("\tif(selrow == null) return;\n");
            sb.append("\t$.messager.confirm('确认信息', '确认要删除此xx?', function(r){\n");
            sb.append("\t\tif (r){\n");
            sb.append("\t\t\t$.ajax({\n");
            sb.append("\t\t\t\turl:\"${pageContext.request.contextPath }/" + sname + "/del.htmlx\",\n");
            sb.append("\t\t\t\tdata:\"id=\"+id,\n");
            sb.append("\t\t\t\tdataType:\"json\",\n");
            sb.append("\t\t\t\ttype:\"POST\",\n");
            sb.append("\t\t\t\tcache:false,\n");
            sb.append("\t\t\t\tsuccess:function(data){\n");
            sb.append("\t\t\t\t\tif(data.success){\n");
            sb.append("\t\t\t\t\t\tshowMsg(\"删除成功！\");\n");
            sb.append("\t\t\t\t\t} \n");
            sb.append("\t\t\t\t},\n");
            sb.append("\t\t\t\terror:function(){\n");
            sb.append("\t\t\t\t\tshowErr(\"出错，请刷新重新操作\");\n");
            sb.append("\t\t\t\t}\n");
            sb.append("\t\t\t});\n");
            sb.append("\t\t}\n");
            sb.append("\t});\n");
            sb.append("}\n");
            sb.append("</script>\n");

            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
    }
}