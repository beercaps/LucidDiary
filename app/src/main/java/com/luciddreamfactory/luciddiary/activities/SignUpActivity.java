package com.luciddreamfactory.luciddiary.activities;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.luciddreamfactory.luciddiary.R;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends FAuthActivity implements LoaderCallbacks<Cursor>, OnClickListener {


    private static final String TAG = SignUpActivity.class.getSimpleName();
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private ProgressDialog mProgressDialog;

    // UI references.
    private AutoCompleteTextView mAutoCompleteTextViewEmail;
    private EditText mEditTextPassword;
    private Button mButtonRegister;
    private CoordinatorLayout mCoordinatorLayout;
    private TextView mTextViewLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // Set up the login form.
        mAutoCompleteTextViewEmail = (AutoCompleteTextView) findViewById(R.id.edittext_signup_email);
        populateAutoComplete();

        mEditTextPassword = (EditText) findViewById(R.id.edittext_signup_password);
        mEditTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegisteringUser();
                    return true;
                }
                return false;
            }
        });

        mButtonRegister = (Button) findViewById(R.id.button_signup_register);
        mButtonRegister.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.progressbar_registering));

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_signup);

        mTextViewLogin = (TextView) findViewById(R.id.textView_signup_signin);
        mTextViewLogin.setOnClickListener(this);

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mAutoCompleteTextViewEmail, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegisteringUser() {
              // Reset errors.
        mAutoCompleteTextViewEmail.setError(null);
        mEditTextPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mAutoCompleteTextViewEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mEditTextPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEditTextPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mAutoCompleteTextViewEmail.setError(getString(R.string.error_field_required));
            focusView = mAutoCompleteTextViewEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mAutoCompleteTextViewEmail.setError(getString(R.string.error_invalid_email));
            focusView = mAutoCompleteTextViewEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            mProgressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            mProgressDialog.dismiss();


                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Snackbar.make(mCoordinatorLayout, getString(R.string.signup_registering_failed), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
           //register here
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SignUpActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mAutoCompleteTextViewEmail.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view == mButtonRegister) {
            attemptRegisteringUser();
        } else if (view == mTextViewLogin) {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }

    @Override
    protected void onSignedIn() {
        Intent intent = new Intent(this, DreamTimeLine.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

