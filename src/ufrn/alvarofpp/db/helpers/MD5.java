package ufrn.alvarofpp.db.helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Essa classe gera o md5 de uma entrada qualquer
 */
public class MD5 {
    /**
     * Retorna o hash md5 da string desejada.
     * @param input Entrada
     * @return O hash md5 da entrada
     * @throws NoSuchAlgorithmException
     */
    public static String hash(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(input.getBytes(), 0, input.length());

        return (new BigInteger(1, messageDigest.digest()).toString(16));
    }
}