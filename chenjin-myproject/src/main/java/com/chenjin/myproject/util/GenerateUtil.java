package com.chenjin.myproject.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/****
 * 代码生成工具
 */
public class GenerateUtil {
    private static String comPath ;
    private static String comPathDot = "com.chenjin.myproject";
    private static String basePath = "E:/gitCodeProject/git-chenjin/chenjin-myproject/";
    
    private static String entityPath = basePath+"src/main/java/com/chenjin/myproject/entity";
    //接口地址
    private static String servicePath = basePath+"src/main/java/com/chenjin/myproject/service";

    private static String servicePackage = "com.chenjin.myproject.service";
    //接口实现类地址
    private static String perPath = basePath+"/src/main/java/com/chenjin";
    private static String suf = "";


    public static void main(String[] args) throws IOException {
        GenerateUtil g = new GenerateUtil();
        g.scanEntity();
    }

    public boolean scanEntity() throws IOException{
        File file = new File(entityPath);
        if(file.isDirectory()){
            String[] filelist = file.list();

            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(entityPath + "/" + filelist[i]);

                String fileName = readfile.getName();

                fileName = fileName.substring(0,fileName.indexOf("."));
                System.out.println(fileName);

                //生成service接口文件
                generateService(fileName);
                //生成service接口实现类文件
                generateServiceImpl(fileName);
                //生成dao接口文件
                generateDao(fileName);
                //生成dao接口实现类文件
                generateDaoImpl(fileName);
            }
        }
        return true;
    }

    /**
     * 生成dao实现文件
     * @param name
     * @throws IOException
     */
    private void generateDaoImpl(String name) throws IOException {
        File daoDir = new File(perPath+"/dao/impl/"+this.suf);
        if(!daoDir.exists()){
            System.out.println("生成dao.impl包");
            daoDir.mkdirs();
        }
        File daoFile = new File(perPath+"/dao/impl/"+name+"Dao.java");
        if(!daoFile.exists()){
            System.out.println("生成"+name+"Dao.java文件");
            daoFile.createNewFile();

            FileOutputStream out=new FileOutputStream(daoFile,false); //如果追加方式用true
            StringBuffer sb=new StringBuffer();
            sb.append("package "+comPathDot+".dao.impl;\n");
            sb.append("\n");
            sb.append("import org.springframework.stereotype.Repository;\n");
            sb.append("import com.shyl.common.framework.dao.BaseDao;\n");
            sb.append("import "+comPathDot+".entity."+name+";\n");
            sb.append("import "+comPathDot+".dao.I"+name+"Dao;\n");
            sb.append("\n");
            sb.append("@Repository");
            sb.append("\n");
            sb.append("public class "+name+"Dao extends BaseDao<"+name+",Long> implements I"+name+"Dao{\n");
            sb.append("\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }

    /**
     * 生成dao接口文件
     * @param name
     * @throws IOException
     */
    private void generateDao(String name) throws IOException {
        System.out.println(perPath+"/dao/");
        File daoDir = new File(perPath+"/dao/");

        if(!daoDir.exists()){
            System.out.println("生成dao包");
            daoDir.mkdirs();
        }
        File daoFile = new File(perPath+"/dao/I"+name+"Dao.java");

        if(!daoFile.exists()){
            System.out.println("生成I"+name+"Dao.java文件");
            daoFile.createNewFile();

            FileOutputStream out=new FileOutputStream(daoFile,false); //如果追加方式用true
            StringBuffer sb=new StringBuffer();
            sb.append("package "+comPathDot+".dao;\n");
            sb.append("\n");
            sb.append("import com.shyl.common.framework.dao.IBaseDao;\n");
            sb.append("import "+comPathDot+".entity."+name+";\n");
            sb.append("\n");
            sb.append("public interface I"+name+"Dao extends IBaseDao<"+name+",Long>{\n");
            sb.append("\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }

    /**
     * 生成service实现文件
     * @param name
     * @throws IOException
     */
    private void generateServiceImpl(String name) throws IOException {
        System.out.println(perPath+"/service/impl/");
        File serviceDir = new File(perPath+"/service/impl/");
        if(!serviceDir.exists()){
            System.out.println("生成service.impl包");
            serviceDir.mkdirs();
        }
        System.out.println(perPath+"/service/impl/"+this.suf+name+"Service.java");
        File serviceFile = new File(perPath+"/service/impl/"+this.suf+name+"Service.java");
        if(!serviceFile.exists()){
            System.out.println("生成"+name+"Service.java文件");
            serviceFile.createNewFile();

            FileOutputStream out=new FileOutputStream(serviceFile,false); //如果追加方式用true
            String sname = name.substring(0,1).toLowerCase()+name.substring(1);
            StringBuffer sb=new StringBuffer();
            sb.append("package "+comPathDot+".service.impl"+this.suf+";\n");
            sb.append("\n");
            sb.append("import javax.annotation.Resource;\n");
            sb.append("import org.springframework.stereotype.Service;\n");
            sb.append("import org.springframework.transaction.annotation.Transactional;\n");
            sb.append("import com.shyl.common.framework.service.BaseService;\n");
            sb.append("import "+comPathDot+".dao.I"+name+"Dao;\n");
            sb.append("import "+comPathDot+".entity."+name+";\n");
            sb.append("import "+comPathDot+".service.I"+name+"Service;\n");
            sb.append("\n");
            sb.append("@Service\n");
            sb.append("@Transactional(readOnly=true)\n");
            sb.append("public class "+name+"Service extends BaseService<"+name+",Long> implements I"+name+"Service{\n");
            sb.append("	private I"+name+"Dao "+sname+"Dao;\n");
            sb.append("\n");
            sb.append("	public I"+name+"Dao get"+name+"Dao() {\n");
            sb.append("		return "+sname+"Dao;\n");
            sb.append("	}\n");
            sb.append("\n");
            sb.append("	@Resource\n");
            sb.append("	public void set"+name+"Dao(I"+name+"Dao "+sname+"Dao) {\n");
            sb.append("		this."+sname+"Dao = "+sname+"Dao;\n");
            sb.append("		super.setBaseDao("+sname+"Dao);\n");
            sb.append("	}\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }

    /**
     * 生成service接口文件
     * @param name
     * @throws IOException
     */
    private void generateService(String name) throws IOException {
        File serviceDir = new File(servicePath);
        System.out.println(servicePath);
        System.out.println(serviceDir.isDirectory());
        if(!serviceDir.exists()){
            System.out.println("生成service包");
            serviceDir.mkdirs();
        }

        System.out.println(servicePath+"/I"+name+"Service.java");
        File serviceFile = new File(servicePath+"/I"+name+"Service.java");
        if(!serviceFile.exists()){
            System.out.println("生成I"+name+"Service.java文件");
            serviceFile.createNewFile();

            FileOutputStream out=new FileOutputStream(serviceFile,false); //如果追加方式用true
            StringBuffer sb=new StringBuffer();
            sb.append("package "+servicePackage+";\n");
            sb.append("\n");
            //sb.append("import com.shyl.common.framework.annotation.ProjectCodeFlag;\n");
            sb.append("import com.shyl.common.framework.service.IBaseService;\n");
            sb.append("import "+comPathDot+".entity"+name+";\n");
            sb.append("\n");
            sb.append("public interface I"+name+"Service extends IBaseService<"+name+",Long>{\n");
            sb.append("\n");
            sb.append("}\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
    }



}
