package com.neusoft.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.neusoft.beans.CustomerBean;
import com.neusoft.beans.EcardBean;
import com.neusoft.dao.CustomerDao;
import com.neusoft.dao.EcardDao;

public class CustomerUtils {
    private CustomerDao cd = CustomerDao.getInstance();
    @SuppressWarnings("unused")
    private CustomerBean account = null;
    private static CustomerUtils instance;

    private CustomerUtils() {
    }

    public static CustomerUtils getInstance() {
        if (instance == null) {
            instance = new CustomerUtils();
        }
        return instance;
    }
    
    /*
     * 方法作用：根据手机号查找用户是否存在
     * 传入参数：输入的手机号 
     * 返回参数：布尔值，true为存在，false为不存在
     */
    public boolean findAccountByCode(String code) {
        CustomerBean acc = cd.findAccountByCode(code);
        if (acc == null) {
            return false;
        }
        return true;
    }

    /*
     * 方法作用：客户登陆检测
     * 传入参数：输入的手机号 
     * 返回参数：整数，0为账号不存在，1为登录成功，2为密码不正确
     */
    public int accountLogin(String code, String password) {
        CustomerBean acc = cd.findAccountByCode(code);
        if (acc == null) {
            return 0;// 账号不存在
        }
        boolean boo = acc.getPassword().equals(password);
        if (boo) {
            this.account = acc;
            return 1;// 登陆成功
        }
        return 2;// 密码不正确
    }

    /*
     * 方法作用：客户修改密码
     * 传入参数：输入的手机号 
     * 返回参数：布尔值，true为修改成功，false为原密码不正确
     */
    public boolean changePassword(String usr, String oldpass, String newpass) {
        CustomerBean acc = cd.findAccountByCode(usr);
        if (!oldpass.equalsIgnoreCase(acc.getPassword())) {
            return false;// 输入的旧密码不正确
        }
        acc.setPassword(newpass);
        cd.updateAccount(acc);
        return true;
    }

    /*
     * 方法作用：客户发送短信检查
     * 传入参数：发件人手机号，收件人手机号
     * 返回参数：CMD-003- 0 发送失败-用户不存在 扣费 1发送失败-余额不足 不扣费 2 发送成功 扣费
     */
    public int sendMessage(String usr, String rece) {
        CustomerBean acc = cd.findAccountByCode(usr);
        double current = acc.getMoney();
        if (current > 0.1) {
            acc.setMoney(current - 0.1);
            cd.updateAccount(acc);
            if (rece.equals("001        ")) {// 查余额
                return 3;
            }
            if (rece.equals("002        ")) {// 查天气
                return 4;
            }
            if (rece.equals("003        ")) {// 充话费
                return 5;
            }
            CustomerBean acc1 = cd.findAccountByCode(rece);
            if (acc1 == null) {
                return 0;
            }
            return 2;
        }
        return 1;
    }

    /*
     * 方法作用：客户充值话费
     * 传入参数：手机号，充值卡密
     * 返回参数：见代码注释
     */
    public int addMoney(String usr, String msg) {
        if (msg.length() < 16) {
            return 0;
        }
        String id = msg.substring(0, 10);
        String pwd = msg.substring(10, msg.length());
        EcardDao ed = new EcardDao().getInstance();
        EcardBean eb = ed.findAccountByCode(id);
        if (eb == null || !pwd.equals(eb.getPwd())) {
            return 0;// 卡密错误
        }
        if (eb.getIsUsed() == 1) {
            return 1;// 已被使用
        }
        if (eb.getTime().compareTo(getCurrenttime()) < 0) {
            return 2;// 过期
        }
        CustomerBean acc = cd.findAccountByCode(usr);
        acc.setMoney(acc.getMoney() + eb.getMoney());
        cd.updateAccount(acc);
        eb.setIsUsed(1);
        ed.updateAccount(eb);
        return 3;

    }

    /*
     * 方法作用：获得系统当前时间
     * 传入参数：无
     * 返回参数：无
     */
    public String getCurrenttime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }
}
