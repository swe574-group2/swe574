package plugin.cat.accounts.security;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.core.userdetails.UserDetails;
import plugin.cat.accounts.model.UserToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * Created by Arda on 11/12/2016.
 */
public class TokenUtil {
    private static final long ONE_HOUR_IN_MILLIS = 1000 * 60 * 60;

    // Time to keep token live
    private static final long EXPIRES = ONE_HOUR_IN_MILLIS;

    // Placed between Strings that make up token
    private static final String TOKEN_SEPARATOR = "-";

    public static String createToken(String nickname, String password) {
        long expires = System.currentTimeMillis() + EXPIRES;

        // Not yet encrypted
        String unsafeToken = generateTokenBody(nickname, password, expires);

        // Encrypt
        String safeToken = encrypt(unsafeToken);

        return safeToken;
    }

    private static String encrypt(String input) {
        String ret = input;

        try {
            ret = new String(Hex.encode(MessageDigest.getInstance("MD5").digest(input.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static String obtainNicknameFromToken(String authToken) {
        return (authToken == null || authToken.equals("")) ? null : authToken.split(TOKEN_SEPARATOR)[0];
    }

    public static boolean validateToken(String authToken, String nickname, String password, UserToken token) {
        String[] parts = authToken.split(TOKEN_SEPARATOR);
        long expires = Long.parseLong(parts[1]);
        String tokenBody = parts[2];
        String tokenBodyFromUser = generateTokenBody(nickname, password, expires);

        if (tokenBody.equals(tokenBodyFromUser)) {
            if (token.getToken().equals(authToken)) {
                return expires >= System.currentTimeMillis();
            }
        }

        return false;
    }

    public static String generateTokenBody(String nickname, String password, long expires) {
        StringBuilder sb = new StringBuilder();
        sb.append(nickname);
        sb.append(TOKEN_SEPARATOR);
        sb.append(expires);
        sb.append(TOKEN_SEPARATOR);
        sb.append(password);
        sb.append(TOKEN_SEPARATOR);
        sb.append("EndOfCatPluginToken");

        return sb.toString();
    }
}