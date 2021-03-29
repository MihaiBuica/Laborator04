package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {
    private final static int CONTACTS_MANAGER_REQUEST_CODE = 2020;

    public final String HIDETEXT = "Hide Additional Fields";
    public final String SHOWTEXT = "Show Additional Fields";

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText jobTitleEditText;
    private EditText companyEditText;
    private EditText websiteEditText;
    private EditText instantMsgEditText;

    private LinearLayout hideDetailsLinearLayout;

    private Button showAdditionalButton;
    private Button saveButton;
    private Button cancelButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == showAdditionalButton.getId())
            {
                if (hideDetailsLinearLayout.getVisibility() == View.VISIBLE)
                {
                    hideDetailsLinearLayout.setVisibility(View.GONE);
                    showAdditionalButton.setText(SHOWTEXT);
                }
                else
                {
                    hideDetailsLinearLayout.setVisibility(View.VISIBLE);
                    showAdditionalButton.setText(HIDETEXT);
                }
            }
            else if (v.getId() == saveButton.getId())
            {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String jobTitle = jobTitleEditText.getText().toString();
                String company = companyEditText.getText().toString();
                String website = websiteEditText.getText().toString();
                String im = instantMsgEditText.getText().toString();

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (jobTitle != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
//                startActivity(intent);
                startActivityForResult(intent, CONTACTS_MANAGER_REQUEST_CODE);
            }
            else if (v.getId() == cancelButton.getId())
            {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);


        nameEditText = findViewById(R.id.editTextName);
        phoneEditText = findViewById(R.id.editTextPhoneNumber);
        emailEditText = findViewById(R.id.editTextEmail);
        addressEditText = findViewById(R.id.editTextAddress);
        jobTitleEditText = findViewById(R.id.editTextJob);
        companyEditText = findViewById(R.id.editTextCompany);
        websiteEditText = findViewById(R.id.editTextWebsite);
        instantMsgEditText = findViewById(R.id.editTextInstantMsg);

        showAdditionalButton = findViewById(R.id.showAdditionalButton);
        showAdditionalButton.setOnClickListener(buttonClickListener);
        saveButton = findViewById(R.id.saveButton);
        showAdditionalButton.setOnClickListener(buttonClickListener);
        cancelButton = findViewById(R.id.cancelButton);
        showAdditionalButton.setOnClickListener(buttonClickListener);


        hideDetailsLinearLayout = findViewById(R.id.buttonsHideArea);


        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, "Invalid phone number", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}
