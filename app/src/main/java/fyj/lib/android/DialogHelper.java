package fyj.lib.android;

import android.content.Context;
import android.widget.Toast;

import com.kecq.myinfo.ApplicationEx;

/**
 * Created by fyj on 2017/12/10.
 */

public class DialogHelper {

    private static Toast toast;

    public static void showToast( CharSequence message) {
        if (toast == null) {
            toast = Toast.makeText(ApplicationEx.getContext(),
                    message,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
