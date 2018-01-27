package fyj.lib.android;

import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fyj on 2017/12/11.
 */

public class ViewCache {

    private static List<View> list = new ArrayList<View>();

    public static void Add(View view) {
        for (View v : list) {
            if (v.getTag() != null && view.getTag() != null) {
                if (v.getTag().toString() == view.getTag().toString()) {
                    return ;
                }
            }
        }
        list.add(view);
    }

    public static View GetView(String tag)
    {
        for (View v : list) {
            if (v.getTag() != null) {
                if (v.getTag().toString() == tag) {
                    return v;
                }
            }
        }

        return null;
    }
}
