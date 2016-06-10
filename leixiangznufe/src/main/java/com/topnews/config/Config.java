package com.topnews.config;

import java.util.ArrayList;

/**
 * Created by zzx on 2016/5/19.
 */
public class Config
{
    public static String homePage = "http://wellan.zuel.edu.cn/";
    //文澜要闻
    public static String wenLanYaoWen = "1668/";
    //科教动态
    public static String keJiaoDongTai = "1670/";
    //综合新闻
    public static String zongHeXinWen = "1669/";
    //媒体报道
    public static String meiTiBaoDao = "1672/";
    //菁菁校园
    public static String jingJingXiaoYuan = "1671/";
    //中南人物
    public static String zhongNanRenWen = "1673/";
    //学术视点
    public static String xueShuShiDian = "1675/";
    //深度报道
    public static String shenDuBaoDao = "1674/";
    //专题网站
    public static String zhuanTiWangZhan = "1677";
    //校史春秋
    public static String xiaoShiChunQiu = "1678";
    
    
    //完整URL
    public static String wenLanYaoWenURL = homePage + wenLanYaoWen;
    public static String keJiaoDongTaiURL = homePage + keJiaoDongTai;
    public static String zongHeXinWenURL = homePage + zongHeXinWen;
    public static String meiTiBaoDaoURL = homePage + meiTiBaoDao;
    public static String jingJingXiaoYuanURL = homePage + jingJingXiaoYuan;
    public static String zhongNanRenWenURL = homePage + zhongNanRenWen;
    public static String xueShuShiDianURL = homePage + xueShuShiDian;
    public static String shenDuBaoDaoURL = homePage + shenDuBaoDao;
    public static String zhuanTiWangZhanURL = homePage + zhuanTiWangZhan;
    public static String xiaoShiChunQiuURL = homePage + xiaoShiChunQiu;
    
    private static ArrayList<String> types;

    public Config()
    {
        types = new ArrayList<String>();
        types.add(wenLanYaoWenURL);
        types.add(keJiaoDongTaiURL);
        types.add(zongHeXinWenURL);
        types.add(meiTiBaoDaoURL);
        types.add(jingJingXiaoYuanURL);
        types.add(zhongNanRenWenURL);
        types.add(xueShuShiDianURL);
        types.add(shenDuBaoDaoURL);
        types.add(zhuanTiWangZhanURL);
        types.add(xiaoShiChunQiuURL);
    }

    public static String getUrlByID(int id)
    {
        return types.get(id - 1);
    }
}
