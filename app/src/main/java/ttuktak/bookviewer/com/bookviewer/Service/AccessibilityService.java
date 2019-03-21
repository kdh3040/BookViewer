package ttuktak.bookviewer.com.bookviewer.Service;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import ttuktak.bookviewer.com.bookviewer.CommonFunc;

public class AccessibilityService extends android.accessibilityservice.AccessibilityService {

    private static final String LOG_TAG = "onAccessibilityEvent";
    private boolean bEnter = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if(CommonFunc.getInstance().GetLock())
        //if(true)
        {
            AccessibilityNodeInfo source = event.getSource();
            if (source != null && source.getText() != null) {
                //    Log.i(LOG_TAG, source.toString());
                Log.i(LOG_TAG, source.getText().toString());
                if(!source.getText().toString().equals(getApplication().getPackageName()))
                {
                    mOnGoHomeClick();
                    return;
                }
            }
        }
        else
            return;
    }

    public void mOnGoHomeClick(){
     /*   Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        //intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                | Intent.FLAG_ACTIVITY_FORWARD_RESULT
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
*/
/*        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(intent);*/
        PackageManager packageManager = this.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(getApplication().getPackageName());
        if (null != intent)
            this.startActivity(intent);

    }

    @Override
    public void onInterrupt() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return false;
    }
}
