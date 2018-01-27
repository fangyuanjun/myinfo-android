package fyj.lib;

import android.util.Base64;

import com.kecq.myinfo.Temp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by fyj on 2017/12/18.
 */

public class SignHelper {
    private static final String HMAC_SHA1 = "HmacSHA1";

    public static String Sign(String dataString,String keyString) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        byte[] key=keyString.getBytes("utf-8");
        byte[] data=dataString.getBytes("utf-8");
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        byte[] signByte = mac.doFinal(data);

        String sign= Base64.encodeToString(signByte,Base64.NO_WRAP);

        String result=java.net.URLEncoder.encode(sign,"UTF-8");

        return result;
    }

    /**
     * 对URL进行签名并返回签名后的URL   url必须是以 /开头的相对路径 并且不得包含 ak  time sign参数
     * 结果是对原url加上  time=xxxxx&ak=xxxxx&sign=xxxx
     * @param url
     * @param ak
     * @param sk
     * @return
     */
    private static String SignUrl(String url,String ak, String sk,String token)
    {
        try {
            if(!url.startsWith("/")){
                LogHelper.e("签名URL必须以/开头");
            }

            SimpleDateFormat si = new SimpleDateFormat("yyyyMMddHHmmss");
            si.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

            String time= si.format(new Date());

            String pre="&";
            if(!url.contains("?")){
                pre="?";
            }

            if(token==null||token.equals("")){
                url=url+pre+"time="+time+"&guid="+ UUID.randomUUID().toString().replace("-","");
            }
          else {
                url=url+pre+"time="+time+"&guid="+ UUID.randomUUID().toString().replace("-","")+"&token="+token;
            }
            url=url+"&ak="+ak+"&sign="+Sign(url,sk);

            return  url;
        }
        catch(Exception e)
        {
            LogHelper.DoException("SignHelper签名异常",e);
           return  null;
        }
    }

    /**
     * 对URL进行签名并返回签名后的URL   url必须是以 /开头的相对路径 并且不得包含 ak  time sign参数
     * 结果是对原url加上  time=xxxxx&ak=xxxxx&sign=xxxx
     * @param url
     * @return
     */
    public static String SignUserUrl(String url)
    {
        return  SignUrl(url,Temp.APP_KEY,Temp.APP_SECRET,Temp.TOKEN);
    }

    public static String SignUrl(String url)
    {
        return  SignUrl(url,Temp.APP_KEY,Temp.APP_SECRET,null);
    }
}
