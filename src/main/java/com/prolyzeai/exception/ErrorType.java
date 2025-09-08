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
    BAD_REQUEST_ERROR(1000, "The entered parameters are incorrect. Please correct them", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1001, "Token is invalid", HttpStatus.BAD_REQUEST),
    TOKEN_CREATION_FAILED(1002, "Error creating token", HttpStatus.SERVICE_UNAVAILABLE),
    TOKEN_VERIFY_FAILED(1003, "Failed to verify token", HttpStatus.SERVICE_UNAVAILABLE),
    TOKEN_EXPIRED(1004, "Token Expired", HttpStatus.BAD_REQUEST),
    NOT_AUTHORIZED(1005, "Not Authorized", HttpStatus.BAD_REQUEST),
    AUTH_NOT_FOUND(1006, "Auth data not found", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(1007, "Invalid refresh token", HttpStatus.BAD_REQUEST),
    SYSTEM_ERROR(1008, "System error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TIME_FORMAT(1009, "Invalid time format", HttpStatus.BAD_REQUEST),

    // 2000s - User / Account Errors
    EMAIL_TAKEN(2000, "Email is Taken", HttpStatus.BAD_REQUEST),
    PHONE_TAKEN(2001, "Phone Number Is Taken", HttpStatus.BAD_REQUEST),
    EMAIL_OR_PHONE_TAKEN(2002, "Email or Phone Number Is Taken", HttpStatus.BAD_REQUEST),
    EMAIL_OR_PASSWORD_WRONG(2003, "Email or Password Wrong", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_OR_EMAIL(2004, "Invalid Password or Email", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(2005, "User Not Found", HttpStatus.BAD_REQUEST),
    USER_ALREADY_DELETED(2006, "User Already Deleted", HttpStatus.BAD_REQUEST),
    ACCOUNT_STATUS_ERROR(2007, "Account status error", HttpStatus.BAD_REQUEST),
    INVALID_ACCOUNT(2008, "Account is not active", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(2009, "Wrong Password", HttpStatus.BAD_REQUEST),
    SUBSCRIPTION_EXPIRED(2010, "Subscription Expired. Please contact support", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER_FORMAT(2011, "Invalid Phone Number Format", HttpStatus.BAD_REQUEST),

    // 3000s - Email / Notifications Errors
    MAIL_SEND_FAIL(3000, "Failed to send email.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOTIFICATION_NOT_FOUND(3001, "Notification Not Found", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_VALID(3002, "Email Not Valid", HttpStatus.BAD_REQUEST),

    // 4000s - Clinic Errors
    CLINIC_NOT_FOUND(4000, "Clinic Not Found", HttpStatus.BAD_REQUEST),
    LOGO_NOT_FOUND(4001, "Logo Not Found", HttpStatus.BAD_REQUEST),
    MONTHLY_PATIENT_LIMIT_EXCEEDED(4002, "Monthly Patient Limit Exceeded. Please Get In Touch With Support", HttpStatus.BAD_REQUEST),
    STAFF_LIMIT_REACHED(4003, "Staff Limit Reached. Please Get In Touch With Support", HttpStatus.BAD_REQUEST),
    COMPANY_NOT_FOUND(4004, "Şirket bulunamadı", HttpStatus.BAD_REQUEST),

    // 5000s - Patient / Appointment Errors
    PATIENT_NOT_FOUND(5001, "Patient Not Found", HttpStatus.BAD_REQUEST),
    NO_AVAILABLE_SLOT(5002, "No Slots Available", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_FOUND(5003, "Appointment Not Found", HttpStatus.BAD_REQUEST),
    POST_OP_NOT_FOUND(5004, "Post Op Not Found", HttpStatus.BAD_REQUEST),
    PACKAGE_CATEGORY_NOT_FOUND(5005, "Package Category Not Found",  HttpStatus.BAD_REQUEST),
    PACKAGE_NOT_FOUND(5006, "Package Not Found",  HttpStatus.BAD_REQUEST),

    //6000s - ? Upload Errors
    INVALID_PHOTO_TYPE(6001, "Invalid Photo Type", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(6002, "File Not Found", HttpStatus.BAD_REQUEST),
    FILE_DELETE_ERROR(6003, "File could not be deleted", HttpStatus.BAD_REQUEST),
    FILE_UPDATE_ERROR(6004, "File could not be updated", HttpStatus.BAD_REQUEST),
    FILE_SAVE_ERROR(6005, "File could not be saved", HttpStatus.BAD_REQUEST),
    FILE_TOO_LARGE(6006, "File is too large", HttpStatus.BAD_REQUEST),
    INVALID_FILE_TYPE(6007, "Invalid File Type", HttpStatus.BAD_REQUEST),

    ;
    //7000s - ? Errors
    //8000s - ? Errors
    //9000s - ? Errors

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}