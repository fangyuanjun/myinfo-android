package fyj.lib;



/**
 * Created by fyj on 2017/12/11.
 */

public abstract class HttpResult {
    public void  Requesting()
    {

    }

    public void  TimeOut()
    {

    }

    public  abstract void  Success(String result);

    public void Error(Throwable tr)
    {

    }
}
