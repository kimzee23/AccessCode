package services;

import data.model.AccessCode;
import data.model.Visitor;
import data.repository.AccessTokenRepository;
import data.repository.AccessTokens;

import java.util.Random;

public class AccessCodeImpl implements AccessCodeService {

    private AccessTokenRepository accessTokenRepository = new AccessTokens();

    @Override
    public AccessCode generateAccessToken(Visitor visitor) {
            Random random = new Random();
            AccessCode accessToken = new AccessCode();
            int randomNumber = random.nextInt(1000);
            String generateOtp = String.format("%04d", randomNumber);
            accessToken.setOtpCode(generateOtp);
            AccessCode otp = accessTokenRepository.save(accessToken);

            return otp;
    }



}
