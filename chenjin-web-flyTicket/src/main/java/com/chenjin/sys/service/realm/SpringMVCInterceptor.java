//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenjin.sys.service.realm;

import com.alibaba.fastjson.JSONObject;
import com.chenjin.sys.dto.Message;
import com.chenjin.sys.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SpringMVCInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(SpringMVCInterceptor.class);

    public SpringMVCInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!this.checkFileType(request, response).booleanValue()) {
            return false;
        } else {
            User currentUser = request.getSession().getAttribute("currentUser") == null ? new User() : (User)request.getSession().getAttribute("currentUser");
            System.out.println(currentUser.getEmpId() + "-->" + new Date() + "-->" + request.getServletPath());
            this.logger.info(currentUser.getEmpId() + "-->" + new Date() + "-->" + request.getServletPath());
            return true;
        }
    }

    private Boolean checkFileType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            Map<String, MultipartFile> files = multipartRequest.getFileMap();
            Iterator iterator = files.keySet().iterator();

            while(iterator.hasNext()) {
                String formKey = (String)iterator.next();
                MultipartFile multipartFile = multipartRequest.getFile(formKey);
                String filename = multipartFile.getOriginalFilename();
                if (!this.checkFile(filename)) {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter out = null;

                    try {
                        Message message = new Message();
                        message.setSuccess(false);
                        message.setMsg(filename + "该文件类型不支持！");
                        JSONObject.toJSON(message);
                        out = response.getWriter();
                        out.append(JSONObject.toJSON(message).toString());
                    } finally {
                        out.close();
                    }

                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkFile(String fileName) {
        String suffixList = "jpg,gif,png,ico,bmp,jpeg,doc,docx,xls,xlsx,pdf,zip,rar";
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        return suffixList.contains(suffix.trim().toLowerCase());
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
