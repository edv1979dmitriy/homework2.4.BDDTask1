package ru.netology.pages;

import ru.netology.data.DataHelper;
import static com.codeborne.selenide.Selenide.$;

public class AuthorizationPage {

    public AuthorizationPage() {
    }

    public VerificationPage loginOfPersonalAccount(DataHelper.AuthInfo info) {
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();
        return new VerificationPage();
    }
}