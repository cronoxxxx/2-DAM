package org.example.loginspring_adriansaavedra.common;

public class Constantes {
    public static final String LOGIN_DIR = "/login";
    public static final String PLAYERS_DIR = "/players";
    public static final String REGISTER_DIR = "/register";
    public static final String VERIFY_DIR = "/verify";
    public static final String ERROR_MESSAGE = "Invalid username or password, or account not verified";
    public static final String SUCCESS_MESSAGE = "Your account has been verified. You can now log in.";
    public static final String SUCCESS_REGISTER_MESSAGE = "Registration successful. Please check your email to verify your account.";
    public static final String ERROR_REGISTER_MESSAGE = "Registration failed. Username already registered or any empty field.";
    public static final String EXPIRED_CODE_MESSAGE = "Invalid or expired verification code.";
    public static final String SPRING_MAIL_USERNAME = "spring.mail.username";
    public static final String EMAIL_VERIFICATION_SUBJECT = "Email Verification";
    public static final String LINK_VERIFY = "http://localhost:8080/verify?code=";
    public static final String PLEASE_CLICK_ON_THE_LINK_TO_VERIFY_YOUR_EMAIL = "Please click on the link to verify your email: ";
    public static final String ID_ARGUMENT = "/{id}";
    public static final String HEADER_PROVIDED_NEEDED = "Authorization header must be provided";
    public static final String BEARER = "Bearer ";
    public static final String USER_S = "user";
    public static final String SERVIDOR = "Servidor";
    public static final String SHA_512 = "SHA-512";
    public static final String AES = "AES";
    public static final String CLAVE = "clave";
    public static final String INIT_EXP = "${";
    public static final String END_EXP = "}";
}
