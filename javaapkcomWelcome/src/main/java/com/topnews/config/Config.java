package com.topnews.config;

import java.util.ArrayList;

/**
 * Created by zzx on 2016/5/19.
 */
public class Config
{
    public static String homePage = "http://wellan.zuel.edu.cn/";
    //����Ҫ��
    public static String wenLanYaoWen = "1668/";
    //�ƽ̶�̬
    public static String keJiaoDongTai = "1670/";
    //�ۺ�����
    public static String zongHeXinWen = "1669/";
    //ý�屨��
    public static String meiTiBaoDao = "1672/";
    //ݼݼУ԰
    public static String jingJingXiaoYuan = "1671/";
    //��������
    public static String zhongNanRenWen = "1673/";
    //ѧ���ӵ�
    public static String xueShuShiDian = "1675/";
    //��ȱ���
    public static String shenDuBaoDao = "1674/";
    //ר����վ
    public static String zhuanTiWangZhan = "1677";
    //Уʷ����
    public static String xiaoShiChunQiu = "1678";
    
    
    //����URL
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
