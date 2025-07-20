package myApp.util;

public enum SendPhrases {
    DELETE_PHRASE("Здравствуйте! Ваш аккаунт был удалён."),
    CREATE_PHRASE("Здравствуйте! Ваш аккаунт на сайте был успешно создан."),
    DELETE_PHRASE_SUBJECT("Удаление аккаунта"),
    CREATE_PHRASE_SUBJECT("Создание аккаунта");
    private final String phrase;

    SendPhrases(String phrase) {
        this.phrase = phrase;
    }
    public String getPhrase() {
        return phrase;
    }
}
