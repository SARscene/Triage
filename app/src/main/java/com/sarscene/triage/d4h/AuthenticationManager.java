package com.sarscene.triage.d4h;

import com.sarscene.triage.d4h.models.User;

public class AuthenticationManager {
    private static User mUser;

    private AuthenticationManager() {

    }

    public static User getUser() {
        return mUser == null ? new User() : mUser;
    }

    public static void setUser(User loggedInUser) {
        mUser = loggedInUser;
    }

    public static boolean isLoggedIn() {
        return mUser != null;
    }
}
