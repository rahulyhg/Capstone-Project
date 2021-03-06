package com.android.akshitgupta.capstoneproject;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.akshitgupta.capstoneproject.data.UserContract;
import com.android.akshitgupta.capstoneproject.enums.Gender;
import com.android.akshitgupta.capstoneproject.object.GeoDetails;
import com.android.akshitgupta.capstoneproject.object.GeoPlaceDetails;
import com.android.akshitgupta.capstoneproject.object.User;
import com.android.akshitgupta.capstoneproject.task.GeoPlaceDetailsTask;
import com.android.akshitgupta.capstoneproject.view.GooglePlacesAutocompleteAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.android.akshitgupta.capstoneproject.utils.ConstantUtils.PERMISSIONS_REQUEST_WRITE_CONTACTS;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = AddUserActivity.class.getSimpleName();
    String placeId;
    private EditText dobDate;
    private EditText dobTime;
    private EditText name;
    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog toDatePickerDialog;
    private Button saveButton;
    private RadioButton maleOption;
    private RadioButton femaleOption;
    private AutoCompleteTextView autoCompView;
    private GeoDetails geoDetails;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;
    private Integer id;

    public static GeoPlaceDetails getLocationDetails(String input) {
        GeoPlaceDetails geoPlaceDetails = null;
        GeoPlaceDetailsTask task;
        task = new GeoPlaceDetailsTask();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, input);
        else
            task.execute(input);

        try {
            geoPlaceDetails = task.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return geoPlaceDetails;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.US);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("userProfile");
        findViewsById(user);
        setDateTimeField();
        setOnClickListeners();
        startToast();
    }

    private void startToast() {
        Toast.makeText(getApplicationContext(), getString(R.string.add_user_toast), Toast.LENGTH_SHORT).show();
    }

    private void findViewsById(User user) {

        dobDate = (EditText) findViewById(R.id.dob_date);
        dobDate.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        dobDate.requestFocus();

        autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        dobTime = (EditText) findViewById(R.id.dob_time);
        dobTime.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
        name = (EditText) findViewById(R.id.name);
        saveButton = (Button) findViewById(R.id.saveButton);
        maleOption = (RadioButton) findViewById(R.id.male);
        femaleOption = (RadioButton) findViewById(R.id.female);
        if (user != null) {
            this.id = user.getId();
            name.setText(user.getUserName());
            dobDate.setText(user.getDobDate());
            dobTime.setText(user.getDobTIme());

            if (user.getUserGender().equals(Gender.MALE.getCode())) {
                maleOption.setChecked(true);
                femaleOption.setChecked(false);

            } else {
                femaleOption.setChecked(true);
                femaleOption.setChecked(false);

            }
        }
    }

    private void setOnClickListeners() {
        dobDate.setOnClickListener(this);
        dobTime.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        maleOption.setOnClickListener(this);
        femaleOption.setOnClickListener(this);
        name.setOnClickListener(this);

        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        autoCompView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                geoDetails = (GeoDetails) autoCompView.getAdapter().getItem(position);
                placeId = geoDetails.getPlaceId();
                Toast.makeText(context, getString(R.string.you_selected) + geoDetails.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dobDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR, i);
                newDate.set(Calendar.MINUTE, i1);

                dobTime.setText(timeFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean errorAlerts() {
        if (TextUtils.isEmpty(name.getText().toString())) {
            name.setError(getString(R.string.name_empty_error));
            return false;
        }

        if (TextUtils.isEmpty(dobDate.getText().toString())) {
            dobDate.setError(getString(R.string.dob_empty_error));
            return false;
        }

        if (TextUtils.isEmpty(dobTime.getText().toString())) {
            dobTime.setError(getString(R.string.time_empty_error));
            return false;
        }

        if (TextUtils.isEmpty(maleOption.getText().toString()) && TextUtils.isEmpty(femaleOption.getText().toString())) {
            maleOption.setError(getString(R.string.gender_empty_error));
            return false;
        }

        if (TextUtils.isEmpty(autoCompView.getText().toString())) {
            autoCompView.setError(getString(R.string.valid_place_error));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        if (view == dobDate) {
            fromDatePickerDialog.show();
        } else if (view == dobTime) {
            toDatePickerDialog.show();
        } else if (view == saveButton) {

            boolean isAlertFound = errorAlerts();
            if (!isAlertFound) {
                return;
            }

            String nameText = name.getText().toString();
            String dobDateText = dobDate.getText().toString();
            String dobTimeText = dobTime.getText().toString();
            String location = autoCompView.getText().toString();
            boolean isMale = maleOption.isChecked();
            String gender = Gender.MALE.getCode();
            if (!isMale) {
                gender = Gender.FEMALE.getCode();
            }

            if (placeId == null) {
                autoCompView.setError(getString(R.string.valid_place_error));
                return;
            }
            GeoPlaceDetails geoPlaceDetails = getLocationDetails(placeId);


            User user = new User();
            user.setUserName(nameText);
            user.setDobDate(dobDateText);
            user.setDobTIme(dobTimeText);
            user.setUserGender(gender);
            user.setCoordLat(geoPlaceDetails.getLatitude());
            user.setCoordLong(geoPlaceDetails.getLongitude());
            user.setCityName(location);
           // Log.i(LOG_TAG, "Save Button User =" + user);

            addUser(user);
            Toast.makeText(getApplicationContext(), getString(R.string.user_profile_success), Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    public void addUserPermission(User user) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, PERMISSIONS_REQUEST_WRITE_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            addUser(user);
        }
    }

    public void addUser(User user) {
        if (id == null) {
            getApplicationContext().deleteDatabase(UserContract.UserEntry.TABLE_NAME);
            ContentValues userValues = new ContentValues();
            userValues.put(UserContract.UserEntry._ID, user.getId());
            userValues.put(UserContract.UserEntry.COLUMN_USER_NAME, user.getUserName());
            userValues.put(UserContract.UserEntry.COLUMN_USER_GENDER, user.getUserGender());
            userValues.put(UserContract.UserEntry.COLUMN_USER_DOB_DATE, user.getDobDate());
            userValues.put(UserContract.UserEntry.COLUMN_USER_DOB_TIME, user.getDobTIme());
            userValues.put(UserContract.UserEntry.COLUMN_CITY_NAME, user.getCityName());
            userValues.put(UserContract.UserEntry.COLUMN_COORD_LAT, user.getCoordLat());
            userValues.put(UserContract.UserEntry.COLUMN_COORD_LONG, user.getCoordLong());
            // Finally, insert location data into the database.
            Uri insertedUri = getApplicationContext().getContentResolver().insert(
                    UserContract.UserEntry.CONTENT_URI,
                    userValues
            );

            // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
            Long userId = ContentUris.parseId(insertedUri);
            id = userId.intValue();
        } else {
            ContentValues userValues = new ContentValues();
            userValues.put(UserContract.UserEntry._ID, id);
            userValues.put(UserContract.UserEntry.COLUMN_USER_NAME, user.getUserName());
            userValues.put(UserContract.UserEntry.COLUMN_USER_GENDER, user.getUserGender());
            userValues.put(UserContract.UserEntry.COLUMN_USER_DOB_DATE, user.getDobDate());
            userValues.put(UserContract.UserEntry.COLUMN_USER_DOB_TIME, user.getDobTIme());
            userValues.put(UserContract.UserEntry.COLUMN_CITY_NAME, user.getCityName());
            userValues.put(UserContract.UserEntry.COLUMN_COORD_LAT, user.getCoordLat());
            userValues.put(UserContract.UserEntry.COLUMN_COORD_LONG, user.getCoordLong());

            // Uri uri = ContentUris.withAppendedId(Words.CONTENT_URI, id);
            String[] args = {String.valueOf(id)};
            getApplicationContext().getContentResolver().update(UserContract.UserEntry.CONTENT_URI, userValues, UserContract.UserEntry._ID + "=?", args);
           // Log.i(LOG_TAG, "Updated user with id = " + id + "  Id" + id);

        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userDefaultId", id.toString());
        editor.commit();
    }

    //Reference :https://developer.android.com/training/permissions/requesting.html
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_CONTACTS: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(AddUserActivity.this, getString(R.string.contacts_access_denied), Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

}