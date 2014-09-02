package app.webs.util;
import com.webs.app.*;

import android.app.*;
import android.content.*;
import android.view.*;
import android.view.WindowManager.*;
import android.widget.*;

public class LoadingDialog{
	private Dialog dialog;
	private Context mCtx;

	public LoadingDialog(Context ctx){
		mCtx = ctx;
	}
	
	public void DialogShow(){
		LinearLayout DialogLayout = (LinearLayout)View.inflate(mCtx, R.layout.util_dialog, null);
		
		dialog = new Dialog(mCtx);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.setContentView(DialogLayout);
		
		LayoutParams params = dialog.getWindow().getAttributes();
	    params.width = LayoutParams.WRAP_CONTENT;
	    params.height = LayoutParams.WRAP_CONTENT;
	    dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
	     
	    dialog.show();
		
	}
	//Loading Dialog Dismiss func
	public void DialogDismiss(){
		dialog.dismiss();
	}	
}