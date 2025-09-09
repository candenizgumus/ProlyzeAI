package com.prolyzeai.constants;

public class Endpoints
{
    //Server
    public static final String SERVER = "https://www.clinic-flow-app.com/";

    //profiles
    public static final String DEV = "/dev";
    // version
    public static final String VERSION = "/v1";

    public static final String ROOT = DEV + VERSION ;

    //controllers
    public static final String ADMIN = "/admin";
    public static final String ITEM = "/item";
    public static final String CATEGORY = "/category";
    public static final String VIDEO = "/video";
    public static final String COMPANY = "/company";
    public static final String MANAGER = "/manager";
    public static final String USER = "/user";
    public static final String S3 = "/s3";
    public static final String CLINIC = "/clinic";
    public static final String FIREBASE = "/firebase";
    public static final String OPERATION = "/operation";
    public static final String FILE = "/file";
    public static final String AUTH = "/auth";
    public static final String SCHEDULER = "/scheduler";
    public static final String NOTIFICATION = "/notification";
    public static final String DASHBOARD = "/dashboard";
    public static final String CLINIC_MANAGER = "/clinic-manager";
    public static final String PATIENT = "/patient";
    public static final String PATIENT_APPOINTMENT = "/patient-appointment";
    public static final String CASH_FLOW = "/cash-flow";
    public static final String OPERATION_PACKAGE_CATEGORY = "/operation-package-category";
    public static final String POST_OP_REVIEW = "/post-op-review";

    //methods
    public static final String SAVE = "/save";
    public static final String DELETE = "/delete";
    public static final String UPDATE = "/update";
    public static final String FIND_ALL = "/find-all";
    public static final String SAVE_BY_MANAGER = "/save-by-manager";
    public static final String SAVE_BY_PATIENT = "/save-by-patient";
    public static final String SAVE_FIREBASE_TOKEN_TO_CLINIC = "/save-firebase-token-to-clinic";
    public static final String SAVE_FIREBASE_TOKEN_TO_PATIENT = "/save-firebase-token-to-patient";
    public static final String FIND_ALL_NOTIFICATIONS_OF_CLINIC = "/find-all-notifications-of-clinic";
    public static final String FIND_ALL_NOTIFICATIONS_OF_PATIENT = "/find-all-notifications-of-patient";
    public static final String FIND_ALL_CONFIRMED_APPOINTMENTS = "/find-all-confirmed-appointments";
    public static final String FIND_ALL_CONFIRMED_APPOINTMENTS_BEFORE_TODAY = "/find-all-confirmed-appointments-before-today";
    public static final String FIND_ALL_COMPLETED_APPOINTMENTS_BEFORE_TODAY = "/find-all-completed-appointments-before-today";
    public static final String FIND_ALL_STAFF = "/find-all-staff";
    public static final String POPULATE_REVIEW_DATES = "/populate-review-dates";

    public static final String FIND_BY_ID = "/find-by-id";
    public static final String GET_ALL_INCOME_AND_EXPENSE = "/get-all-income-and-expense";
    public static final String FIND_BY_TOKEN = "/find-by-token";
    public static final String SWITCH_WHITELABEL = "/switch-whitelabel";
    public static final String FIND_TOP_5_BY_FLEXIBLE_NAME_SEARCH = "/find-top-5-by-flexible-name-search";
    public static final String GET_BY_APPOINTMENT_ID = "/get-by-appointmentId";
    public static final String ADD_PATIENT_PHOTO = "/add-patient-photo";
    public static final String GET_PRE_OP_PHOTOS = "/get-pre-op-photos";
    public static final String GET_POST_OP_PHOTOS = "/get-post-op-photos";
    public static final String GET_LOGO = "/get-logo";
    public static final String GET_VIDEO = "/get-video";
    public static final String CHANGE_STATUS = "/change-status";
    public static final String GET_NAME_SURNAME_OF_USER_BY_TOKEN = "/get-name-surname-of-user-by-token";
    public static final String UPLOAD_TO_PRE_OP = "/upload-to-pre-op";
    public static final String UPLOAD_PHOTO_TO_PRE_OP_APPOINTMENT = "/upload-photo-to-pre-op-appointment";
    public static final String UPLOAD_DOCUMENT_TO_PATIENT = "/upload-document-to-patient";
    public static final String UPDATE_PRE_OP_APPOINTMENT_PHOTO = "/update-pre-op-appointment-photo";
    public static final String DELETE_PRE_OP_APPOINTMENT_PHOTO = "/delete-pre-op-appointment-photo";
    public static final String DELETE_POST_OP_REVIEW_PHOTO = "/delete-post-op-review-photo";
    public static final String UPLOAD_REVIEW_PHOTOS = "/upload-review-photos";
    public static final String UPLOAD_CLINIC_LOGO = "/upload-clinic-logo";
    public static final String UPLOAD_VIDEO_TO_CLINIC = "/upload-video-to-Clinic";
    public static final String APPROVE_OR_REJECT_APPOINTMENT = "/approve-or-reject-appointment";
    public static final String FIND_ALL_MONTHLY_APPOINTMENTS = "/find-all-monthly-appointments";
    public static final String FIND_ALL_APPOINTMENTS_OF_PATIENT = "/find-all-appointments-of-patient";
    public static final String FIND_ALL_PENDING_APPOINTMENTS = "/find-all-pending-appointments";
    public static final String CHANGE_STAGE = "/change-stage";
    public static final String SET_APPOINTMENT_START_AND_END_TIME = "/set-appointment-start-and-end-time";
    public static final String GET_ALL_APPOINTMENTS_WITH_TIMES = "/get-all-appointments-with-times";
    public static final String FIND_ALL_COMPLETED_APPOINTMENTS = "/find-all-completed-appointments";
    public static final String ADD_SUBSCRIPTION = "/add-subscription";
    public static final String FIND_ALL_BY_TOKEN = "/find-all-by-token";
    public static final String LOGIN = "/login";
    public static final String SET_AVAILABLE_SLOT = "/set-avalaible-slot";
    public static final String CHECK_SLOT_BY_DATE = "/check-slot-by-date";
    public static final String GET_AVAILABILITY_OF_MONTH = "/get-availability-of-month";
    public static final String SET_MONTHLY_PATIENT_LIMIT = "/set-monthly-patient-limit";
    public static final String ADD_MANAGER_TO_CLINIC = "/add-manager-to-clinic";
    public static final String CHANGE_STAFF_STATUS = "/change-staff-status";
    public static final String SET_STAFF_LIMIT_OF_CLINIC = "/set-staff-limit-of-clinic";
    public static final String GET_MONTHLY_DATA_OF_CLINIC = "/get-monthly-data-of-clinic";
    public static final String GET_RECENT_APPOINTMENTS = "/get-recent-appointments";
    public static final String FIND_BY_CATEGORY =  "/find-by-category";
    public static final String REFRESH_ACCESS_TOKEN = "/refresh-access-token";
    public static final String LOGOUT = "/logout";
    public static final String GET_REMAINING_SUBSCRIPTION_TIME = "/get-remaining-subscription-time";
    public static final String CHANGE_PASSWORD = "/change-password";
    public static final String RESET_PASSWORD_REQUEST = "/reset-password-request";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String FORGOT_PASSWORD = "/forgot-password";
    public static final String MAKE_REVIEW = "/make-review";
    public static final String DELETE_REVIEW = "/delete-review";
    public static final String READ_NOTIFICATION = "/read-notification";
    public static final String GET_TODAY_APPOINTMENTS = "/get-today-appointments";
    public static final String CONFIRMED_APPOINTMENT_COUNT = "/confirmed-appointment-count";
    public static final String MONTHLY = "/monthly";
    public static final String PREVIOUS_MONTH = "/previous-month";
    public static final String NEXT_MONTH = "/next-month";

    //Notifications
    public static final String APPOINTMENT_SAVED_BY_MANAGER = "New Appointment! Date: ";
    public static final String APPOINTMENT_SAVED_BY_PATIENT = "New appointment request from: ";
    public static final String WELCOME_TO_CLINIC_FLOW = "Welcome to ClinicFlow";
    public static final String GOT_NEW_REVIEW = "Your post-op photo has been reviewed!";
    public static final String PATIENT_UPLOADED_POST_OP_PHOTO = " uploaded post op photo. Date: ";
    public static final String PATIENT_UPLOADED_PRE_OP_PHOTO = " uploaded pre op photo. Date: ";

    //S3
    public static final String BUCKET_NAME = "clinic-flow-bucket";
}
