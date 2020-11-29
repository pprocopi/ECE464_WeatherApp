package com.example.ece464_weatherapp.ui.login;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;

import com.example.ece464_weatherapp.RegisterActivity;
import com.example.ece464_weatherapp.data.LoginRepository;
import com.example.ece464_weatherapp.data.Result;
import com.example.ece464_weatherapp.data.model.LoggedInUser;
import com.example.ece464_weatherapp.R;

import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        else if ((username.contains("@")) && (username.contains(".com"))){
            return true;
        } else {
            return false;
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        Pattern UpperCase = Pattern.compile("[A-Z]");
        Pattern LowerCase = Pattern.compile("[a-z]");
        Pattern DigitCase = Pattern.compile("[0-9]");
        Pattern SymbolCase = Pattern.compile("[!@#$%^*]");

        if (password == null) {
            return false;
        } else if (!UpperCase.matcher(password).find()) {
            return false;
        } else if (!LowerCase.matcher(password).find()) {
            return false;
        } else if (!DigitCase.matcher(password).find()) {
            return false;
        } else if (!SymbolCase.matcher(password).find()) {
            return false;
        } else if (password.trim().length() >= 8 && password.trim().length() <=20) {
            return true;
        }
        else {
            return false;
        }

    }

}


