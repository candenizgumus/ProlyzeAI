package com.prolyzeai.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum representing different types of errors that can occur in the application.
 * Each error type has a unique code, message, and HTTP status.
 * DONT CHANGE THE ERROR CODES!!!
 *
 * @author hcaslan
 */
@Getter
@AllArgsConstructor
public enum ErrorType
{
    // 1000s - Auth & Token & System Errors
    BAD_REQUEST_ERROR(1000, "Girilen parametreler hatalı. Lütfen düzeltin", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1001, "Geçersiz token", HttpStatus.BAD_REQUEST),
    TOKEN_CREATION_FAILED(1002, "Token oluşturulurken hata oluştu", HttpStatus.SERVICE_UNAVAILABLE),
    TOKEN_VERIFY_FAILED(1003, "Token doğrulanamadı", HttpStatus.SERVICE_UNAVAILABLE),
    TOKEN_EXPIRED(1004, "Token süresi dolmuş", HttpStatus.BAD_REQUEST),
    NOT_AUTHORIZED(1005, "Yetkiniz yok", HttpStatus.BAD_REQUEST),
    AUTH_NOT_FOUND(1006, "Kimlik doğrulama verisi bulunamadı", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(1007, "Geçersiz yenileme token'ı", HttpStatus.BAD_REQUEST),
    SYSTEM_ERROR(1008, "Sistem hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TIME_FORMAT(1009, "Geçersiz zaman formatı", HttpStatus.BAD_REQUEST),

    // 2000s - User / Account Errors
    EMAIL_TAKEN(2000, "Bu e-posta adresi kullanılmaktadır", HttpStatus.BAD_REQUEST),
    PHONE_TAKEN(2001, "Bu telefon numarası kullanılmaktadır", HttpStatus.BAD_REQUEST),
    EMAIL_OR_PHONE_TAKEN(2002, "E-posta veya telefon numarası baska kullanılar tarafından kullanılmaktadır", HttpStatus.BAD_REQUEST),
    EMAIL_OR_PASSWORD_WRONG(2003, "E-posta veya şifre hatalı", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_OR_EMAIL(2004, "Geçersiz şifre veya e-posta", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(2005, "Kullanıcı bulunamadı", HttpStatus.BAD_REQUEST),
    USER_ALREADY_DELETED(2006, "Kullanıcı zaten silinmiş", HttpStatus.BAD_REQUEST),
    ACCOUNT_STATUS_ERROR(2007, "Hesap durum hatası", HttpStatus.BAD_REQUEST),
    INVALID_ACCOUNT(2008, "Hesap aktif değil", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(2009, "Hatalı şifre", HttpStatus.BAD_REQUEST),
    SUBSCRIPTION_EXPIRED(2010, "Abonelik süreniz doldu. Lütfen destek ekibiyle iletişime geçin", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER_FORMAT(2011, "Geçersiz telefon numarası formatı", HttpStatus.BAD_REQUEST),

    // 3000s - Email / Notifications Errors
    MAIL_SEND_FAIL(3000, "E-posta gönderilemedi", HttpStatus.INTERNAL_SERVER_ERROR),
    NOTIFICATION_NOT_FOUND(3001, "Bildirim bulunamadı", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_VALID(3002, "Geçersiz e-posta adresi", HttpStatus.BAD_REQUEST),

    // 4000s - Clinic Errors
    CLINIC_NOT_FOUND(4000, "Klinik bulunamadı", HttpStatus.BAD_REQUEST),
    LOGO_NOT_FOUND(4001, "Logo bulunamadı", HttpStatus.BAD_REQUEST),
    MONTHLY_PATIENT_LIMIT_EXCEEDED(4002, "Aylık hasta limiti aşıldı. Lütfen destek ekibiyle iletişime geçin", HttpStatus.BAD_REQUEST),
    STAFF_LIMIT_REACHED(4003, "Personel limitine ulaşıldı. Lütfen destek ekibiyle iletişime geçin", HttpStatus.BAD_REQUEST),
    COMPANY_NOT_FOUND(4004, "Şirket bulunamadı", HttpStatus.BAD_REQUEST),

    // 5000s - Patient / Appointment Errors
    PATIENT_NOT_FOUND(5001, "Hasta bulunamadı", HttpStatus.BAD_REQUEST),
    NO_AVAILABLE_SLOT(5002, "Uygun randevu aralığı bulunamadı", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_FOUND(5003, "Randevu bulunamadı", HttpStatus.BAD_REQUEST),
    POST_OP_NOT_FOUND(5004, "Ameliyat sonrası kaydı bulunamadı", HttpStatus.BAD_REQUEST),
    PACKAGE_CATEGORY_NOT_FOUND(5005, "Paket kategorisi bulunamadı",  HttpStatus.BAD_REQUEST),
    PACKAGE_NOT_FOUND(5006, "Paket bulunamadı",  HttpStatus.BAD_REQUEST),

    //6000s - ? Upload Errors
    INVALID_PHOTO_TYPE(6001, "Geçersiz fotoğraf türü", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(6002, "Dosya bulunamadı", HttpStatus.BAD_REQUEST),
    FILE_DELETE_ERROR(6003, "Dosya silinemedi", HttpStatus.BAD_REQUEST),
    FILE_UPDATE_ERROR(6004, "Dosya güncellenemedi", HttpStatus.BAD_REQUEST),
    FILE_SAVE_ERROR(6005, "Dosya kaydedilemedi", HttpStatus.BAD_REQUEST),
    FILE_TOO_LARGE(6006, "Dosya boyutu çok büyük", HttpStatus.BAD_REQUEST),
    INVALID_FILE_TYPE(6007, "Geçersiz dosya türü", HttpStatus.BAD_REQUEST),

    ;
    //7000s - ? Hatalar
    //8000s - ? Hatalar
    //9000s - ? Hatalar

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}