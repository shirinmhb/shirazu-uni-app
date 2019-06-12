package com.example.shiraz_uni_app.Internet.ChangePassword;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

public class ChangePasswordModel extends Observable {

    boolean mSuccess;

    public boolean ismSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }


    public void changePassword(String mNewPassword) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectUser = new JSONObject();
        try {

            String username = Hawk.get("user_name");
            String token = Hawk.get("token");

            jsonObject.put("password", mNewPassword);
            jsonObject.put("token", token);
            jsonObject.put("username" , username);

            jsonObjectUser.put("user" , jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("shirintest", "json sent : " + jsonObjectUser.toString());

        AndroidNetworking.post("") //todo : get url from server.
                .addHeaders("X-HTTP-Method-Override", "PATCH")
                .addJSONObjectBody(jsonObjectUser)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("shirintest", "on response" + response.toString());
                        setmSuccess(true);
                        setChanged();
                        notifyObservers();
                    }

                    @Override
                    public void onError(ANError error) {

                        setmSuccess(false);
                        setChanged();
                        notifyObservers();
                    }
                });
    }
}
