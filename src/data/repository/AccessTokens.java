package data.repository;

import data.model.AccessCode;

import java.util.ArrayList;
import java.util.List;

public class AccessTokens implements AccessTokenRepository {

    private static long tokenCounter = 0;
    private static List<AccessCode> accessTokens = new ArrayList<>();


    @Override
    public AccessCode save(AccessCode accessToken) {
        accessTokens.add(accessToken);
        return accessToken;
    }

    @Override
    public AccessCode findAccessToken(String accessToken) {
        for (AccessCode token : accessTokens) {
            if (token.receiveAccessToken().equals(accessToken)) {
                return token;
            }
        }
        return null;
    }

    @Override
    public AccessCode validateAccessToken(AccessCode accessToken) {
        return null;
    }

    @Override
    public AccessCode otpExpiredDate(AccessCode accessToken) {
        return null;
    }

    @Override
    public long count() {
        return accessTokens.size();
    }
}
