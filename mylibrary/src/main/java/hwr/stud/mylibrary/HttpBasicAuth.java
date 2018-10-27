package hwr.stud.mylibrary;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Base64;

import static java.util.Base64.getEncoder;

public class HttpBasicAuth {

    final String username;
    final String password;

    public HttpBasicAuth(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public String getAuthString() {

        String credsConcat = username + ":" + password;
        String authString = android.util.Base64.encodeToString(credsConcat.getBytes(), android.util.Base64.NO_WRAP);

        return authString;
    }
}
