//package pl.edu.ug.astokwisz.projektap.component;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//@Component
//public class BCryptPasswordEncoder implements PasswordEncoder {
//
//    @Override
//    public String encode(CharSequence arg0) {
//        try {
//            return getMD5Hex((String) arg0);
//        } catch (NoSuchAlgorithmException e) {
//            return "NoSuchAlgorithmException";
//        }
//    }
//
//    @Override
//    public boolean matches(CharSequence arg0, String arg1) {
//        return arg1.equals(encode(arg0));
//    }
//
//    public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {
//
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(inputString.getBytes());
//
//        byte[] digest = md.digest();
//
//        return convertByteToHex(digest);
//    }
//
//    private static String convertByteToHex(byte[] byteData) {
//
//        StringBuilder sb = new StringBuilder();
//        for (byte byteDatum : byteData) {
//            sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
//        }
//
//        return sb.toString();
//    }
//
//}