package com.example.irae4.fammap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import Model.Event;
import Model.LoginData;
import Model.Model;
import Model.Person;
import Model.User;
import Requests.RegisterRequest;
import Responce.EventResponse;
import Responce.LoginResponse;
import Responce.PersonResponse;
import Responce.RegisterResponse;
import Responce.Response;
import Responce.Error;
import ServerAccess.ServerProxy;


public class LoginFragment extends Fragment {

    private LoginData mLoginData;

    private EditText mHostField;
    private EditText mPortField;
    private EditText mUserNameField;
    private EditText mPasswordField;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private RadioGroup mGenderRadio;
    private RadioButton mMaleRadio;
    private RadioButton mFemaleRadio;

    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginData = new LoginData();//stores all needed data for login
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
//wire up the widgets
        mHostField = (EditText) v.findViewById(R.id.host_edit_text);
        mHostField.addTextChangedListener(new TextWatcher() {//listener to update host
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mLoginData.setHost(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {//check for login/register data
                if(checkLoginFields()){
                    mLoginButton.setEnabled(true);
                }
                else
                    mLoginButton.setEnabled(false);
                if(checkRegisterFields()){
                    mRegisterButton.setEnabled(true);
                }
                else
                    mRegisterButton.setEnabled(false);
            }
        });
        mPortField = (EditText) v.findViewById(R.id.port_edit_text);
        mPortField.addTextChangedListener(new TextWatcher() {//listener to update port
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mLoginData.setPort(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {//check for login/register data
                if(checkLoginFields()){
                    mLoginButton.setEnabled(true);
                }
                else
                    mLoginButton.setEnabled(false);
                if(checkRegisterFields()){
                    mRegisterButton.setEnabled(true);
                }
                else
                    mRegisterButton.setEnabled(false);
            }
        });
        mUserNameField = (EditText) v.findViewById(R.id.userName_edit_text);
        mUserNameField.addTextChangedListener(new TextWatcher() {//listener for username
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mLoginData.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {//check for login/register data
                if(checkLoginFields()){
                    mLoginButton.setEnabled(true);
                }
                else
                    mLoginButton.setEnabled(false);
                if(checkRegisterFields()){
                    mRegisterButton.setEnabled(true);
                }
                else
                    mRegisterButton.setEnabled(false);
            }
        });
        mPasswordField = (EditText) v.findViewById(R.id.password_edit_text);
        mPasswordField.addTextChangedListener(new TextWatcher() {//listener for password
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mLoginData.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {//check for login/register data
                if(checkLoginFields()){
                    mLoginButton.setEnabled(true);
                }
                else
                    mLoginButton.setEnabled(false);
                if(checkRegisterFields()){
                    mRegisterButton.setEnabled(true);
                }
                else
                    mRegisterButton.setEnabled(false);
            }
        });
        mFirstNameField = (EditText) v.findViewById(R.id.firstName_edit_text);
        mFirstNameField.addTextChangedListener(new TextWatcher() {//listener for first name
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                if(checkLoginFields()){
                    mLoginButton.setEnabled(true);
                }
                else
                    mLoginButton.setEnabled(false);
                if(checkRegisterFields()){
                    mRegisterButton.setEnabled(true);
                }
                else
                    mRegisterButton.setEnabled(false);
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mLoginData.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(checkRegisterFields()){//check for login/register data
                    mRegisterButton.setEnabled(true);
                }
                else
                    mRegisterButton.setEnabled(false);
            }
        });
        mLastNameField = (EditText) v.findViewById(R.id.lastName_edit_text);
        mLastNameField.addTextChangedListener(new TextWatcher() {//listener for last name
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mLoginData.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(checkRegisterFields()){//check for login/register data
                    mRegisterButton.setEnabled(true);
                }
                else
                    mRegisterButton.setEnabled(false);
            }
        });
        mEmailField = (EditText) v.findViewById(R.id.email_edit_text);
        mEmailField.addTextChangedListener(new TextWatcher() {//listener for email
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mLoginData.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(checkRegisterFields()){//check for login/register data
                    mRegisterButton.setEnabled(true);
                }
                else
                    mRegisterButton.setEnabled(false);
            }
        });

        mMaleRadio = (RadioButton) v.findViewById(R.id.radio_male);
        mFemaleRadio = (RadioButton) v.findViewById(R.id.radio_female);
        mGenderRadio = (RadioGroup) v.findViewById(R.id.gender_group);
        mGenderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {//listener for gender
                RadioButton checkedRadioButton = (RadioButton)mGenderRadio.findViewById(mGenderRadio.getCheckedRadioButtonId());

                if (checkedRadioButton == mMaleRadio)
                {
                    mLoginData.setGender("m");
                }
                else if (checkedRadioButton == mFemaleRadio){
                    mLoginData.setGender("f");
                }
                else {
                    mLoginData.setGender(null);
                }
                if(checkRegisterFields()){//check for login/register data
                    mRegisterButton.setEnabled(true);
                }
                else
                    mRegisterButton.setEnabled(false);
            }
        });



        mLoginButton = (Button) v.findViewById(R.id.login_button);
        mLoginButton.setEnabled(false);
        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//on click login
                mLoginButton.setEnabled(false);
                mRegisterButton.setEnabled(false);
                Toast.makeText(getActivity(), "Logging In", Toast.LENGTH_SHORT).show();
                LoginTask task = new LoginTask();
                task.execute(mLoginData);
            }
        });
        mRegisterButton = (Button) v.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);
        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//on click register
                mLoginButton.setEnabled(false);
                mRegisterButton.setEnabled(false);

                Toast.makeText(getActivity(), "Registering", Toast.LENGTH_SHORT).show();
                RegisterTask task = new RegisterTask();
                task.execute(mLoginData);
            }
        });


        return v;
    }
    private boolean checkRegisterFields(){//checks if data for register is all there
        if(mLoginData.getHost() == null || mLoginData.getHost().equals(""))
            return false;
        if(mLoginData.getPort() == null || mLoginData.getPort().equals(""))
            return false;
        if(mLoginData.getUserName() == null || mLoginData.getUserName().equals(""))
            return false;
        if(mLoginData.getPassword() == null || mLoginData.getPassword().equals(""))
            return false;

        if(mLoginData.getFirstName() == null || mLoginData.getFirstName().equals(""))
            return false;
        if(mLoginData.getLastName() == null || mLoginData.getLastName().equals(""))
            return false;
        if(mLoginData.getEmail() == null  || mLoginData.getEmail().equals(""))
            return false;
        if(mLoginData.getGender() == null || mLoginData.getGender().equals(""))
            return false;

        return true;
    }
    private boolean checkLoginFields(){//checks if data for register is all there
        if(mLoginData.getHost() == null || mLoginData.getHost().equals(""))
            return false;
        if(mLoginData.getPort() == null || mLoginData.getPort().equals(""))
            return false;
        if(mLoginData.getUserName() == null || mLoginData.getUserName().equals(""))
            return false;
        if(mLoginData.getPassword() == null || mLoginData.getPassword().equals(""))
            return false;
        return true;
    }

    public class LoginTask extends AsyncTask<LoginData, Integer, Boolean>{//login async
        Response response;
        String host;
        String port;
        LoginData data;
        String id;
        String firstName;
        String lastName;

        @Override
        protected Boolean doInBackground(LoginData...datas){
            data = datas[0];
            host = data.getHost();
            port = data.getPort();

            ServerProxy proxy = new ServerProxy();
            response = proxy.login(data);
            if(response instanceof Error){
                return false;
            }

//logs in
            LoginResponse resp = (LoginResponse)response;
            id = resp.getId();
            User.instance().setId(id);
            response = proxy.getPeople(resp.getAuthToken(), host, port);
            if(response instanceof Error){
                return false;
            }
            //gets people
            Person[] people = ((PersonResponse) response).getData();
            for(int i = 0; i < people.length; i++){
             if(people[i].getId().equals(id)){
                 firstName = people[i].getFirstName();
                 lastName = people[i].getLastName();
                 break;
             }
            }

            Model.instance().addPersons(people);
//gets events
            response = proxy.getEvents(resp.getAuthToken(), host, port);
            if(response instanceof Error){
                return false;
            }
            Event[] events = ((EventResponse) response).getData();

            Model.instance().addEvents(events);
            Model.instance().create();

            return true;
        }
        protected void onPostExecute(Boolean result) {
            if(result){
                String info ="Login Successful for " + firstName + " " + lastName;
                Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();

                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.afterLogin();//swaps to map fragment



            }
            else{
                Error error = (Error)response;
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
    public class RegisterTask extends AsyncTask<LoginData, Integer, Boolean>{//register async
        Response response;
        String host;
        String port;
        LoginData data;
        @Override
        protected Boolean doInBackground(LoginData...datas){
        data = datas[0];
        host = data.getHost();
        port = data.getPort();

        ServerProxy proxy = new ServerProxy();
        response = proxy.register(data);
            if(response instanceof Error){
            return false;
        }
//registers
        RegisterResponse resp = (RegisterResponse) response;
            String id = resp.getId();
            User.instance().setId(id);
        response = proxy.getPeople(resp.getAuthToken(), host, port);
            if(response instanceof Error){
            return false;
        }
        //gets people
            Person[] people = ((PersonResponse) response).getData();
            Model.instance().addPersons(people);
        response = proxy.getEvents(resp.getAuthToken(), host, port);
            if(response instanceof Error){
            return false;
        }
        //gets events
            Event[] events = ((EventResponse) response).getData();
            Model.instance().addEvents(events);

            Model.instance().create();

            return true;
    }
    protected void onPostExecute(Boolean result) {
        if(result){
            String info ="Register Successful for " + data.getFirstName() + " " + data.getLastName();
            Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();

            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.afterLogin();//starts map fragment

        }
        else{
            Error error = (Error)response;
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    }

}

