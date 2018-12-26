package com.chenjin.sys.controller;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.annotation.CurrentUser;
import com.chenjin.common.util.ExcelUtil;
import com.chenjin.sys.dto.Message;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IUserService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/sys/sql"})
public class SQLController
{

    @Resource
    private IUserService userService;

    @RequestMapping({""})
    public String home()
    {
        return "sys/user/sql";
    }

    @RequestMapping({"/dosql"})
    @ResponseBody
    public DataGrid<Map<String, Object>> dosql(PageRequest pageable, String mysql, @CurrentUser User currentUser)
    {
        System.out.println("mysql:" + mysql);

        DataGrid page = this.userService.queryBySql(currentUser.getProjectCode(), mysql, pageable);

        return page;
    }

    @RequestMapping(value={"/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message del(String mysql, @CurrentUser User currentUser)
    {
        Message message = new Message();
        try
        {
            int count = this.userService.executeSql(currentUser.getProjectCode(), mysql);
            message.setMsg("执行成功" + count + "笔");
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping({"/csv"})
    @ResponseBody
    public void csv(String mysql, HttpServletResponse resp, @CurrentUser User currentUser)
    {
        try
        {
            System.out.println("mysql:" + mysql);

            String name = "sqlData";
            List list = this.userService.listBySql(currentUser.getProjectCode(), mysql);
            if (list.size() > 0) {
                String[] heanders = new String[0];
                List head = new ArrayList();
                Map m0 = (Map)list.get(0);
                for (Object key : m0.keySet()) {
                    head.add(key);
                }
                heanders = (String[])head.toArray(new String[head.size()]);

                ExcelUtil excelUtil = new ExcelUtil(heanders, heanders);
                Workbook workbook = excelUtil.doExportXLS(list, name, false, false);
                resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                resp.setHeader("Content-Disposition", "attachment; filename=" + name + ".xls");
                OutputStream out = resp.getOutputStream();
                workbook.write(out);
                out.flush();
                workbook.close();
                out.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping({"/upload"})
    @ResponseBody
    public Message upload(MultipartFile myfile, @CurrentUser User currentUser)
    {
        Message message = new Message();
        String filename = myfile.getOriginalFilename();
        if ((filename == null) || ("".equals(filename))) {
            message.setMsg("文件不能为空");
            return message;
        }
        try {
            if ((filename.endsWith(".xls")) || (filename.endsWith(".xlsx")))
            {
                String msg = doExcelH(myfile, currentUser);

                message.setMsg(msg);
            } else {
                message.setMsg("请用正确模版格式导入");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }
        return message;
    }

    private String doExcelH(MultipartFile file, User currentUser) throws Exception
    {
        String[][] upExcel = null;
        InputStream input = file.getInputStream();
        XSSFWorkbook workBook = new XSSFWorkbook(input);
        XSSFSheet sheet = workBook.getSheetAt(0);
        if (sheet != null)
        {
            for (int i = 1; i <= sheet.getPhysicalNumberOfRows() - 1; i++) {
                XSSFRow row0 = sheet.getRow(0);
                XSSFRow row = sheet.getRow(i);
                if (upExcel == null) {
                    upExcel = new String[sheet.getPhysicalNumberOfRows() - 1][row0.getPhysicalNumberOfCells()];
                }
                for (int j = 0; j < row0.getPhysicalNumberOfCells(); j++) {
                    XSSFCell cell = row.getCell(j);
                    String cellStr = ExcelUtil.getValue(cell);
                    upExcel[(i - 1)][j] = cellStr;
                }
            }
        }
        workBook.close();
        return doExcelH(upExcel, currentUser);
    }

    private String doExcelH(String[][] upExcel, User currentUser)
            throws Exception
    {
        String className = upExcel[0][1];

        String[] keynames = upExcel[1];
        System.out.println("className = " + className);

        Class entityClass = Class.forName(className);

        Table table = (Table)entityClass.getAnnotation(Table.class);
        String tablename = table.name();

        String seqname = "";
        Method getidm = entityClass.getMethod("getId", new Class[0]);
        SequenceGenerator sequenceGenerator = (SequenceGenerator)getidm.getAnnotation(SequenceGenerator.class);
        String sequenceName = sequenceGenerator.sequenceName();

        StringBuffer sqlkey = new StringBuffer();
        StringBuffer sqlval = new StringBuffer();
        for (int i = 1; i < keynames.length; i++) {
            sqlkey.append("," + keynames[i]);
            sqlval.append(",?");
        }

        StringBuffer sql = new StringBuffer();
        sql.append("insert into " + tablename + " (id");
        sql.append(sqlkey + ") values (" + sequenceName + ".nextval");
        sql.append(sqlval + ")");
        System.out.println("sql ======" + sql);

        List list = new ArrayList();
        for (int i = 2; i < upExcel.length; i++) {
            List vlist = new ArrayList();
            for (int j = 1; j < upExcel[i].length; j++) {
                String value = upExcel[i][j];
                System.out.print(value + ",");
                vlist.add(value);
            }
            list.add(vlist);
            System.out.println("");
        }

        this.userService.saveSQL(currentUser.getProjectCode(), sql.toString(), list);

        return "导入完成，" + list.size() + "笔";
    }
}