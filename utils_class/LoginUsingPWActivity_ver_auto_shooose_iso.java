package com.zing.zalo.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.helper.FacebookConnector;
import com.facebook.worker.TaskExecutor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.vng.mobileTracking.android.sdk.ZMobileTracking;
import com.zing.zalo.BuildConfig;
import com.zing.zalo.R;
import com.zing.zalo.actionlog.ActionLog;
import com.zing.zalo.actionlog.constant.ActionConstant;
import com.zing.zalo.app.MainApplication;
import com.zing.zalo.business.ZaloBusiness;
import com.zing.zalo.business.ZaloBusinessImpl;
import com.zing.zalo.common.NotiManager;
import com.zing.zalo.common.RequestLocation;
import com.zing.zalo.common.RequestLocationBase;
import com.zing.zalo.common.TrackingId;
import com.zing.zalo.connection.socket.SocketConnection;
import com.zing.zalo.control.ContactProfile;
import com.zing.zalo.data.GlobalData;
import com.zing.zalo.data.SharedPreferencesData;
import com.zing.zalo.db.DatabaseChatProxy;
import com.zing.zalo.db.backup.BackupInfo;
import com.zing.zalo.db.backup.BackupRestoreChatDB;
import com.zing.zalo.db.model.ServiceMap;
import com.zing.zalo.dialog.ConfirmActivePhoneDialog;
import com.zing.zalo.dialog.MaterialZaloDialog;
import com.zing.zalo.parser.BackgroundParser;
import com.zing.zalo.parser.CountryCodeParser;
import com.zing.zalo.pojo.SecurityQuestion;
import com.zing.zalo.qrcode.ui.ProgressDialogCustom;
import com.zing.zalo.ui.fragments.MainFragment;
import com.zing.zalo.uicontrol.CustomEditText;
import com.zing.zalo.utils.ActivityCompatUtil;
import com.zing.zalo.utils.AppLog;
import com.zing.zalo.utils.ConfigAQueryUtils;
import com.zing.zalo.utils.LocationUtils;
import com.zing.zalo.utils.NetworkUtils;
import com.zing.zalo.utils.ThreadUtils;
import com.zing.zalo.utils.Utils;
import com.zing.zalo.utils.phonenumbers.PhoneNumberUtil;
import com.zing.zalo.utils.phonenumbers.Phonenumber;
import com.zing.zalo.webview.WebViewActivity;
import com.zing.zalo.worker.HandleLogin;
import com.zing.zalo.worker.PhoneContactWorker;
import com.zing.zalocore.CoreUtility;
import com.zing.zalocore.business.BusinessListener;
import com.zing.zalocore.business.ERROR;
import com.zing.zalocore.business.ErrorMessage;
import com.zing.zalocore.connection.HttpHelper;
import com.zing.zalocore.connection.RequestBase;
import com.zing.zalocore.connection.UrlHelper;
import com.zing.zalocore.qos.QOSCode;
import com.zing.zalocore.qos.QOSUtils;
import com.zing.zalocore.utils.Log;
import com.zing.zalocore.utils.Md5Generator;
import com.zing.zalocore.utils.PasswordUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

//import com.actionbarsherlock.view.MenuItem;
//import com.zing.zalo.theme.ResourceHelper;
//import com.zing.zalo.utils.ThemeUtils;

public class LoginUsingPWActivity extends MultiFragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final int REQUEST_CODE_SECURITY_QUESTION = 1001;

    private final String TAG = LoginUsingPWActivity.class.getSimpleName();
    private static final String LAST_SUBMIT_PHONE = "last_submit_phone_num";
    private static final String WRONG_SUBMIT_PHONE_COUNT = "wrong_submit_phone_count";

    // UI - EditText
    private CustomEditText etPhoneNumber;
    private CustomEditText etPassword;

    // UI - TextView
    private TextView tvError;
    private TextView tvCountryName;
    private TextView tvForgotPassword;

    // UI - Button
    private Button btnLogin;

    // UI - Checkbox
    private TextView tvShowPassword;
    private TextView tvFAQ;

    // Dialogs
    private final int ASK_TO_GET_ACTIVECODE_BY_SMS = 0;
    private final int ASK_TO_GET_ACTIVECODE_BY_CALL = 1;
    private final int WARNING_MSG_UP_TO_DATE_APP = 2;
    private static final int WARNING_RE_ACTIVATION_BY_SECURITY_QUESTION = 3;
    private final int ASK_TO_GETACTIVECODEBYREJECTCALL = 4;
    private static final int DIALOG_VERIFY_PHONE_NUMBER = 5;
    private static final int DIALOG_REGISTER_NEW_USER = 6;
    private ProgressDialogCustom loadingDialog = null;

    // Data
    private String sPhoneNumber, sPassword;
    private String passwordAfterEncoded = "";
    private int sms_left = 0;
    private int voice_left = 0;
    private int submitWrongNumber = 0;
    private String lastPhoneNumber = "";

    // Business
    private ZaloBusiness bussinessActivePassword;
    private BusinessListener activePasswordListener;
    private BusinessListener isSetPasswordListener;

    // Status
    private boolean isFromStartUp = false;
    private boolean isActivated = false;
    private boolean isSupportVoiceCall = false;
    private boolean isValidKey = false;
    private boolean isSet = false;
    private boolean is_new = false;
    private boolean isdoingActivePassWord = false;
    private boolean isRequestingIsSetPassword = false;
    private int iRejectedCallQuota = 0;
//	private int iTimeout = 0;


    private String errorMsg_VERSION_END_SUPPORT_ERROR = "";

    private SecurityQuestion mSecurityQuestion;
    private String mSavePassword;//for submit security question answer
    private boolean isShowPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginbypassword);
        AppLog.WriteLogs(TAG);

        // InitUI
        setTitle(getString(R.string.login_title));

        buildGoogleApiClient();

        btnLogin = (Button) findViewById(R.id.layoutgetcode);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionLog.startLog(ActionConstant.FORM_PASSWORD_NEXT, "");
                ActionLog.endLog();
                sPhoneNumber = etPhoneNumber.getText().toString().trim();
                sPassword = etPassword.getText().toString();
                if (checkInputData(true, true)) {
                    mSecurityQuestion = null;
                    mSavePassword = null;
                    GlobalData.tmp_phoneNumber = sPhoneNumber;
                    if (!sPhoneNumber.equals(lastPhoneNumber)) {
                        lastPhoneNumber = sPhoneNumber;
                        submitWrongNumber = 0;
                    }
                    activePassword(SharedPreferencesData.getIsoCountryCode(MainApplication.getAppContext()),
                            sPhoneNumber,
                            sPassword,
                            "1",
                            ConfigAQueryUtils.getAvatarSize());
                }
            }
        });

        etPhoneNumber = (CustomEditText) findViewById(R.id.etPhoneNumber);
        etPassword = (CustomEditText) findViewById(R.id.etPassword);
        // execute button DONE on keyboard
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (btnLogin != null) {
                        btnLogin.performClick();
                    }
                    return true;
                }
                return false;
            }
        });

        etPassword.setTypeface(Typeface.DEFAULT);

        tvShowPassword = (TextView) findViewById(R.id.tv_show_password);
        tvShowPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isShowPassword = !isShowPassword;
                if (isShowPassword) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etPassword.setSelection(etPassword.getText().length());
                    tvShowPassword.setText(getString(R.string.startup_hide_password));
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword.setSelection(etPassword.getText().length());
                    tvShowPassword.setText(getString(R.string.startup_show_password));
                }
                etPassword.setTypeface(Typeface.DEFAULT);
            }
        });


        tvError = (TextView) findViewById(R.id.tvError);
        tvError.setVisibility(View.GONE);

        tvCountryName = (TextView) findViewById(R.id.tvCountryName);
        tvCountryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent(LoginUsingPWActivity.this, com.zing.zalo.ui.CountryListActivity.class);
                startActivity(localIntent);
            }
        });

        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setPaintFlags(tvForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionLog.startLog(ActionConstant.FORM_PASSWORD_UNKNOWN, "");
                ActionLog.endLog();
                if (etPhoneNumber != null)
                    sPhoneNumber = etPhoneNumber.getText().toString().trim();

                if (checkInputData(true, false)) {
                    GlobalData.tmp_phoneNumber = sPhoneNumber;
                    isSetPassword(GlobalData.tmp_phoneNumber);
                }
            }
        });

        tvFAQ = (TextView) findViewById(R.id.tvFAQ);
        tvFAQ.setPaintFlags(tvFAQ.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvFAQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent(LoginUsingPWActivity.this, PolicyActivity.class);
                localIntent.putExtra(PolicyActivity.OPEN_URL, GlobalData.URL_FAQ);
                startActivity(localIntent);
            }
        });

        // InitBusiness
        activePasswordListener = new BusinessListener() {
            @Override
            public void onErrorData(final ErrorMessage error_message) {

                try {
                    if (isActive() && (error_message.getError_code() == ERROR.REQUIRE_ANSWER || error_message.getError_code() == ERROR.ANSWER_NOT_CORRECT_AND_NEXT_QUESTION) && !TextUtils.isEmpty(error_message.getData())) {
                        try {
                            JSONObject data = new JSONObject(error_message.getData());
                            data = data.getJSONObject("data");
                            data = data.getJSONObject("question");
                            SecurityQuestion question = SecurityQuestion.parseObjectFromJson(data);
                            Intent intent = new Intent(LoginUsingPWActivity.this, JoinSecurityQuestionActivity.class);
                            intent.putExtra(SecurityQuestionActivity.EXTRA_QUESTION, question);
                            intent.putExtra(SecurityQuestionActivity.EXTRA_PASSWORD, !TextUtils.isEmpty(mSavePassword) ? mSavePassword : sPassword);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Thread.sleep(500L);
                            startActivityForResult(intent, REQUEST_CODE_SECURITY_QUESTION);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (tvError != null) {
                                        tvError.setVisibility(View.VISIBLE);
                                        tvError.setText(R.string.security_question_activity_error_request_answer_security_question);
                                    }
                                }
                            });

                            isdoingActivePassWord = false;
                            dismissLoadingDialog();
                            return;
                        } catch (Exception e) {
                            Log.e(TAG, e);
                        }
                    }

                    if (isActive() && error_message.getError_code() != ERROR.NETWORK_ERROR)
                        ServiceMap.syncServiceMap(true);

                    runOnUiThread(new Runnable() {
                        @SuppressWarnings("deprecation")
                        @Override
                        public void run() {
                            if (error_message != null) {
                                if (error_message.getError_code() == ERROR.USER_NOT_FOUND) {
                                    if (submitWrongNumber > 0) {
                                        if (tvError != null) {
                                            tvError.setVisibility(View.GONE);
                                        }
                                        showAppDialog(DIALOG_VERIFY_PHONE_NUMBER);
                                    } else {
                                        submitWrongNumber++;
                                        if (tvError != null) {
                                            tvError.setVisibility(View.VISIBLE);
                                            tvError.setText(MainApplication.getAppContext().getString(R.string.PASSWORD_DONT_MATCH_USERNAME_MSG));
                                        }
                                    }
                                } else if (error_message.getError_code() == ERROR.ANSWER_NOT_CORRECT_AND_FAIL || error_message.getError_code() == ERROR.QUESTION_ALREADY_EXCEED) {
                                    removeDialog(WARNING_RE_ACTIVATION_BY_SECURITY_QUESTION);
                                    showDialog(WARNING_RE_ACTIVATION_BY_SECURITY_QUESTION);
                                /*} else if (error_message.getError_code()==ERROR.QUESTION_ALREADY_EXCEED) {
	                                if(tvError != null) {
	                                    tvError.setVisibility(View.VISIBLE);
	                                    tvError.setText(R.string.security_question_activity_error_question_exceed);
	                                }*/
                                } else if (error_message.getError_code() != ERROR.VERSION_END_SUPPORT) {
                                    if (tvError != null) {
                                        tvError.setVisibility(View.VISIBLE);
                                        tvError.setText(error_message.getError_message());
                                    }
                                } else {
                                    errorMsg_VERSION_END_SUPPORT_ERROR = !TextUtils.isEmpty(error_message.getError_message()) ? error_message.getError_message() : "";
                                    removeDialog(WARNING_MSG_UP_TO_DATE_APP);
                                    showDialog(WARNING_MSG_UP_TO_DATE_APP);
                                }

                                if (error_message.getError_code() == ERROR.INVALID_PHONE_NUMBER && etPhoneNumber != null) {
                                    etPhoneNumber.requestFocus();
                                } else if (error_message.getError_code() == ERROR.PASSWORD_DONT_MATCH_USERNAME && etPassword != null) {
                                    //ThemeUtils.setBackgroundResource(LoginUsingPWActivity.this, etPassword, R.drawable.stencil_edit_text_error);
                                    etPassword.requestFocus();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, e);
                }

                isdoingActivePassWord = false;
                dismissLoadingDialog();
            }

            @Override
            public void onDataProcessed(Object entity) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isActive()) {
                                if (tvError != null) {
                                    tvError.setVisibility(View.GONE);
                                }
                            }
                        }
                    });

                    if (entity != null) {
                        JSONObject data = (JSONObject) entity;
                        JSONObject d = new JSONObject(data.getString("data"));

                        Log.d(TAG, "login:" + d);

//						if(!d.isNull("user_name"))
//						{
//							String userName = d.getString("user_name");
//							if(userName != null && userName.length() > 0 && !userName.trim().equals("null"))
//								GlobalData.userName = d.getString("user_name");
//						}

                        if (d.has("user_id")) {
                            CoreUtility.currentUserUid = d.getString("user_id");
//                            ResourceHelper.reset(MainApplication.getAppContext());
                            SharedPreferencesData.setCurrentUserUid(MainApplication.getAppContext(), CoreUtility.currentUserUid);
                            //Reset chat backgrounds
                            BackgroundParser.resetBackgroundParser();
                        }
                        if (d.has("session_key"))
                            CoreUtility.sessionKey = d.getString("session_key");

                        HandleLogin.isNeedUpdateSession = false;

                        if (d.has("timestamp")) {
                            long timeStartApp = System.currentTimeMillis();
                            SharedPreferencesData.setTimeStartApp(MainApplication.getAppContext(), timeStartApp);
                            long loginTime = d.getLong("timestamp");
                            SharedPreferencesData.setLoginTime(MainApplication.getAppContext(), loginTime);
                        }
                        if (d.has("sign"))
                            SharedPreferencesData.setSign(MainApplication.getAppContext(), d.getString("sign"));

                        if (d.has("token"))
                            GlobalData.token = d.getString("token");

                        if (d.has("is_new")) {
                            is_new = d.getBoolean("is_new");
                        }

                        if (is_new) {
                            SharedPreferencesData.setAutoUpdatePhonebook(MainApplication.getAppContext(), true);
                        }

//						if(d.has("cer_key"))
//						{
//							String cer_key = d.getString("cer_key");
//							try {
//								SecurityUtils.encryptCerKey(cer_key);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}

                        SharedPreferencesData.parseIPPortServer(d);

                        if (d.has("jp_url")) {
                            SharedPreferencesData.setJumpUrl(MainApplication.getAppContext(), d.optString("jp_url"));
                        }

                        if (!d.isNull("facebook") && !is_new) {
                            JSONObject fbInfo = d.getJSONObject("facebook");

                            if (!fbInfo.isNull("fid") && fbInfo.getString("fid").length() > 5) {
                                GlobalData.fid = fbInfo.getString("fid");
                                SharedPreferencesData.setfacebookID(MainApplication.getAppContext(), GlobalData.fid);
                            }

                            if (!fbInfo.isNull("fname") && fbInfo.getString("fname").length() > 0 && !fbInfo.getString("fname").equalsIgnoreCase("null")) {
                                GlobalData.fname = fbInfo.getString("fname");
                                SharedPreferencesData.setfacebookName(MainApplication.getAppContext(), GlobalData.fname);
                            }

                            if (!fbInfo.isNull("access_token") && fbInfo.getString("access_token").length() > 5) {
                                GlobalData.fAccess_token = fbInfo.getString("access_token");
                                SharedPreferencesData.setfacebookAccessToken(MainApplication.getAppContext(), GlobalData.fAccess_token);
                                FacebookConnector.restoreSession(GlobalData.fAccess_token, GlobalData.fid);
                            }
                        }

                        if (!d.isNull("google") && !is_new) {
                            try {
                                JSONObject info = d.getJSONObject("google");
                                String gid = info.getString("gid");
                                if (gid == null || !gid.equals(SharedPreferencesData.getGooglePlusID(MainApplication.getAppContext()))) {
                                    SharedPreferencesData.setGooglePlusID(MainApplication.getAppContext(), "");
                                    SharedPreferencesData.setGooglePlusCurrentZaloLink(MainApplication.getAppContext(), "");
                                    SharedPreferencesData.setGooglePlusName(MainApplication.getAppContext(), "");
                                }
                            } catch (Exception e) {
                                Log.e(TAG, e);
                            }
                        }

                        HandleLogin.getInstance().parseIMEIStatus(d);

                        if (CoreUtility.currentUserUid != null && CoreUtility.currentUserUid.length() > 0)
                            Utils.sendNotificationLoginPhone(CoreUtility.currentUserUid);

                        SharedPreferencesData.setContact_isNew(MainApplication.getAppContext(), is_new);
                        SharedPreferencesData.setValidToken(MainApplication.getAppContext(), "1");
                        GlobalData.phoneNumber = GlobalData.tmp_phoneNumber;
                        SharedPreferencesData.saveInfoSettings(MainApplication.getAppContext());

                        try {
                            if (!d.isNull("profile")) {
                                JSONObject myInfo = d.getJSONObject("profile");
                                GlobalData.userInfo = new ContactProfile(myInfo);
                                GlobalData.userInfo.phone = GlobalData.phoneNumber;
                                SharedPreferencesData.setUserInfo(MainApplication.getAppContext(), GlobalData.userInfo.getContent());
                            } else if (!TextUtils.isEmpty(CoreUtility.currentUserUid)) {
                                GlobalData.userInfo = new ContactProfile(CoreUtility.currentUserUid);
                                GlobalData.userInfo.phone = GlobalData.phoneNumber;
                                SharedPreferencesData.setUserInfo(MainApplication.getAppContext(), GlobalData.userInfo.getContent());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            if (!d.isNull("update_status")) {
                                SharedPreferencesData.setUpdateProfileStatus(MainApplication.getAppContext(), d.getJSONObject("update_status").toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (d.has("default_avatar")) {
                            GlobalData.sDefaultAvtUrlFriend = d.getString("default_avatar");
                        }

                        SharedPreferencesData.setLastPhoneNumber(MainApplication.getAppContext(), GlobalData.tmp_phoneNumber);
                        SharedPreferencesData.saveInfoSettings(MainApplication.getAppContext());
                        PhoneContactWorker.scanPhoneContacts();
                        HandleLogin.getInstance().parseEncryptInfor(d);
                        HandleLogin.getInstance().parseSMSPrivacy(d);
                        HandleLogin.getInstance().parseServerSetting(d);

                        //parse backup info
                        boolean hasBackupInfo = false;
                        if (SharedPreferencesData.isServerConfigEnableBackupFeature(MainApplication.getAppContext())) {
                            JSONObject backupInfoJson = d.optJSONObject("msg_backup_info");
                            if (backupInfoJson!=null && backupInfoJson.optInt("found", 0)==1) {
                                hasBackupInfo = true;
                                JSONObject backupInfoJsonData = backupInfoJson.optJSONObject("data");
                                BackupRestoreChatDB.getInstance().parseBackupInfoFromLoginFlow(backupInfoJsonData);
                            }
                            /*if (!hasBackupInfo) {
                                //check local
                                BackupInfo info = BackupRestoreChatDB.getInstance().getBackupInfoLocal();
                                hasBackupInfo = info!=null && info.isValid();
                            }*/
                        }

                        if (is_new) {
                            SharedPreferencesData.setAlreadyUpdateAvt(MainApplication.getAppContext(), false);
                            Intent localIntent = new Intent(LoginUsingPWActivity.this, com.zing.zalo.ui.UpdateUserInfor.class);
                            localIntent.putExtra(UpdateUserInfor.BOL_EXTRA_FROM_VERIFYCODE, true);
                            localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(localIntent);

                            setResult(RESULT_OK);
                            finish();
                        } else {
                            //init value global
                            if (d.has("resetPass") && d.getInt("resetPass") == 1) {
                                SharedPreferencesData.setForceUpdatePassword(MainApplication.getAppContext(), true);
                            }

                            if (!TextUtils.isEmpty(CoreUtility.currentUserUid))
                                GlobalData.doInitNotifyNumberList();

                            //revert commit for bug Fix bug màn hình khi login pword mà trước đó bị đá ra tại màn hình force pword
                            //SharedPreferencesData.setForceUpdatePassword(MainApplication.getAppContext(), false);

                            if (hasBackupInfo && SharedPreferencesData.isServerConfigEnableBackupFeature(MainApplication.getAppContext()) &&
                                    DatabaseChatProxy.getDatabaseChatImpl().needToShowRestoreUI(CoreUtility.currentUserUid)) {
                                SocketConnection.getInstance().lockSocketConnect(SocketConnection.MAX_TIME_LOCK_SOCKET_CONNECT);
                            }

                            Intent localIntent = new Intent(LoginUsingPWActivity.this, com.zing.zalo.ui.MainTabActivity.class);
                            localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            localIntent.putExtra(MainFragment.EXTRA_FROM_ACTIVE_LOGIN_FLOW, true);
                            startActivity(localIntent);

                            setResult(RESULT_OK);
                            finish();

                        }

                        if (!d.isNull("location_interval"))//in minutes
                        {
                            int timeIntervalSubmitLocation = d.getInt("location_interval") * 60 * 1000;//to convert miliseconds
                            SharedPreferencesData.setTimeIntervalSubmitLocationFromSerVer(MainApplication.getAppContext(), timeIntervalSubmitLocation);
                        }

                        GlobalData.isSetPassWord = 1;
                        SharedPreferencesData.setIsSetPassword(MainApplication.getAppContext(), GlobalData.isSetPassWord);

                        Map<String, String> eventParam = new HashMap<String, String>(1);
                        eventParam.put("appUID", SharedPreferencesData.getCurrentUserUid(MainApplication.getAppContext()));
                        if(GlobalData.USE_ZMOBILE_TRACKING)
                        {
                            ZMobileTracking.trackEvent(TrackingId.RE_ACTIVE_PWD.getIdString(), eventParam);
                        }

                        Utils.initGlobalList();

                        // sync language from startUp flow to in APP
                        String strLanguage = SharedPreferencesData.getLanguageSettingWithoutUserId(MainApplication.getAppContext());
                        SharedPreferencesData.setCurrentLanguageSetting(MainApplication.getAppContext(), strLanguage, true); //sync value from DB to preference
                        SharedPreferencesData.setCurrentLanguageSetting(MainApplication.getAppContext(), strLanguage, false); //sync value from DB to preference

                        Utils.changeToLanguage(null, SharedPreferencesData.getLanguageSetting(MainApplication.getAppContext()), false);
                        Utils.changeGlobalStringWhenLanguageChange(MainApplication.getAppContext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                isdoingActivePassWord = false;
                dismissLoadingDialog();
				
				/*if(!TextUtils.isEmpty(CoreUtility.currentUserUid) && SharedPreferencesData.getNeedUpdateCateMsg(MainApplication.getAppContext(), CoreUtility.currentUserUid))
					Utils.updateCateNewMessageTable(GlobalData.CATE_ALL_MSG);*/
            }
        };

        isSetPasswordListener = new BusinessListener() {
            @Override
            public void onErrorData(final ErrorMessage error_message) {
                try {
                    if (isActive() && error_message.getError_code() != ERROR.NETWORK_ERROR)
                        ServiceMap.syncServiceMap(true);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tvError != null) {
                                tvError.setVisibility(View.VISIBLE);
                                tvError.setText(error_message.getError_message());
                            }

                            if (error_message.getError_code() == ERROR.INVALID_PHONE_NUMBER) {
                                etPhoneNumber.requestFocus();
                            } else if (error_message.getError_code() == ERROR.PASSWORD_DONT_MATCH_USERNAME) {
                                //ThemeUtils.setBackgroundResource(LoginUsingPWActivity.this, etPassword, R.drawable.stencil_edit_text_error);
                                etPassword.requestFocus();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                isRequestingIsSetPassword = false;
                dismissLoadingDialog();
            }

            @Override
            public void onDataProcessed(Object entity) {
//				"is_set":1,
//				"is_activated":1,
//				"voice":1,
//				"is_valid_key":0,

//				"Timeout":15,
//				"DelayedTime":15
//				"RejectedCallQuota":1,
//				"phone_froms":["00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868","00868"],

                try {
                    JSONObject json = (JSONObject) entity;
                    isSet = json.has("is_set") ? json.getInt("is_set") == 1 : false;
                    isActivated = json.has("is_activated") ? json.getInt("is_activated") == 1 : false;
                    isSupportVoiceCall = json.has("voice") ? json.getInt("voice") == 1 : false;
                    isValidKey = json.has("is_valid_key") ? json.getInt("is_valid_key") == 1 : false;


//					iTimeout = !json.isNull("Timeout") ? json.getInt("Timeout") : 0;
                    GlobalData.iTimeOutRequestActiveByRejectCall = !json.isNull("Timeout") ? json.getInt("Timeout") : 0;
                    GlobalData.iTimeDelayRequestActiveByRejectCall = !json.isNull("DelayedTime") ? json.getInt("DelayedTime") : 0;
                    iRejectedCallQuota = 0;//json.has("RejectedCallQuota") ? json.getInt("RejectedCallQuota") : 0;

                    //For fill to dialog confirm request call to active
                    GlobalData.zalo_phoneNumber = !json.isNull("phone_from") ? json.getString("phone_from") : "";
                    GlobalData.sms_gateWay = !json.isNull("sms_gateway") ? json.getString("sms_gateway") : "";
                    GlobalData.hintSendSmsToGetActiveCode = json.optString("sms_content_hint", "");//!json.isNull("sms_content_hint") ? json.getString("sms_content_hint") : "";
                    JSONArray callNumPatterns = new JSONArray();

                    if (!json.isNull("phone_froms")) {
                        callNumPatterns = new JSONArray(json.getString("phone_froms"));
                    }

                    GlobalData.callNumberPatternList.clear();

                    for (int m = 0; m < callNumPatterns.length(); m++) {
                        try {
                            String callNum = String.valueOf(callNumPatterns.get(m));

                            if (!TextUtils.isEmpty(callNum)) {
                                GlobalData.callNumberPatternList.add(callNum);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (GlobalData.tmp_phoneNumber != null && GlobalData.sms_left != null && GlobalData.voice_left != null) {
                        sms_left = GlobalData.sms_left.containsKey(GlobalData.tmp_phoneNumber) ? GlobalData.sms_left.get(GlobalData.tmp_phoneNumber) : 0;
                        voice_left = GlobalData.voice_left.containsKey(GlobalData.tmp_phoneNumber) ? GlobalData.voice_left.get(GlobalData.tmp_phoneNumber) : 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (isActivated) {
                        runOnUiThread(new Runnable() {
                            @SuppressWarnings("deprecation")
                            @Override
                            public void run() {
                                try {
                                    if (iRejectedCallQuota > 0) //new flow for request get active code by reject call
                                    {
//										startVerifyActivationCode(2);
                                        removeDialog(ASK_TO_GETACTIVECODEBYREJECTCALL);
                                        if (LoginUsingPWActivity.this != null && !LoginUsingPWActivity.this.isFinishing())
                                            showDialog(ASK_TO_GETACTIVECODEBYREJECTCALL);
                                    } else if (isSupportVoiceCall && (sms_left < 2 && voice_left > 0)) // StartUp Flow: 1 sms -> n call -> 1 sms
                                    {
                                        removeDialog(ASK_TO_GET_ACTIVECODE_BY_CALL);
                                        if (LoginUsingPWActivity.this != null && !LoginUsingPWActivity.this.isFinishing())
                                            showDialog(ASK_TO_GET_ACTIVECODE_BY_CALL);
                                    } else {
                                        removeDialog(ASK_TO_GET_ACTIVECODE_BY_SMS);
                                        if (LoginUsingPWActivity.this != null && !LoginUsingPWActivity.this.isFinishing())
                                            showDialog(ASK_TO_GET_ACTIVECODE_BY_SMS);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (tvError != null) {
                                        tvError.setVisibility(View.VISIBLE);
                                        tvError.setText(getString(R.string.str_text_phone_numner_isnotvalid));
                                    }

                                    if (etPhoneNumber != null) {
                                        //ThemeUtils.setBackgroundResource(LoginUsingPWActivity.this, etPhoneNumber, R.drawable.stencil_edit_text_error);
                                        etPhoneNumber.requestFocus();
                                    }
                                    showAppDialog(DIALOG_VERIFY_PHONE_NUMBER);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                isRequestingIsSetPassword = false;
                dismissLoadingDialog();
            }
        };

        bussinessActivePassword = new ZaloBusinessImpl();
        bussinessActivePassword.setBusinessListener(activePasswordListener);

        if (ActivityCompatUtil.checkSelfPermission(this, ActivityCompatUtil.PHONE_SMS_PERMISSION)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompatUtil.requestPermissions(this, ActivityCompatUtil.PHONE_SMS_PERMISSION, ActivityCompatUtil.REQUEST_CODE_PHONE_SMS_PERMISSION);
        }

        // InitData
        String lastPhoneNum = SharedPreferencesData.getLastPhoneNumber(MainApplication.getAppContext());
        if (!TextUtils.isEmpty(GlobalData.tmp_phoneNumber)) {
            etPhoneNumber.setText(GlobalData.tmp_phoneNumber);
        } else if (!TextUtils.isEmpty(lastPhoneNum)) {
            etPhoneNumber.setText(lastPhoneNum);
            SharedPreferencesData.setLastPhoneNumber(MainApplication.getAppContext(), "");
            if (GlobalData.tmp_phoneNumber != null)
                GlobalData.tmp_phoneNumber = "";
        } else {
            etPhoneNumber.setText("");

            try {
                TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                String sMSISDN = tMgr.getLine1Number();

                if (!TextUtils.isEmpty(sMSISDN)) {
                    etPhoneNumber.setText(sMSISDN);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                boolean isAlreadyActivated = extras.getBoolean("isAlreadyActivated");
                isFromStartUp = extras.getBoolean("isFromStartUp");
                if (isAlreadyActivated)
                    tvError.setVisibility(View.VISIBLE);
            }
        }

        if(etPhoneNumber != null && !TextUtils.isEmpty(etPhoneNumber.getText()))
        {
            String strPhoneNumber = etPhoneNumber.getText().toString().trim();
            if (!TextUtils.isEmpty(strPhoneNumber))
                etPassword.requestFocus();
            else
                etPhoneNumber.requestFocus();
        }

        TaskExecutor.queueRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    Utils.convertPhoneNumberToE164("113", false);
                } catch (Exception e) {
                    Log.e(TAG, e);
                }
            }
        });

        /*String country = CountryCodeParser.getInstance().getSimCountryIso();



        if (country != null) {
            String countryCode = CountryCodeParser.getInstance().getICCSMap().get(country).getNames();
            String countryName = CountryCodeParser.getInstance().getICCSMap().get(country).country;

            String isoCountryCode = SharedPreferencesData.getIsoCountryCode(MainApplication.getAppContext());
            if(isoCountryCode.equals(country))

            tvCountryName.setText(countryName + " (" + countryCode + ")");

            *//*String countryName = languageMap.get(country);
            if (countryName != null) {
                int index = countriesArray.indexOf(countryName);
                if (index != -1) {
                    codeField.setText(countriesMap.get(countryName));
                    countryState = 0;
                }
            }*//*
        }*/

        long startTime = System.currentTimeMillis();
        String countryISOCode = "";
        String countryNetworkISOCode = "";

        try {
            TelephonyManager teleMgr = (TelephonyManager) MainApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (teleMgr != null) {
                countryISOCode = teleMgr.getSimCountryIso();
                countryISOCode = countryISOCode.toUpperCase();

                if (teleMgr.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA)
                { // device is not 3G (would be unreliable)
                    String networkCountry = teleMgr.getNetworkCountryIso();
                    if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                        countryNetworkISOCode = networkCountry.toLowerCase(Locale.US);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.w(TAG, "ABC getSimCountryIso: " + countryISOCode + " - countryNetworkISOCode: " + countryNetworkISOCode + " - time: " + (System.currentTimeMillis() - startTime));


        // ###################################
        String localeCountry = Locale.getDefault().getCountry();//US

//      if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ActivityCompatUtil.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
            startTime = System.currentTimeMillis();
            final long fstartTime = startTime;
            ThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Location location = LocationUtils.getLatestLocation(MainApplication.getAppContext());//getBestLastGeolocation(MainApplication.getAppContext());

                        if (location != null) {
                            Log.w(TAG, "ABC lastKnow");
                            getCountryCode(location, fstartTime);
                        } else
                        {
                            RequestLocation myLocation = new RequestLocation();
                            myLocation.getLocation(MainApplication.getAppContext(), new RequestLocationBase.LocationResult() {
                                @Override
                                public void gotLocation(Location location) {
                                    try {
                                        if (location == null) {
                                            return;
                                        }

                                        if (location != null) {
                                            getCountryCode(location, fstartTime);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//          String sMSISDN = tMgr.getLine1Number();
            String sMSISDN = "+84988394787";//tMgr.getLine1Number();


            if (!TextUtils.isEmpty(sMSISDN) && TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
                etPhoneNumber.setText(sMSISDN);
            }

            if(!TextUtils.isEmpty(sMSISDN))
            {
                startTime = System.currentTimeMillis();
                boolean isValid;
                Phonenumber.PhoneNumber number;
                PhoneNumberUtil util = PhoneNumberUtil.getInstance();

                for (String r : util.getSupportedRegions())
                {
                    try {
                        // check if it's a possible number
                        isValid = util.isPossibleNumber(sMSISDN, r);
                        if (isValid)
                        {
                            number = util.parse(sMSISDN, r);

                            // check if it's a valid number for the given region
                            isValid = util.isValidNumberForRegion(number, r);
                            if (isValid)
                            {
                                //                          System.out.println(r + ": " + number.getCountryCode() + ", " + number.getNationalNumber());
                                Log.w(TAG, "ABC PhoneNumberUtil: " + r + ": " + number.getCountryCode() + ", " + number.getNationalNumber() + " - time: " + (System.currentTimeMillis() - startTime));
                                break;
                            }
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }


        // #######################################
//      getCountry();

//      getInfoNetwork();

        getCountryCodeByIp();
        getCountryInfo();

        ActionLog.startLog(ActionConstant.FORM_PASSWORD);
        ActionLog.endLog();
    }

    public static Location getBestLastGeolocation(Context context) {
        Location bestLocation = null;

        try {
            LocationManager manager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = manager.getAllProviders();


            for (String it : providers) {
                Location location = manager.getLastKnownLocation(it);
                if (location != null) {
                    if (bestLocation == null
                            || location.getAccuracy() < bestLocation.getAccuracy()) {
                        bestLocation = location;
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bestLocation;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*try {
            if (ActivityCompatUtil.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)==PackageManager.PERMISSION_GRANTED) {
                TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//              String sMSISDN = tMgr.getLine1Number();
                String sMSISDN = "+84988394787";//tMgr.getLine1Number();


                if (!TextUtils.isEmpty(sMSISDN) && TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
                    etPhoneNumber.setText(sMSISDN);
                }

                if(!TextUtils.isEmpty(sMSISDN))
                {
                    long startTime = System.currentTimeMillis();
                    boolean isValid;
                    Phonenumber.PhoneNumber number;
                    PhoneNumberUtil util = PhoneNumberUtil.getInstance();

                    for (String r : util.getSupportedRegions())
                    {
                        try {
                            // check if it's a possible number
                            isValid = util.isPossibleNumber(sMSISDN, r);
                            if (isValid)
                            {
                                number = util.parse(sMSISDN, r);

                                // check if it's a valid number for the given region
                                isValid = util.isValidNumberForRegion(number, r);
                                if (isValid)
                                {
                                    //                          System.out.println(r + ": " + number.getCountryCode() + ", " + number.getNationalNumber());
                                    Log.w(TAG, "ABC PhoneNumberUtil: " + r + ": " + number.getCountryCode() + ", " + number.getNationalNumber() + " - time: " + (System.currentTimeMillis() - startTime));
                                    break;
                                }
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if(ActivityCompatUtil.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                long startTime = System.currentTimeMillis();
                final long fstartTime = startTime;
                ThreadUtils.postOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Location location = getBestLastGeolocation(MainApplication.getAppContext());

                            if (location != null) {
                                Log.w(TAG, "ABC lastKnow");
                                getCountryCode(location, fstartTime);
                            } else {
                                RequestLocation myLocation = new RequestLocation();
                                myLocation.getLocation(MainApplication.getAppContext(), new RequestLocationBase.LocationResult() {
                                    @Override
                                    public void gotLocation(Location location) {
                                        try {
                                            if (location == null) {
                                                return;
                                            }

                                            if (location != null) {
                                                getCountryCode(location, fstartTime);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private boolean stringIsNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected void onDestroy() {
        dismissLoadingDialog();
        super.onDestroy();
    }

    @Override
    public void finish() {
        dismissLoadingDialog();
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SECURITY_QUESTION) {
            dismissLoadingDialog();
            if (resultCode == RESULT_OK) {
                mSecurityQuestion = (SecurityQuestion) data.getSerializableExtra(SecurityQuestionActivity.EXTRA_QUESTION);
                sPhoneNumber = etPhoneNumber.getText().toString().trim();
                mSavePassword = data.getStringExtra(SecurityQuestionActivity.EXTRA_PASSWORD);
                //etPassword.setText(sPassword);

                activePassword(SharedPreferencesData.getIsoCountryCode(MainApplication.getAppContext()),
                        sPhoneNumber,
                        mSavePassword,
                        "1",
                        ConfigAQueryUtils.getAvatarSize());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            String isoCountryCode = SharedPreferencesData.getIsoCountryCode(MainApplication.getAppContext());
            String countryCode = CountryCodeParser.getInstance().getICCSMap().get(isoCountryCode).getNames();
            String countryName = CountryCodeParser.getInstance().getICCSMap().get(isoCountryCode).country;
            tvCountryName.setText(countryName + " (" + countryCode + ")");
            NotiManager.getInstance().clearMessageNoti();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (super.onKeyUp(keyCode, event)) return true;

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPress();
            return true;
        }

        return false;
    }

    @Override
    protected boolean isHandleBackKeyOnSuper() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (super.onOptionsItemSelected(item)) return true;

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPress();
                return true;
        }
        return false;
    }

    private void onBackPress() {
        if (isFromStartUp) {
            Intent intent = new Intent(this, StartUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, VerifyPhoneNumberActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        finish();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        try {
            if (savedInstanceState != null) {
                savedInstanceState.putString("onSave", GlobalData.tmp_phoneNumber);
                savedInstanceState.putString("zalo_phoneNumber", GlobalData.zalo_phoneNumber);
                savedInstanceState.putString("sms_gateWay", GlobalData.sms_gateWay);
                savedInstanceState.putString("sms_content_hint", GlobalData.hintSendSmsToGetActiveCode);
                savedInstanceState.putBoolean("isFromStartUp", isFromStartUp);
                savedInstanceState.putString(LAST_SUBMIT_PHONE, lastPhoneNumber);
                savedInstanceState.putInt(WRONG_SUBMIT_PHONE_COUNT, submitWrongNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            if (savedInstanceState != null) {
                isFromStartUp = savedInstanceState.containsKey("isFromStartUp") ? savedInstanceState.getBoolean("isFromStartUp") : false;
                GlobalData.tmp_phoneNumber = savedInstanceState.containsKey("onSave") ? savedInstanceState.getString("onSave") : "";
                GlobalData.zalo_phoneNumber = savedInstanceState.containsKey("zalo_phoneNumber") ? savedInstanceState.getString("zalo_phoneNumber") : "";
                GlobalData.sms_gateWay = savedInstanceState.containsKey("sms_gateWay") ? savedInstanceState.getString("sms_gateWay") : "";
                GlobalData.hintSendSmsToGetActiveCode = savedInstanceState.containsKey("sms_content_hint") ? savedInstanceState.getString("sms_content_hint") : "";
                if (savedInstanceState.containsKey(LAST_SUBMIT_PHONE)) {
                    lastPhoneNumber = savedInstanceState.getString(LAST_SUBMIT_PHONE);
                }
                if (savedInstanceState.containsKey(WRONG_SUBMIT_PHONE_COUNT)) {
                    submitWrongNumber = savedInstanceState.getInt(WRONG_SUBMIT_PHONE_COUNT);
                }
                if (etPhoneNumber != null)
                    etPhoneNumber.setText(GlobalData.tmp_phoneNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Dialog onCreateDialog(int dialogId) {
        Dialog dialog = null;
        switch (dialogId) {
            case DIALOG_VERIFY_PHONE_NUMBER: {
                Resources res = getResources();

                ConfirmActivePhoneDialog.ConfirmBuilder customBuilder = new ConfirmActivePhoneDialog.ConfirmBuilder(LoginUsingPWActivity.this);
                customBuilder.setTitle(getString(R.string.str_titleDlg_confirmPhone))
                        .setMessage(getString(R.string.input_phone10))
                        .setPhoneNumber(Utils.covertPhoneNumberToFineString(etPhoneNumber.getText().toString(), SharedPreferencesData.getIsoCountryCode(MainApplication.getAppContext())))
                        .setNegativeButton(getString(R.string.change), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActionLog.startLog(ActionConstant.DIALOG_CONFIRM_PHONE_NUMBER_CANCEL);
                                ActionLog.endLog();
                                if (dialog != null)
                                    dialog.dismiss();
                                etPhoneNumber.requestFocus();
                            }
                        })
                        .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActionLog.startLog(ActionConstant.DIALOG_CONFIRM_PHONE_NUMBER_OK);
                                ActionLog.endLog();
                                if (dialog != null)
                                    dialog.dismiss();
                                showAppDialog(DIALOG_REGISTER_NEW_USER);
                            }
                        });
                dialog = customBuilder.create();
            }
            break;
            case DIALOG_REGISTER_NEW_USER: {
                Resources res = getResources();

                ConfirmActivePhoneDialog.ConfirmBuilder customBuilder = new ConfirmActivePhoneDialog.ConfirmBuilder(LoginUsingPWActivity.this);
                customBuilder.setTitle(getString(R.string.str_title_dialog_register_new_user))
                        .setMessage(getString(R.string.input_phone11))
                        .setPhoneNumber(Utils.covertPhoneNumberToFineString(etPhoneNumber.getText().toString(), SharedPreferencesData.getIsoCountryCode(MainApplication.getAppContext())))
                        .setNegativeButton(getString(R.string.str_cap_skip), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActionLog.startLog(ActionConstant.DIALOG_CONFIRM_CREATE_NEW_ACC_CANCEL);
                                ActionLog.endLog();
                                if (dialog != null)
                                    dialog.dismiss();

//								Intent intent = new Intent(LoginUsingPWActivity.this, StartUpActivity.class);
//								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//								startActivity(intent);
//								finish();
                            }
                        })
                        .setPositiveButton(getString(R.string.str_cap_register_new_user), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActionLog.startLog(ActionConstant.DIALOG_CONFIRM_CREATE_NEW_ACC_OK);
                                ActionLog.endLog();
                                if (dialog != null)
                                    dialog.dismiss();
                                Intent intent = new Intent(LoginUsingPWActivity.this, VerifyPhoneNumberActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                dialog = customBuilder.create();
            }
            break;
            case ASK_TO_GET_ACTIVECODE_BY_SMS: {
                Resources res = getResources();

                ConfirmActivePhoneDialog.ConfirmBuilder customBuilder = new ConfirmActivePhoneDialog.ConfirmBuilder(LoginUsingPWActivity.this);
                customBuilder.setTitle(getString(R.string.str_titleDlg_confirmPhone))
                        .setMessage(getString(R.string.input_phone05))
                        .setPhoneNumber(Utils.covertPhoneNumberToFineString(etPhoneNumber.getText().toString(), SharedPreferencesData.getIsoCountryCode(MainApplication.getAppContext())))
                        .setIdIconReceiveActiveCode(R.drawable.im_sms_confirm)
                        .setNegativeButton(getString(R.string.change), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActionLog.startLog(ActionConstant.FORM_PASSWORD_PHONE_VERIFY_CANCEL, "");
                                if (dialog != null)
                                    dialog.dismiss();
                                ActionLog.endLog();
                                etPhoneNumber.requestFocus();
                            }
                        })
                        .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActionLog.startLog(ActionConstant.FORM_PASSWORD_PHONE_VERIFY_OK, "");
                                if (dialog != null)
                                    dialog.dismiss();
                                startVerifyActivationCode(0);
                                ActionLog.endLog();
                            }
                        });
                dialog = customBuilder.create();
            }
            break;

            case ASK_TO_GET_ACTIVECODE_BY_CALL: {
                Resources res = getResources();

//			    String msg = String.format(getString(R.string.str_calltoactive_confirm), !TextUtils.isEmpty(GlobalData.zalo_phoneNumber) ? GlobalData.zalo_phoneNumber : getString(R.string.str_replaceString_zaloPhone_null));
                ConfirmActivePhoneDialog.ConfirmBuilder customBuilder = new ConfirmActivePhoneDialog.ConfirmBuilder(LoginUsingPWActivity.this);
                customBuilder.setTitle(getString(R.string.str_titleDlg_confirmPhone))
                        .setMessage(getString(R.string.str_calltoactive_confirm))
                        .setPhoneNumber(Utils.covertPhoneNumberToFineString(etPhoneNumber.getText().toString(), SharedPreferencesData.getIsoCountryCode(MainApplication.getAppContext())))
                        .setIdIconReceiveActiveCode(R.drawable.ic_callconfirm)
                        .setNegativeButton(getString(R.string.change), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null)
                                    dialog.dismiss();
                                etPhoneNumber.requestFocus();
                            }
                        })
                        .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null)
                                    dialog.dismiss();
                                startVerifyActivationCode(1);
                            }
                        });
                dialog = customBuilder.create();
            }
            break;

            case ASK_TO_GETACTIVECODEBYREJECTCALL: {
                Resources res = getResources();

                ConfirmActivePhoneDialog.ConfirmBuilder customBuilder = new ConfirmActivePhoneDialog.ConfirmBuilder(LoginUsingPWActivity.this);
                customBuilder.setTitle(getString(R.string.str_titleDlg_confirmPhone))
                        .setMessage(getString(R.string.str_rejectcalltoactive_confirm))
                        .setPhoneNumber(Utils.covertPhoneNumberToFineString(etPhoneNumber.getText().toString(), SharedPreferencesData.getIsoCountryCode(MainApplication.getAppContext())))
                        .setIdIconReceiveActiveCode(R.drawable.ic_callconfirm)
                        .setNegativeButton(getString(R.string.change), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null)
                                    dialog.dismiss();
                                etPhoneNumber.requestFocus();
                            }
                        })
                        .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null)
                                    dialog.dismiss();
                                startVerifyActivationCode(2);
                            }
                        });
                dialog = customBuilder.create();
            }
            break;

            case WARNING_MSG_UP_TO_DATE_APP: {
                Resources res = getResources();

                MaterialZaloDialog.Builder customBuilder = new MaterialZaloDialog.Builder(LoginUsingPWActivity.this);
                customBuilder.setTitle(getString(R.string.str_titleDlgUpdate))
                        .setMessage(errorMsg_VERSION_END_SUPPORT_ERROR)
                        .setPositiveButton(getString(R.string.str_close_app), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                if (dialog != null)
                                    dialog.dismiss();

                                Utils.exit(LoginUsingPWActivity.this, false);
                            }
                        })
                        .setNegativeButton(getString(R.string.str_titleDlgUpdate), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null)
                                    dialog.dismiss();

                                openMarketForUpdateNew();
                                Utils.exit(LoginUsingPWActivity.this, false);
                            }
                        });

                dialog = customBuilder.create();
                dialog.setCancelable(false);
            }
            break;

            case WARNING_RE_ACTIVATION_BY_SECURITY_QUESTION: {
                Resources res = getResources();

                MaterialZaloDialog.Builder customBuilder = new MaterialZaloDialog.Builder(LoginUsingPWActivity.this);
                customBuilder.setDialogType(MaterialZaloDialog.Builder.DIALOG_BUTTON_TYPE_6)
                        .setMessage(R.string.security_question_activity_re_activation)
                        .setPositiveButton(getString(R.string.str_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null)
                                    dialog.dismiss();

                                if (etPhoneNumber != null)
                                    sPhoneNumber = etPhoneNumber.getText().toString().trim();

                                if (checkInputData(true, false)) {
                                    GlobalData.tmp_phoneNumber = sPhoneNumber;
                                    isSetPassword(GlobalData.tmp_phoneNumber);
                                }
                            }
                        });

                dialog = customBuilder.create();
                dialog.setCancelable(false);
            }
            break;
        }
        return dialog;
    }

    private void openMarketForUpdateNew() {
        try {
            Intent intent = WebViewActivity.createIntent(getApplicationContext(), String.format(getString(R.string.str_ratezalo_url), LoginUsingPWActivity.this.getPackageName()));
            if (intent != null) {
                startActivity(intent);
            }
        } catch (Exception e) {

        }
    }

    private void showLoadingDialog() {
        try {
            loadingDialog = ProgressDialogCustom.createAndShow(LoginUsingPWActivity.this, getString(R.string.str_isProcessing), true, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    loadingDialog = null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissLoadingDialog() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing() && !LoginUsingPWActivity.this.isFinishing())
                loadingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void activePassword(String iso_country_code, String phone_num, String password, String inc_p, int avatarSize) {
        if (NetworkUtils.isNetworkAvailable(true)) {
            if (isdoingActivePassWord)
                return;

            String phoneNumE164 = Utils.convertPhoneNumberToE164(phone_num, false);

            if(TextUtils.isEmpty(phoneNumE164) || phoneNumE164.equals("invalid"))
            {
                Utils.showMess(Utils.getErrorMsgFromCode(ERROR.INVALID_PHONE_NUMBER, ERROR.NETWORK_ERROR_MSG));
                return;
            }

            showLoadingDialog();
            String key = Md5Generator.md5(CoreUtility.AES_KEY + phoneNumE164);
            passwordAfterEncoded = PasswordUtils.getPassword(key, password);

            String cs = "";
            String localeCode639_1 = "";
            String str_timeZone = "";
            long localTime = System.currentTimeMillis();
            String jsonDeviceInfos = "";

            try {
                if (BuildConfig.DEBUG) {
                    cs = "7ad1cddec8101b35cc2fa47c46b8c84f";
                } else {
                    PackageManager pm = MainApplication.getAppContext().getPackageManager();
                    Signature[] mySigs = pm.getPackageInfo(MainApplication.getAppContext().getPackageName(), PackageManager.GET_SIGNATURES).signatures;

                    if (mySigs != null && mySigs.length > 0) {
                        cs = Md5Generator.md5(mySigs[0].toCharsString());
                    }
                }

                localeCode639_1 = Locale.getDefault() != null ? Locale.getDefault().toString() : ""; //SharedPreferencesData.getLanguageSetting(MainApplication.getAppContext());//

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());
                Date currentLocalTime = calendar.getTime();
                DateFormat date = new SimpleDateFormat("Z");
                String timeZone = date.format(currentLocalTime);

//		        TimeZone tz = TimeZone.getDefault();
//		        Date now = new Date();
//		        int offsetFromUtc = tz.getOffset(now.getTime()) / 1000;//libcore.util.ZoneInfo[id="Asia/Saigon",mRawOffset=25200000,mEarliestRawOffset=25580000,mUseDst=false,mDstSavings=380000,transitions=4]
//
//		        int minutes = offsetFromUtc / 60;
//		        int hours = minutes / 60;
//		        int expandHour = minutes -  (hours * 60);
//		        int offsetHrs = offset / 1000 / 60 / 60;
//		        int offsetMins = offset / 1000 / 60 % 60;

                if (timeZone.length() == 5) {
                    str_timeZone = timeZone.substring(0, 3) + ":" + timeZone.substring(3, 5);
                } else
                    str_timeZone = timeZone;

                jsonDeviceInfos = Utils.createDeviceInfosJsonFormat();
            } catch (Exception e) {
                e.printStackTrace();

            }

            String user_language = SharedPreferencesData.getLanguageSettingWithoutUserId(MainApplication.getAppContext());
            isdoingActivePassWord = true;
            bussinessActivePassword.setBusinessListener(activePasswordListener);
            if (mSecurityQuestion == null) {
                bussinessActivePassword.activePasswordWithQuestion(phone_num, passwordAfterEncoded, iso_country_code, inc_p, avatarSize, cs, str_timeZone, localeCode639_1, localTime, user_language, jsonDeviceInfos);
            } else {
                bussinessActivePassword.activePasswordWithQuestion(phone_num, passwordAfterEncoded, iso_country_code, inc_p, avatarSize, cs, str_timeZone, localeCode639_1, localTime, user_language, jsonDeviceInfos
                        , String.valueOf(mSecurityQuestion.getQuestionType()), String.valueOf(mSecurityQuestion.getSelectionRequiredNumber()), mSecurityQuestion.getAnswerSelectedValues());
                mSecurityQuestion = null;
            }
        }
    }

    private void isSetPassword(String phone_num) {
        if (!NetworkUtils.isNetworkAvailable(true))
            return;

        if (isRequestingIsSetPassword)
            return;

        isRequestingIsSetPassword = true;
        showLoadingDialog();

        String cs = "";
        String localeCode639_1 = "";
        String str_timeZone = "";
        long localTime = System.currentTimeMillis();
        String jsonDeviceInfos = "";

        try {
            if (BuildConfig.DEBUG) {
                cs = "7ad1cddec8101b35cc2fa47c46b8c84f";
            } else {
                PackageManager pm = MainApplication.getAppContext().getPackageManager();
                Signature[] mySigs = pm.getPackageInfo(MainApplication.getAppContext().getPackageName(), PackageManager.GET_SIGNATURES).signatures;

                if (mySigs != null && mySigs.length > 0) {
                    cs = Md5Generator.md5(mySigs[0].toCharsString());
                }
            }

            localeCode639_1 = Locale.getDefault().getLanguage(); //SharedPreferencesData.getLanguageSetting(MainApplication.getAppContext());//

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());
            Date currentLocalTime = calendar.getTime();
            DateFormat date = new SimpleDateFormat("Z");
            String timeZone = date.format(currentLocalTime);

            if (timeZone.length() == 5) {
                str_timeZone = timeZone.substring(0, 3) + ":" + timeZone.substring(3, 5);
            } else
                str_timeZone = timeZone;

            jsonDeviceInfos = Utils.createDeviceInfosJsonFormat();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String user_language = SharedPreferencesData.getLanguageSettingWithoutUserId(MainApplication.getAppContext());
        ZaloBusinessImpl bussinessIsSetPassword = new ZaloBusinessImpl();
        bussinessIsSetPassword.setBusinessListener(isSetPasswordListener);
        bussinessIsSetPassword.isSetPassword(phone_num, cs, str_timeZone, localeCode639_1, localTime, false, user_language, jsonDeviceInfos);
    }

    private void startVerifyActivationCode(int typeToGetActiveCode)//0: SMS - 1: CALL - 2: REJECT CALL
    {
        Bundle extras = new Bundle();

        extras.putBoolean(VerifyActivationCodeActivity.BOL_EXTRA_IS_ALREADY_ACTIVATED, isActivated);
        extras.putBoolean(VerifyActivationCodeActivity.BOL_EXTRA_SUPPORT_CALL_TO_ACTIVE, isSupportVoiceCall);
        extras.putBoolean(VerifyActivationCodeActivity.BOL_EXTRA_IS_VALID_KEY, isValidKey);
        extras.putBoolean(VerifyActivationCodeActivity.BOL_EXTRA_IS_FORGET_PASSWORD, true);
        extras.putBoolean("isSet", isSet);
        extras.putBoolean("isFromStartUp", isFromStartUp);

        if (typeToGetActiveCode == 1)
            extras.putBoolean(VerifyActivationCodeActivity.BOL_EXTRA_IS_CALL_TO_ACTIVE, true);
        else if (typeToGetActiveCode == 2) {
            extras.putInt(VerifyActivationCodeActivity.INT_EXTRA_REJECTEDCALLQUOTA, iRejectedCallQuota);// new flow with request get active code by
//			extras.putInt(VerifyActivationCodeActivity.INT_EXTRA_TIMEOUT_WATINGCALL, iTimeout);// new flow with request get active code by 
//			extras.putInt(VerifyActivationCodeActivity.INT_EXTRA_DELAYTIME_CALL_REQUEST, iDelayTime);// new flow with request get active code by 
        }

        Intent localIntent = new Intent(LoginUsingPWActivity.this, com.zing.zalo.ui.VerifyActivationCodeActivity.class);
        localIntent.putExtras(extras);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(localIntent);
        finish();
    }

    private boolean checkInputData(boolean checkPhoneNumber, boolean checkPassword) {
        //ThemeUtils.setBackgroundResource(LoginUsingPWActivity.this, etPhoneNumber, R.drawable.stencils_bg_edit_text);
        //ThemeUtils.setBackgroundResource(LoginUsingPWActivity.this, etPassword, R.drawable.stencils_bg_edit_text);

        // Phone number must not be empty
        if (checkPhoneNumber && TextUtils.isEmpty(sPhoneNumber)) {
            //ThemeUtils.setBackgroundResource(LoginUsingPWActivity.this, etPhoneNumber, R.drawable.stencil_edit_text_error);
            etPhoneNumber.requestFocus();
            tvError.setText(getString(R.string.input_phone00));
            tvError.setVisibility(View.VISIBLE);
            return false;
        }

        // Password must not be empty
        if (checkPassword && TextUtils.isEmpty(sPassword)) {
            //ThemeUtils.setBackgroundResource(LoginUsingPWActivity.this, etPassword, R.drawable.stencil_edit_text_error);
            etPassword.requestFocus();
            tvError.setText(getString(R.string.str_text_empty_password));
            tvError.setVisibility(View.VISIBLE);
            return false;
        }

        //tvError.setVisibility(View.GONE);
        return true;
    }


    private void getCountryCode(final Location location, final long startTime) {

        AsyncTask<Void, Void, String> countryCodeTask = new AsyncTask<Void, Void, String>() {
            final float latitude = (float) location.getLatitude();
            final float longitude = (float) location.getLongitude();
            // Helper.log(TAG, "latitude: " +latitude);
            List<Address> addresses = null;
            Geocoder gcd = new Geocoder(MainApplication.getAppContext());
            String code = null;

            @Override
            protected String doInBackground(Void... params) {
                try {
                    addresses = gcd.getFromLocation(latitude, longitude, 1);
                    if(addresses != null && !addresses.isEmpty())
                        code = addresses.get(0).getCountryCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return code;
            }

            @Override
            protected void onPostExecute(String code) {
//                mCountryCodeListener.returnCountryCode(code);
                String codeISO = code;
                Log.w(TAG, "ABC codeISO 1: " + codeISO + " - time: " + (System.currentTimeMillis() - startTime));

            }

        };

        countryCodeTask.execute();
    }

    private void getCountryCodeByIp() {
        AsyncTask<Void, Void, String> getCountryCodeTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
//                  getCountry();
                    getCountry1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String code) {
//                mCountryCodeListener.returnCountryCode(code);
//                String codeISO = code;
//                Log.w(TAG, "ABC codeISO: " + codeISO + " - time: " + (System.currentTimeMillis() - startTime));
            }
        };

        getCountryCodeTask.execute();
    }

    boolean isGettingCountry = false;
    private void getCountryInfo()
    {
        /*final long startTime = System.currentTimeMillis();
        if(isGettingCountry)
            return;

        ZaloBusiness getCountryInfoBussiness = new ZaloBusinessImpl();
        getCountryInfoBussiness.setBusinessListener(new BusinessListener() {
            @Override
            public void onDataProcessed(Object entity) {
                isGettingCountry = false;
                JSONObject data = (JSONObject)entity;

                if(data.isNull("address"))
                {
                    JSONObject addData = data.optJSONObject("address");
                    if(addData!= null)
                    {
                        String country_code = addData.optString("country_code");

                        Log.w(TAG, "ABC country_code: " + country_code + " - time: " + (System.currentTimeMillis() - startTime));
                    }
//
//                        "continent":"Asia",
//                        "continent_code":"AS",
//                        "city":"-",
//                        "country":"Vietnam",
//                        "country_code":"VN",
//                        "region":"-"
                }
            }

            @Override
            public void onErrorData(ErrorMessage error_message) {
                isGettingCountry = false;
            }
        });

        isGettingCountry = true;
        getCountryInfoBussiness.getJSONCountryInfo();*/
    }

    public String getCountry() {

        long startTime = System.currentTimeMillis();
        String country = null;

        try {

            HttpClient httpclient = new DefaultHttpClient();

//          HttpGet httpget = new HttpGet("http://freegeoip.net/json/github.com");
            HttpGet httpget = new HttpGet("http://api.wipmania.com/");

            HttpResponse response;

            response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();

            entity.getContentLength();

            String str = EntityUtils.toString(entity);

            Log.i(TAG, "ABC get from IP: " + str + "- Time: " +(System.currentTimeMillis() - startTime));

            country = str;
            //{"ip":"192.30.252.130","country_code":"US","country_name":"United States","region_code":"CA","region_name":"California","city":"San Francisco","zip_code":"94107","time_zone":"America/Los_Angeles","latitude":37.7697,"longitude":-122.3933,"metro_code":807}
        }

        catch (Exception e){
            Log.e(TAG, e.toString());
        }

        return country;
    }

    public void getCountry1()
    {
        //gen url
//      String url = "http://api.wipmania.com/jsonp";
        String url = "http://api.wipmania.com/json";


        long startTimes = System.currentTimeMillis();

        HttpClient httpClientGet = RequestBase.getHttpClient();

        Log.d(TAG, "URL = " + url);

        synchronized (httpClientGet)
        {
            HttpGet httpGet = new HttpGet(url);

            httpGet.addHeader("Accept-Encoding", "gzip");

            HttpResponse response = null;
            String jsonData = null;

            try {
                httpGet.setHeader("User-Agent", System.getProperties().getProperty("http.agent"));
                response = httpClientGet.execute(httpGet);

                if (response != null) {

                    jsonData = HttpHelper.request(response, true);

                    if(!TextUtils.isEmpty(jsonData))
                    {
//                      jsonData.replace("\\(", "");
//                      jsonData.replace("\\)", "");
                        jsonData = jsonData.replaceAll("[()]", "");
                    }

                    JSONObject json = new JSONObject(jsonData);
//                  Log.d(TAG, "Response JSON of request " + id + " = " + jsonData);

                    // process response data
                    JSONObject addData = json.optJSONObject("address");
                    if(addData!= null)
                    {
                        String country_code = addData.optString("country_code");

                        Log.w(TAG, "ABC country_code: " + country_code + " - time: " + (System.currentTimeMillis() - startTimes));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.i(TAG, "getCountry1 " + " API END");
            }
        }
    }

    public void getInfoNetwork()
    {
        try {
            WifiManager wifiMan = (WifiManager) MainApplication.getAppContext().getSystemService(
                    Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMan.getConnectionInfo();

            List<ScanResult> tempList = wifiMan.getScanResults();

            String macAddr = wifiInfo.getMacAddress();
            String bssid = wifiInfo.getBSSID();
            Log.w(TAG, "ABC");

            boolean validCommand = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    private long startTimeGGPlayService = 0;
    @Override
    public void onConnected(Bundle bundle) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if(mGoogleApiClient != null)
        {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            String code = "";
            if (mLastLocation != null) {

                Address currentAddress = LocationUtils.getAddress(MainApplication.getAppContext(), mLastLocation);

                if(currentAddress != null)
                {
                    code = currentAddress.getCountryCode();
                    Log.w(TAG, "ABC Yes location find: " + code + " - time: " + (System.currentTimeMillis() - startTimeGGPlayService));
                }
            } else {
                Log.w(TAG, "ABC No location find");
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "ABC Connection suspended");
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.w(TAG, "ABC Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimeGGPlayService = System.currentTimeMillis();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//http://stackoverflow.com/questions/14517338/android-check-whether-the-phone-is-dual-sim/17499889#17499889
	
	// The MSISDN (aka the mobile phone number) isn't a SIM data, so you can't retrieve it. The SIM card has an IMSI (International Mobile Subsriber Identity) that is sent to the HLR (Home Location Register) in charge of doing the mapping MSISDN/IMSI.
	// Mobile phone operators could store the MSISDN on the SIM card if they wanted to, but since it is not required in the GSM protocol it isn't.
	
	// Locale.getDefault().getCountry();
	
	// http://whatismyipaddress.com/ip-lookup
	
	//You shouldn't be passing anything in to getCountry(), remove Locale.getDefault()

	//String locale = context.getResources().getConfiguration().locale.getCountry();
	
	// http://ip-api.com/docs/api:json
	
	//http://ip-api.com/xml/?fields=country,countryCode,region,regionName,city,zip,lat,lon,timezone,isp,org,as,query,status,message
	
	//http://ip-api.com/docs/api:json
	
	// http://ip-api.com/json
	
	// http://www.hostip.info/use.html
	
	// http://ipinfodb.com/ip_location_api.php
	
	// http://dev.maxmind.com/geoip/legacy/geolite/
	
	// http://stackoverflow.com/questions/4453328/get-location-of-wifi-ip-in-android
	
	// https://developer.android.com/reference/android/location/LocationManager.html
	

	// USE: https://play.google.com/store/apps/details?id=net.hideman&hl=en to fake location, app will detect wrong language
	//      https://play.google.com/store/apps/details?id=com.hidemyip.hideme&hl=en
	
	//10-11 11:50:07.234 10499-17102/com.zing.zalo W/StartUpView: ABC country_code: RU - time: 2036 Russia
	//10-11 11:51:41.142 10499-18824/com.zing.zalo W/StartUpView: ABC country_code: XX - time: 4527 Canada
}
