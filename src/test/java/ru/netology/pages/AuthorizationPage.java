package ru.netology.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationPage {

    public AuthorizationPage() {
    }

    public static void loginOfPersonalAccount() {
        open("http://localhost:9999");
        $("[data-test-id='login'] input").setValue("vasya");
        $("[data-test-id='password'] input").setValue("qwerty123");
        $("[data-test-id='action-login']").click();
    }
}
