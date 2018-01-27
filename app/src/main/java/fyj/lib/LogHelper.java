package fyj.lib;

import android.util.Log;

/**
 * Created by fyj on 2017/12/12.
 */

public class LogHelper {
    public static void DoException(Throwable e)
    {
        if(e.getMessage()!=null){   //必须判断 否则println needs a message
            Log.e("LogHelper",e.getMessage());
        }
        e.printStackTrace();
    }

    public static void DoException(String tag,Throwable e)
    {
        if(e.getMessage()!=null){   //必须判断  否则println needs a message
            Log.e("LogHelper",e.getMessage());
        }
        e.printStackTrace();
    }

    public static void i(String message)
    {

    }

    public static void e(String message)
    {

    }
}
