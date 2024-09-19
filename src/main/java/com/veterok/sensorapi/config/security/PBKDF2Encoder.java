package com.veterok.sensorapi.config.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class PBKDF2Encoder implements PasswordEncoder {

    @Value("${security.password-encoder.secret}")
    private byte[] secret;

    @Value("${security.password-encoder.iteration}")
    private Integer iteration;

    @Value("${security.password-encoder.keylength}")
    private Integer keylength;

    private SecretKeyFactory keyFactor;

    @PostConstruct
    public void init() {
        try {
            keyFactor = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * More info (<a href="https://www.owasp.org/index.php/Hashing_Java">...</a>) 404 :(
     * @param cs password
     * @return encoded password
     */
    @Override
    public String encode(CharSequence cs) {
        try {
            byte[] result = keyFactor
                    .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret, iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return encode(cs).equals(string);
    }
}
