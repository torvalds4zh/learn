package com.weibangong.company;

/**
 * Created by zhangping on 16/6/6.
 */
public enum  BillingPlan {

    FREE("免费版", 30, false),         // 0 - 免费版年服务费：0元，可支持100个内部人员使用
    STANDARD("标准版", 30, false),     // 1 - 普通版年服务费：2000元，可支持30个内部人员使用；
    ADVANCED("高级版", 50, false),     // 2 - 高级版年服务费：3000元，可支持50个内部人员使用；
    GOLD("黄金版", 100, false),        // 3 - 黄金版年服务费：5000元，可支持100个内部人员使用；
    WHITE_GOLD("白金版", 220, false),  // 4 - 白金版年服务费：10000元，可支持220个内部人员使用；
    DIAMOND("钻石版", 500, false),     // 5 - 钻石版年服务费：20000元，可支持500个内部人员使用；
    GROUP("集团版", 1500, false),      // 6 - 集团版年服务费：50000元，可支持1500个内部人员使用；
    UNLIMITED("无限版", -1, false),    // 7 - 无限版年服务费：?元，可支持无限个内部人员使用；
    AGENT("代理商版本", 300, false),   // 8 -代理商版年服务费：?元，可支持300个内部人员使用；

    // 新增快速试销版本
    BASIC1("基础版1", 100, false),    // 9  -  基础版1         价格：1000元           支持账号数<=100
    BASIC2("基础版2", 300, false),    // 10 - 基础版2         价格：2000元           支持账号数  >100 <=300
    BASIC3("基础版3", 600, false),    // 11 - 基础版3         价格：3000元           支持账号数  >300 <=600
    BASIC4("基础版4", 1500, false),   // 12 - 基础版4         价格:  5000元         支持账号数  > 600 <=1500

    FREE_PACKAGE("50人(免费)", 50, true),// 免费
    PACKAGE1("50人", 50, true), //2000 元
    PACKAGE2("200人", 200, true), // 4000元
    PACKAGE3("400人", 400, true), // 6000元
    PACKAGE4("800人", 800, true), // 10000元

    FREE_PACKAGE1("10人(免费)", 10, true), // 10人免费体验版
    FREE_PACKAGE2("20人(免费)", 20, true),

    // 新版本套餐
    STANDARD_NEW("标准版", 20, true),     // 1 - 普通版年服务费：2000元，可支持20个内部人员使用；
    ADVANCED_NEW("高级版", 100, true),     // 2 - 高级版年服务费：4000元，可支持100个内部人员使用；
    GOLD_NEW("黄金版", 200, true),        // 3 - 黄金版年服务费：6000元，可支持200个内部人员使用；
    WHITE_GOLD_NEW("白金版", 400, true),  // 4 - 白金版年服务费：10000元，可支持400个内部人员使用；
    DIAMOND_NEW("钻石版", 1500, true), // 20人免费体验版 30000

    // 2016 新版本套餐
    BASIC_NEW_2016("基础版", 20, true),    // 25 基础版 20人 3000
    STANDARD_NEW_2016("标准版", 50, true),     // 26 - ：标准版 50人 5000；
    ADVANCED_NEW_2016("高级版", 100, true),     // 27 - 高级版 100人 8000；
    GOLD_NEW_2016("黄金版", 200, true),        // 28 - 黄金版 200人 12000；
    WHITE_GOLD_NEW_2016("白金版", 400, true),  // 29 - 白金版 400人 20000；
    DIAMOND_NEW_2016("钻石版", 1000, true), // 30 1000人 40000
    MENGNIU("", 5760, true), // 蒙牛大客户5760人
    YOUYONGFENQI("", 5400, true), // 有用分期5000人
    MENG_MARKET("", 5500, true),

    BASIC("基础版", 20, true),
    KAOQIN("考勤版", 100, true),
    ZYB("完整版", -1, true);
    ; // 蒙牛市场冲击


    private final String name;

    private final int quota;

    private final boolean valid;

    BillingPlan(String name, int quota, boolean valid) {
        this.name = name;
        this.quota = quota;
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public int getQuota() {
        return quota;
    }

    public boolean isValid() {
        return valid;
    }
}
