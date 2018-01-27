package fyj.lib.android.update;

public interface IUpdateCallback {
	 public void checkUpdateCompleted(boolean isUpdate);
	 public void checkCanceled();
	 public void downloadCanceled();
	 public void downloadCompleted(boolean sucess, String errorMsg);
}
