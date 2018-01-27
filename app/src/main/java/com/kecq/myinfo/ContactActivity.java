package com.kecq.myinfo;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fyj.lib.android.DialogHelper;

public class ContactActivity extends AppCompatActivity {
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        // 实例化
        asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());
        ReadContact();
    }

    private  void ReadContact(){
        try {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_CONTACTS},
                        1);
            }
        else
            {
                // Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
                Uri contactUri =ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                // 查询的字段
                String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                        ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                        ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY };
                // 按照sort_key升序查詢
                asyncQueryHandler.startQuery(0, null, contactUri, projection, null, null,
                        "sort_key COLLATE LOCALIZED asc");

              /*  Cursor cursor = this.getContentResolver().query(contactUri,
                        null,
                        null, null, null);

                DialogHelper.showToast("读取成功"+cursor.getCount());

                while (cursor.moveToNext()) {
                    String  contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String  contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                }
                cursor.close();//使用完后一定要将cursor关闭，不然会造成内存泄露等问题*/
            }


        }catch (Exception e){
            DialogHelper.showToast(e.getMessage());
            e.printStackTrace();

        }finally {

        }
    }

    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst(); // 游标移动到第一项
                for (int i = 0; i < cursor.getCount(); i++) {
                 int k=1;
                }
            }
            super.onQueryComplete(token, cookie, cursor);
        }

    }
}
