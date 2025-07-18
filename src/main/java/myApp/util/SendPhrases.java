package myApp.util;

public enum SendPhrases {
    DELETE_PHRASE("Здравствуйте! Ваш аккаунт был удалён."),
    CREATE_PHRASE("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");

    private final String phrase;

    SendPhrases(String phrase) {
        this.phrase = phrase;
    }
    public String getPhrase() {
        return phrase;
    }
}
