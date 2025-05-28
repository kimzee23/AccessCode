package data.repository;

import data.model.AccessCode;


public interface AccessTokenRepository {

    AccessCode save(AccessCode accessToken);
    AccessCode validateAccessToken(AccessCode accessToken);
    AccessCode findAccessToken(String accessToken);
    AccessCode otpExpiredDate(AccessCode accessToken);
    long count();
}
