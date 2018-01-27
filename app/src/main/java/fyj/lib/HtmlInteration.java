package fyj.lib;

import android.webkit.JavascriptInterface;

/**
 * Created by fyj on 2017/12/22.
 */

public class HtmlInteration {

    OnHtmlListener listener;

    public HtmlInteration()
    {

    }

    public HtmlInteration(OnHtmlListener listener)
    {
        this.listener=listener;
    }

    @JavascriptInterface
    public void processHTML(String html){
       if(this.listener!=null){
           this.listener.doHtml(html);
       }
    }

   public interface  OnHtmlListener
    {
        void doHtml(String html);
    }
}
