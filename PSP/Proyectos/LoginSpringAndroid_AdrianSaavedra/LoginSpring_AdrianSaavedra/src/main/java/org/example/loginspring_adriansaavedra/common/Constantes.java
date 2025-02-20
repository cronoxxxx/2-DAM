package org.example.loginspring_adriansaavedra.common;

public class Constantes {
    public static final String LOGIN_DIR = "/login";
    public static final String PLAYERS_DIR = "/players";
    public static final String REGISTER_DIR = "/register";
    public static final String FAVORITES_DIR = "/favorites";
    public static final String VERIFY_DIR = "/verify";
    public static final String SUCCESS_MESSAGE = "Your account has been verified. You can now log in.";
    public static final String EXPIRED_CODE_MESSAGE = "Invalid or expired verification code.";
    public static final String SPRING_MAIL_USERNAME = "spring.mail.username";
    public static final String EMAIL_VERIFICATION_SUBJECT = "Email Verification";
    public static final String LINK_VERIFY = "http://localhost:8080/verify?code=";
    public static final String PLEASE_CLICK_ON_THE_LINK_TO_VERIFY_YOUR_EMAIL = "Please click on the link to verify your email: ";
    public static final String ID_ARGUMENT = "/{id}";
    public static final String HEADER_PROVIDED_NEEDED = "Authorization header must be provided";
    public static final String BEARER = "Bearer ";
    public static final String SERVIDOR = "Servidor";
    public static final String SHA_512 = "SHA-512";
    public static final String AES = "AES";
    public static final String CLAVE = "clave";
    public static final String INIT_EXP = "${";
    public static final String END_EXP = "}";
    public static final String USERNAME = "username";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String REFRESH_DIR = "/refresh";
    public static final String USER_ID_S = "userId";
    public static final String COMPULSORY_FILLED = "All fields must be filled";
    public static final String PLAYER_NOT_FOUND = "Player not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String USER_NOT_VERIFIED = "User not verified";
    public static final String PLAYER_FAVORITE_ALREADY_EXISTS = "Player favorite already exists";
    public static final String INVALID_VERIFICATION_CODE = "Invalid verification code";
    public static final String INVALID_CRED_OR_NOT_VERIFIED = "Invalid credentials or user not verified";
    public static final String FAV_PLAYERS_TWO_DIR = "/{credentialId}/{playerId}";
    public static final String FAV_PLAYERS_ONE_DIR = "/{credentialId}";

}
