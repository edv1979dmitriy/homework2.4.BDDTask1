package ru.netology.data;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    public DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    public static CardInfo getCardInfo(int index) {
        List<CardInfo> cards = new ArrayList<>();
        CardInfo firstCard = new CardInfo("5559 0000 0000 0001", 10_000,0);
        CardInfo secondCard = new CardInfo("5559 0000 0000 0002",10_000,1);
        cards.add(0, firstCard);
        cards.add(1, secondCard);
        return cards.get(index);
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String codeSMS;
    }

    @Value
    public static class CardInfo {
        String namberCard;
        int initialbalance;
        int index;
    }
}
