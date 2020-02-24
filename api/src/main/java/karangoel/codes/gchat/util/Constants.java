package karangoel.codes.gchat.util;

final public class Constants {
    // == fields ==
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000;  // 10 days
    public static final String TOKEN_PREFIX = "Bearer_";
    public static final String HEADER_STRING = "Authorization";

    public static final String SIGN_UP_URL = Mappings.VERSION + Mappings.USER + Mappings.SIGN_UP;

    // == constructors ==
    private Constants() {}
}
