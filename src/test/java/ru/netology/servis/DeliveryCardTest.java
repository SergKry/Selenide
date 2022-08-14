package ru.netology.servis;


import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    String date(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }


    @Test
    void shouldValidData() {
        String data = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (data));
        $("[name = 'name' ]").setValue("Кругляковский Станисилав");
        $("[data-test-id=phone] input").setValue("+79081010445");
        $("[data-test-id=agreement").click();
        $$(By.className("button__content")).first().click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + data));
    }
}
