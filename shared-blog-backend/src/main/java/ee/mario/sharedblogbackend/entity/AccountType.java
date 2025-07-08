package ee.mario.sharedblogbackend.entity;

public enum AccountType {
    WRITER, EDITOR, MANAGER
    // 0. WRITER ---> saab postitada ja kõik
    // 1. EDITOR ---> saab vaadata postitusi üle (approve) ja kustutada, muuta
    // 2. MANAGER ---> saab anda WRITER inimestele õigusi, et nad oleks admin

}
