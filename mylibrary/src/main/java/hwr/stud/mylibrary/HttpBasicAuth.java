package hwr.stud.mylibrary;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Base64;

import static java.util.Base64.getEncoder;

public class HttpBasicAuth {

    public static String  getAuthString(String username, String password) {

        String credsConcat = username + ":" + password;
        String authString = android.util.Base64.encodeToString(credsConcat.getBytes(), android.util.Base64.NO_WRAP);

        return authString;
    }
}
