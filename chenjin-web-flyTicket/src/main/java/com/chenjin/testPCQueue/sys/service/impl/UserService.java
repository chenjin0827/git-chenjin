package com.chenjin.testPCQueue.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.exception.MyException;
import com.chenjin.testPCQueue.common.framework.service.BaseService;
import com.chenjin.testPCQueue.commons.CommonProperties;
import com.chenjin.testPCQueue.sys.common.JsonUtil;
import com.chenjin.testPCQueue.sys.dao.IUserDao;
import com.chenjin.testPCQueue.sys.entity.User;
import com.chenjin.testPCQueue.sys.service.IUserService;
import com.web.security.CommonSecurity;
import com.web.security.HttpUtil;
import com.web.vo.SecurityModel;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly=true)
public class UserService extends BaseService<User, Long>
        implements IUserService
{
    private IUserDao userDao;

    @Resource(name="jdbc")
    private JdbcTemplate jdbc;

    public IUserDao getUserDao()
    {
        return this.userDao;
    }
    @Resource(name="userDao")
    public void setUserDao(IUserDao userDao) { this.userDao = userDao;
        super.setBaseDao(userDao);
    }

    @Transactional
    public void unlock()
    {
        this.userDao.unlock();
    }

    @Transactional(readOnly=true)
    public User getLogin(String projectCode, User user) {
        return this.userDao.getLogin(user);
    }

    public void clear()
    {
        this.userDao.clear();
    }

    public User findByEmpId(String projectCode, String empId) {
        return this.userDao.findByEmpId(empId);
    }

    @Transactional
    public String doExcelH(MultipartFile file) throws IOException {
        String msg = "";
        String[][] upExcel = null;
        InputStream input = file.getInputStream();
        HSSFWorkbook workBook = new HSSFWorkbook(input);
        HSSFSheet sheet = workBook.getSheetAt(0);

        if (sheet != null) {
            for (int i = 2; i <= sheet.getPhysicalNumberOfRows() - 1; i++) {
                HSSFRow row1 = sheet.getRow(1);
                HSSFRow row = sheet.getRow(i);
                for (int j = 0; j < row1.getPhysicalNumberOfCells(); j++) {
                    if (upExcel == null) {
                        upExcel = new String[sheet.getPhysicalNumberOfRows() - 2][row1.getPhysicalNumberOfCells()];
                    }
                    HSSFCell cell = row.getCell(j);
                    String cellStr = getValue(cell);

                    upExcel[(i - 2)][j] = cellStr;
                }
            }
        }

        workBook.close();
        try {
            msg = upload(upExcel);
        } catch (Exception e) {
            msg = e.getMessage();
            e.printStackTrace();
        }
        System.out.println(msg);
        return msg;
    }

    public String upload(String[][] userStr)
            throws Exception
    {
        return "";
    }

    public String getValue(Cell cell)
    {
        String value = "";
        if (cell == null) {
            return value;
        }
        switch (cell.getCellType())
        {
            case 0:
                if (HSSFDateUtil.isCellDateFormatted(cell))
                {
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = format.format(date);
                } else {
                    BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();

                    if ((value == null) || ("".equals(value.trim()))) break;
                    String[] item = value.split("[.]");
                    if ((1 >= item.length) || (!"0".equals(item[1]))) break;
                    value = item[0];
                }

                break;
            case 1:
                value = cell.getStringCellValue().toString();
                break;
            case 2:
                value = String.valueOf(cell.getNumericCellValue());
                if (!value.equals("NaN")) break;
                value = cell.getStringCellValue().toString();

                break;
            case 4:
                value = " " + cell.getBooleanCellValue();
                break;
            case 3:
                value = "";
                break;
            case 5:
                value = "";
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        if ("null".endsWith(value.trim())) {
            value = "";
        }
        return value;
    }

    public DataGrid<Map<String, Object>> queryBySql(String projectCode, String mysql, PageRequest pageable)
    {
        return this.userDao.queryBySql(mysql, pageable);
    }

    public List<Map<String, Object>> listBySql(String projectCode, String mysql) {
        return this.userDao.listBySql(mysql);
    }

    @Transactional
    public int executeSql(String projectCode, String mysql) {
        return this.userDao.executeSql(mysql);
    }

    public User getByCert(String projectCode, String caCert) {
        return this.userDao.getByCert(caCert);
    }

    public void saveSQL(String projectCode, String sql, List list)
    {
        int[] count = this.jdbc.batchUpdate(sql.toString(), new BatchPreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                try {
                    for (int j = 0; j < list.size(); j++)
                        ps.setObject(j + 1, list.get(j));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw new SQLException("value异常");
                }
            }

            public int getBatchSize()
            {
                return list.size();
            } } );
    }

    public User getByIdcode(String idCard) {
        return this.userDao.getByIdcode(idCard);
    }

    public void registerSSO(User user, User currentUser) {
        System.out.println("CommonProperties.APPNAME=" + CommonProperties.APPNAME);
        System.out.println("CommonProperties.SECURITY=" + CommonProperties.SECURITY);
        String idCode = user.getIdcard();
        SecurityModel sm = new CommonSecurity(CommonProperties.APPNAME, CommonProperties.SECURITY).encrypt(idCode);
        System.out.println(CommonProperties.SSOURL + "/api/mangageUser");
        System.out.println(JsonUtil.tranObjectToJsonStr(sm));
        String result = HttpUtil.sendJsonPost(CommonProperties.SSOURL + "/api/mangageUser", JsonUtil.tranObjectToJsonStr(sm));
        System.out.println("SSO result =" + result);
        if (result == null) {
            throw new MyException("单点登录注册失败！result = null");
        }
        JSONObject jo = JSONObject.parseObject(result);
        if (!jo.getBooleanValue("success"))
            throw new MyException("单点登录注册失败！");
    }
}