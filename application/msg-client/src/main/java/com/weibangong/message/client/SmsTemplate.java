package com.weibangong.message.client;

public enum SmsTemplate {

    // 注意事项：
    // 1. 所有模板后面都加上 (微办公)；
    // 2. 短信数字不能超过70字，多了会切成多条发送。接收端iOS有合成功能，某些Android手机不支持该功能。文字内容要确保不会在URL链接中被截断。
    // 3. 增加短信模板需要在oa_security的t_sms_template中添加相应的模板。

    // 邀请企业客户试用，参数{mobile}为手机号
    //欢迎你试用移动互联网时代最好用的办公平台-微办公，成为我们精心选择邀请的前100名用户。请点击 http://www.weibangong.com
    // 下载安装后用${mobile}注册，即可用微办公轻松掌控工作，提升办公效率！期待你的使用反馈！客服电话4000659966。【微办公】
    INVITE_ORGANIZATION,

    // 企业客户自行注册，参数{mobile}为手机号
    //感谢你注册移动互联网时代最好用的办公平台-微办公！即刻启用，将你的公司装进手机里，对公司进行随时随地的管理，从而提升办公效率！
    // 请点击 http://www.weibangong.com 下载安装后用${mobile}注册。客服电话4000659966。【微办公】
    REGISTER_ORGANIZATION,

    // 企业管理员邀请同事，参数{userName}为管理员姓名，{mobile}为手机号
    //${userName}邀请你加入（${companyName}）的一体化移动办公平台-微办公。请点击 http://www.weibangong.com
    // 下载安装后用${mobile}注册。电脑端登录地址为 http://www.weibangong.com 。【微办公】
    INVITE_PERSON,

    // 新同事注册、激活帐号，参数{code}为验证码
    //${code}为你的微办公注册验证码，请在30分钟内完成输入。如非本人操作，请忽略。【微办公】
    ACTIVATTE_ACCOUNT,

    // 忘记密码，通过手机验证码重置，参数{code}为验证码
    //${code}为你的微办公密码找回验证码，请在15分钟内完成输入。如非本人操作，请忽略。【微办公】
    RESET_PASSWORD,

    // 企业账号通过审核后，系统发给预留手机号的短信，参数{mobile}为手机号，参数{password}为初始密码
    //感谢你选择一体化移动办公平台-微办公。即刻启用，让你的公司实现销售效能提升，管理高效规范，沟通便捷通畅，任务执行到位！
    // 请点击 http://www.weibangong.com 下载安装后用${mobile}登录，密码${password}。电脑端登录地址为 www.weibangong.com 。【微办公】
    REGISTER_ORGANIZATION_SUCCESS,

    // 通过管理后台创建一个员工账户，账户自动激活并分配一个初始密码，参数{employeeName}为管理员姓名，参数{companyName}为公司名，参数{mobile}为员工手机号，参数{password}为初始密码
    //${name}先生/女士.${employeeName}邀请你加入（${companyName}）的一体化移动办公平台-微办公。请点击 http://www.weibangong.com
    // 下载安装后用${mobile}登录，密码${password}。电脑端登录地址为 www.weibangong.com 。【微办公】
    CREATE_PERSON,

    // 个人账号注册通过审核后，系统发给员工手机号的短信
    //你的账号已通过审核，请即刻登录享受简单高效的移动办公。客服电话4000659966。【微办公】
    REGISTER_PERSON_SUCCESS,

    // 已有个人账号，被邀请进入新公司，身份员工
    //${name}已邀请你加入（${companyName}）的一体化移动办公平台-微办公，用你现有的微办公账号和密码登录即可使用。【微办公】
    JOIN_IN_NEW_COMPANY,

    // 已有个人账号，现要创建新企业，身份企业负责人
    //你的公司（${companyName}）已启用微办公服务，用你现有的微办公账号和密码登录即可使用。【微办公】
    CREATE_NEW_COMPANY,

    //你的公司（${companyName}）已启用微办公服务，请点击 http://www.weibangong.com 下载安装后用${username}登录，密码${password}。
    // 电脑端登录网址 http://www.weibangong.com 。【微办公】
    CREATE_NEW_COMPANY_WITH_PASSWORD,

    //${companyName}的小伙伴们在一体化移动办公平台-微办公等你，赶快去 www.weibangong.com 下载APP登录使用吧。
    // 你的用户名：{username}，初始密码：${password}。【微办公】
    REMIND_UN_LOGIN_USERS_WITH_PASSWORD,

    //亲爱的用户，【${companyName}】已拒绝了你的加入申请。请联系贵公司系统管理员或再次申请加入该公司。【微办公】
    APPLY_REJECTED,

    //亲爱的用户，贵公司新部署了一体化移动办公平台微办公，你的申请已通过，登录账号：${username}。
    // 注：PC登录网址： www.weibangong.com 。客服电话： 400-065-9966 。【微办公】
    APPLY_APPROVAL,

    //亲爱的用户，欢迎使用微办公。你已成功注册企业【${companyName}】，登录账号：${account}。
    // PC登录网址：www.weibangong.com 。客服电话:400-065-9966 。【微办公】
    COMPANY_REGISTER_SUCCESS,

    //亲爱的用户，欢迎使用微办公。你已成功注册企业【${companyName}】，登录账号：${account}，密码：${password}。PC登录网址：www.weibangong.com 。
    // 客服电话:400-065-9966 。【微办公】
    COMPANY_REGISTER_SUCCESS_WITH_PASSWORD,

    //亲爱的用户，你的账号已被【${companyName}】从通讯录移除，移除后无法登入该企业，如有问题请联系该企业系统管理员。
    // www.weibangong.com 【微办公】
    ACCOUNT_DELETE,

    //你的账号密码已重置，新密码为${password}，你可以登录后对新密码进行修改(切勿告之他人)。客服电话：400-065-9966。【微办公】
    PASSWORD_RESET,

    //验证码： ${code}，该验证码30分钟内有效，限本次使用。【微办公】
    VERIFY_CODE,

    //亲爱的用户。即刻登录一体化移动办公平台-微办公，让你提高工作效率！用户名和密码见注册通知短信。如需帮助请致电4000659966。【微办公】
    NOT_LOGGER_REMIND,

    //亲爱的${username}，恭喜你已成功邀请10位朋友使用了微办公，送给你30元购物卡，感谢你的邀请！京东购物卡充值码：${code}密码：${password}【微办公】
    MARKET_INVITE,

    // 亲爱的{username}，上周你有来自微秘发送的新消息，快登录www.weibangong.com查看吧.【微办公】
    FREE_COMPANY_NO_ACTIVE_WEEK,

    // 亲爱的{username}，你的微办公免费试用版3天后将到期关停，请登录www.weibangong.com查看，及时做好账户数据备份。如需升级为正式版，请拨打联系电话：4000659966。【微办公】
    FREE_COMPANY_NO_ACTIVE_MONTH;
}
