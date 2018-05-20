package edu.pima.jdalessandro.notes;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Note implements Serializable {
    private String mTitle;
    private String mContent;
    private long mDateTime;

    public Note(long dateInMillis, String title, String content) {
        mDateTime = dateInMillis;
        mTitle = title;
        mContent = content;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setmTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setmContent(String content) {
        mContent = content;
    }

    public String getDateTimeFormatted(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(mDateTime));
    }

    public void setDateTime(long dateTime){
        mDateTime = dateTime;
    }

    public long getDateTime(){
        return mDateTime;
    }
}
