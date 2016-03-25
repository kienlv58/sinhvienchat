package asynctask;

import android.os.AsyncTask;

/**
 * Created by Kiên on 3/19/2016.
 */
public class LoadMsgAsynctask extends AsyncTask<Boolean,Void,Boolean> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        Boolean chated = params[0];

        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
