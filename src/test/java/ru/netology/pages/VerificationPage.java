package ru.netology.pages;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    public VerificationPage() {
    }

    public static String codeSMS = "12345";

    public static void verification() {
        $("[data-test-id='code'] input").setValue(codeSMS);
        $("[data-test-id='action-verify']").click();
    }
}
