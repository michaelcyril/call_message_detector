package com.example.mripoti_tapeli_android;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactUtils {
    public static boolean isPhoneNumberInContacts(Context context, String phoneNumber) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));

        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup._ID}, null, null, null);

        if (cursor != null) {
            boolean exists = cursor.moveToFirst();
            cursor.close();
            return exists;
        }

        return false;
    }
}