package bookviewer.bookviewer.com.bookviewer;

import android.app.ProgressDialog;
import android.content.Context;

public class CommonFunc {
    private static CommonFunc _Instance;

    public static CommonFunc getInstance() {
        if (_Instance == null)
            _Instance = new CommonFunc();

        return _Instance;
    }

    private CommonFunc() {}

    private  ProgressDialog dialog;

    public void ShowProgressDialog(Context context)
    {
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("불러오는 중");
        dialog.show();
    }

    public void HideProgressDialog()
    {
        dialog.hide();
    }
}
