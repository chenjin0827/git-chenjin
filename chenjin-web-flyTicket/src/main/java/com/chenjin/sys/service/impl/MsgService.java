package com.chenjin.sys.service.impl;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.exception.MyException;
import com.chenjin.common.framework.service.BaseService;
import com.chenjin.sys.dao.IMsgDao;
import com.chenjin.sys.dao.IMsgItemDao;
import com.chenjin.sys.dao.IUserDao;
import com.chenjin.sys.entity.Msg;
import com.chenjin.sys.entity.MsgItem;
import com.chenjin.sys.entity.Organization;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IMsgService;
import com.chenjin.sys.service.IOrganizationService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class MsgService extends BaseService<Msg, Long>
        implements IMsgService
{
    private IMsgDao msgDao;

    @Resource
    private IMsgItemDao msgItemDao;

    @Resource
    private IOrganizationService organizationService;

    @Resource
    private IUserDao userDao;

    public IMsgDao getMsgDao()
    {
        return this.msgDao;
    }
    @Resource
    public void setMsgDao(IMsgDao msgDao) {
        this.msgDao = msgDao;
        super.setBaseDao(msgDao);
    }

    @Transactional
    public void sendBySYS(String projectCode, Msg msg, Long[] userIds) {
        msg.setIds("01");
        msg.setCaty("0");
        msg.setOwnerId(Long.valueOf(Long.parseLong("-1")));
        msg.setOwnerName("系统");
        msg = (Msg)this.msgDao.save(msg);

        for (int i = 0; i < userIds.length; i++) {
            User user = (User)this.userDao.getById(userIds[i]);
            if (user == null) {
                continue;
            }
            MsgItem mrl = new MsgItem();
            mrl.setMsg(msg);

            mrl.setSender("0");
            mrl.setIds("00");
            this.msgItemDao.save(mrl);
        }

        MsgItem mrl = new MsgItem();
        mrl.setMsg(msg);

        mrl.setSender("1");
        mrl.setIds("00");
        this.msgItemDao.save(mrl);
    }

    @Transactional
    public void sendBySYSToOrg(String projectCode, Msg msg, Long OrgId) throws Exception {
        msg.setIds("01");
        msg.setCaty("0");
        msg = (Msg)this.msgDao.save(msg);

        Organization o = (Organization)this.organizationService.getById(projectCode, OrgId);
        if (o == null) {
            throw new MyException("OrgId：" + OrgId + "不存在");
        }

        MsgItem mrl = new MsgItem();
        mrl.setMsg(msg);
        mrl.setOrganizationId(o.getId());
        mrl.setOrgName(o.getOrgName());
        mrl.setSender("0");
        mrl.setIds("00");
        this.msgItemDao.save(mrl);

        MsgItem mrl1 = new MsgItem();
        mrl1.setMsg(msg);
        mrl1.setOrganizationId(msg.getOrganizationId());
        mrl1.setOrgName(msg.getOrgName());
        mrl1.setSender("1");
        mrl1.setIds("00");
        this.msgItemDao.save(mrl1);
    }

    public DataGrid<Msg> getDraftMsg(String projectCode, Msg mrl, PageRequest pageable)
    {
        return this.msgDao.getDraftMsg(mrl, pageable);
    }

    @Transactional
    public void sendFromDraft(String projectCode, Msg msg, String userIdlist, User currUser)
    {
        Msg msgold = (Msg)this.msgDao.getById(msg.getId());
        msgold.setIds("01");
        msgold.setBody(msg.getBody());
        msgold.setTitle(msg.getTitle());
        this.msgDao.update(msgold);

        String[] userids = userIdlist.split(",");
        for (int i = 0; i < userids.length; i++) {
            User user = (User)this.userDao.getById(Long.valueOf(Long.parseLong(userids[i])));
            if (user == null) {
                continue;
            }
            MsgItem mrl = new MsgItem();
            mrl.setMsg(msg);

            mrl.setSender("0");
            mrl.setIds("00");
            this.msgItemDao.save(mrl);
        }

        MsgItem mrl = new MsgItem();
        mrl.setMsg(msg);

        mrl.setSender("1");
        mrl.setIds("00");
        this.msgItemDao.save(mrl);
    }
}