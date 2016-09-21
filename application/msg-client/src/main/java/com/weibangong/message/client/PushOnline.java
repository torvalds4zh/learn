package com.weibangong.message.client;

import java.io.*;

public class PushOnline {
    /**
     * 微秘推送
     * 线上奇鱼 tenantId 538332
     * 线上白雪 userId 360608
     */
    public static void main(String[] args) {

        PushClient pushClient = PushClient.create()
                .addresses(Constant.ONLINE_MQ_SERVER).username("wbgrmq").password("wbgrmq").virtualHost("/").end();

        String str;
        //除奇鱼的所有公司
//        String path = "/Users/HaiZhi/Desktop/IM-About/OnlinePush/allall.csv__";
        //奇鱼 ALL : tenantId
//        String path = "/Users/HaiZhi/Desktop/IM-About/OnlinePush/online_qiyu";

        //简报TEam
//        String path = "/Users/HaiZhi/Desktop/IM-About/OnlinePush/online_jb";

        // 测试环境
//        String path = "/Users/HaiZhi/Desktop/IM-About/OnlinePush/testing_evn_data";
        // hzmail
        String path = "/Users/HaiZhi/Desktop/IM-About/OnlinePush/online_allall_201607014.csv";

//        String path = "/Users/HaiZhi/Downloads/Ios0909.csv";

//        String path = "/Users/HaiZhi/Desktop/IM-About/OnlinePush/pre_push_baixue_hongyang";

//        String path = "/Users/HaiZhi/Desktop/IM-About/OnlinePush/pre_push_single";

//        String path ="/Users/HaiZhi/Downloads/teacher1.csv";





        BufferedReader br;
        //V 192
//        String content = "{\"height\":916,\"width\":640,\"type\":\"1\",\"url\":\"http://www.weibangong.com:80/files/574ea22de4b0a0f915e48e9e\",\"id\":\"574ea22de4b0a0f915e48e9e\",\"length\":\"77663\"}";
        //端午节
//        String content = "{\"height\":1334,\"width\":750,\"type\":\"1\",\"url\":\"http://www.weibangong.com:80/files/575781d1e4b017205061f43e\",\"id\":\"575781d1e4b017205061f43e\",\"length\":\"58587\"}";

//      公司改名
//        String content = "{\"height\":1334,\"width\":750,\"type\":\"1\",\"url\":\"http://www.weibangong.com:80/files/575d13f6e4b01720506201f0\",\"id\":\"575d13f6e4b01720506201f0\",\"length\":\"39672\"}";

//      邮箱2.0上线
//        String content = "{\"height\":5094,\"width\":640,\"type\":\"1\",\"url\":\"http://www.weibangong.com:80/files/57638b58e4b05a1af1fb3434\",\"id\":\"57638b58e4b05a1af1fb3434\",\"length\":\"489935\"}";

//      线上看卡片效果
//        String content = "{\"title\":\"借势 破局！\",\"content\":\"" +
//                "借势 破局！新的互联网企业应用到底能为企业带来哪些价值，怎样帮助企业立于不败之地呢？\"" +
//                ",\"type\":\"7\",\"datetime\":\"1468235921577\"," +
//                "\"url\":\"https://mp.weixin.qq.com/s?__biz=MzI2NTMyODI0Ng==&mid=2247483807&idx=1&sn=414c92f1f8edf25a74ef6d6c50fd6418&scene=0&key=77421cf58af4a653cb913658830ebb297baf5d9b81527f484bed86873b195e40f66bbbdd03901e03b7937f515eaa9bfa&ascene=0&uin=MTUxMTY1MzE2MA%3D%3D&devicetype=iMac+MacBookPro11%2C1+OSX+OSX+10.10.5+build(14F1808)&version=11000003&pass_ticket=c1KIDq4K945mp1jud5czlhtLZ5e77VIJZDjoGbQDDR%2BpD5i5qcKjeb26i4kxE6h6\"" +
//                ",\"image\":\"http://static.weibangong.com/files/57837e53a6f9860e3a22c125\" ,\"cardtype\":\"1\" ,\"version\":\"1\",\"height\":\"356\"" +
//                ",\"width\":\"638\"}";

//        智能简报[文字]
//        String content = "{\"type\":\"0\",\"text\":\"公司本周新录入34家客户、26个联系人。" +
//                "维护跟进了0家客户，跟进客户的员工有18人，产生了0条跟进记录，客情关系还需要加强维护。" +
//                "新开合同金额0.00元。收到合同回款金额0.00元。是因为没开合同，还是没人回款？" +
//                "外勤方面，本周共有23人发出26次外勤，人均出勤1.13次。" +
//                "外勤发出前三为1 魏阳 (2.0)2 刘潇 (2.0)3 张雪红 (2.0)，可以多鼓励鼓励员工哦。\"}";

//        智能简报[卡片]
//        String content = "{\"title\":\"运营周报[测试看效果]\",\"content\":\"" +
//                "公司本周新录入34家客户、26个联系人。" +
//        "维护跟进了0家客户，跟进客户的员工有18人，产生了0条跟进记录，客情关系还需要加强维护。" +
//                "新开合同金额0.00元。收到合同回款金额0.00元。是因为没开合同，还是没人回款？" +
//                "外勤方面，本周共有23人发出26次外勤，人均出勤1.13次。" +
//                "外勤发出前三为1 魏阳 (2.0)2 刘潇 (2.0)3 张雪红 (2.0)，可以多鼓励鼓励员工哦。\"" +
//                ",\"type\":\"7\",\"datetime\":\"1468330119516\"," +
//                "\"url\":\"https://mp.weixin.qq.com/s?__biz=MzI2NTMyODI0Ng==&mid=2247483807&idx=1&sn=414c92f1f8edf25a74ef6d6c50fd6418&scene=0&key=77421cf58af4a653cb913658830ebb297baf5d9b81527f484bed86873b195e40f66bbbdd03901e03b7937f515eaa9bfa&ascene=0&uin=MTUxMTY1MzE2MA%3D%3D&devicetype=iMac+MacBookPro11%2C1+OSX+OSX+10.10.5+build(14F1808)&version=11000003&pass_ticket=c1KIDq4K945mp1jud5czlhtLZ5e77VIJZDjoGbQDDR%2BpD5i5qcKjeb26i4kxE6h6\"" +
//                ",\"image\":\"http://static.weibangong.com/files/5784efbe1d5583ba58ad31b0\" ,\"cardtype\":\"1\" ,\"version\":\"1\",\"height\":\"356\"" +
//                ",\"width\":\"638\"}";


//      V 2.0上线
//        String content = "{\"height\":1334,\"width\":750,\"type\":\"1\",\"url\":\"http://static.weibangong.com/files/578731c5c1ab9dd0cb9af9ca\",\"id\":\"578731c5c1ab9dd0cb9af9ca\",\"length\":\"212677\"}";


//        CRM
//        String content = "{\"height\":356,\"width\":638,\"type\":\"1\",\"url\":\"http://static.weibangong.com/files/578c8d27c8628b6b17b242a4\",\"id\":\"578c8d27c8628b6b17b242a4\",\"length\":\"41913\"}";


//                String content = "{\"title\":\"v2.0客户管理新功能解读\",\"content\":\"" +
//                "客户管理V2.0各个方面全面优化提升，让你的使用过程更加轻松便捷！\"" +
//                ",\"type\":\"7\",\"datetime\":\"1468897433990\"," +
//                "\"url\":\"http://marketing.weibangong.com/crm/index.html\"" +
//                ",\"image\":\"http://static.weibangong.com/files/578c8d27c8628b6b17b242a4\" ,\"cardtype\":\"1\" ,\"version\":\"1\",\"height\":\"356\"" +
//                ",\"width\":\"638\"}";

//
//                String content = "{\"type\":\"0\",\"text\":\"亲爱的微办公用户，微办公近期已全面升级，" +
//                        "你当前使用的iOS手机端微办公版本过低，将影响部分功能的正常使用，强烈期待你能升级到最新版本，" +
//                        "可以在微办公官网www.weibangong.com下载安装最新版本，体验一体化办公平台的安全高效，微办公感谢有你相伴!\"}";


////        智能简报[卡片]
//        String content = "{\"title\":\"运营周报\",\"content\":\"" +
//                "关键词：业务量上升、客单价下降、回款逾期、出勤率低\"" +
//                ",\"type\":\"11\",\"datetime\":\"2016年9月5日-2016年9月11日\"," +
//                "\"url\":\"http://www.weibangong.com/brief/details.html\"" +
//                ",\"image\":\"http://static.weibangong.com/files/57b3d324e82862e48b72c2d2\" ,\"cardtype\":\"1\" ,\"version\":\"1\",\"height\":\"320\"" +
//                ",\"width\":\"640\"}";


        //教师节
//        String content = "{\"height\":1334,\"width\":750,\"type\":\"1\",\"url\":\"http://static.weibangong.com/files/57d14c12d65519fcefbb0942\",\"id\":\"57d14c12d65519fcefbb0942\",\"length\":\"166865\"}";

        //中秋节
        String content = "{\"height\":1334,\"width\":750,\"type\":\"1\",\"url\":\"http://static.weibangong.com/files/57d8b2a988288031e39b8323\",\"id\":\"57d8b2a988288031e39b8323\",\"length\":\"244065\"}";





        int i = 0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            while ((str = br.readLine()) != null) {

                String[] userAndTenant = str.split(",");
                System.out.println(userAndTenant[0] + " :: " + userAndTenant[1] + " sqe:" + (++i));
                //System.out.println(content);



//              // 微密文字/图片推送 --------------begin----------------------
//                PushMessage.create()
//                        .type(PushType.WeimiMsgPush.name()).target(userAndTenant[0]).header("tenantId", userAndTenant[1])
//                        .body(content)
//                        .secretaryPush(pushClient);
//              // 微密文字/图片推送 --------------end----------------------



//               限速--------------begin----------------------
                //按公司推 每100个停5秒
                //按用户推 每1000个停5秒
                if (i % 1000 == 0) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                //限速--------------end----------------------

            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
