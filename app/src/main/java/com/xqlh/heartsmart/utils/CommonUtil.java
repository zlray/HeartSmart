package com.xqlh.heartsmart.utils;

import android.text.TextUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2016/11/6.
 */

public class CommonUtil {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return 为空 返回true  不为空返回false
     */
    public static boolean isStringEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * 验证用户名只包含2-4位中文
     * @param account
     * @return
     */
    public static boolean checkAccountMark(String account){
        String all = "^[\\u4e00-\\u9fa5]{2,4}";
        //[\u4e00-\u9fa5]{2,7}
        //String hh= "/^[\\\\u4E00-\\\\u9FA5]{2,4}$/";
        Pattern pattern = Pattern.compile(all);
        return pattern.matches(all,account);
    }


    /**
     * 验证验证码只能为字母
     * @param account
     * @return
     */
    public static boolean isYzm(String account){
        String all = "^[A-Za-z]+$";
        //[\u4e00-\u9fa5]{2,7}
        //String hh= "/^[\\\\u4E00-\\\\u9FA5]{2,4}$/";
        Pattern pattern = Pattern.compile(all);
        return pattern.matches(all,account);
    }


    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 隐藏手机号中间四位
     * @param s
     * @return
     */
    public static String hidePhone(String s){
        String hide = s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return hide;
    }

    /**
     * 隐藏银行卡中间位置
     * @param s
     * @return
     */
    public static String hideCardNo(String s){
        String hide = s.replaceAll("(\\d{4})\\d{8}(\\d{4})", "$1*******$2");
        return hide;
    }

    /**
     * 隐藏积分账号中间位置
     * @param s
     * @return
     */
    public static String hideIntegralNo(String s){
        String hide = s.replaceAll("(\\d{4})\\d{9}(\\d{4})", "$1*******$2");
        return hide;
    }


    /**
     * 用来获取随机并不重复的控件ID
     * @return
     */

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 只能是数字,字母,下划线
     * @param str
     * @return
     */
    public static boolean isStringFormatCorrect(String str) {
        //^[a-zA-Z][a-zA-Z0-9_ ]+$
        String strPattern = "[a-zA-Z0-9_ ]{6,16}";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
//如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

}
