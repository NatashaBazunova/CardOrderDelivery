package ru.netology.order;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderTest {
    LocalDate currentDate = LocalDate.now().plusDays(6);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String meetDate = currentDate.format(formatter);



    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

    }


    @Test
    public void shouldSubmitOrder() {
        $("[data-test-id=city] input").val("Уфа");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+78956701518");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification]").shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(text("Успешно!"));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + meetDate));

    }
    @Test
    public void shouldNotSubmitOrderWithoutCity() {
        $("[data-test-id=city] input").val(" ");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+78956711378");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    public void shouldNotSubmitOrderWithoutDate() {
        $("[data-test-id=city] input").val("Краснодар");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(" ");
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+78956711378");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(withText("Неверно введена дата")).should(visible);
    }
    @Test
    public void shouldNotSubmitOrderWithoutName() {
        $("[data-test-id=city] input").val("Краснодар");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val(" ");
        $("[data-test-id=phone] input").val("+78956711378");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=name] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    public void shouldNotSubmitOrderWithoutPhone() {
        $("[data-test-id=city] input").val("Краснодар");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val(" ");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=phone] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    public void shouldNotSubmitOrderWithoutAgreement() {
        $("[data-test-id=city] input").val("Краснодар");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+79856321510 ");
//        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(".checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
    @Test
    public void shouldNotSubmitOrderWithEngCity() {
        $("[data-test-id=city] input").val("Ufa");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+78951231456 ");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=city] .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }
    @Test
    public void shouldNotSubmitOrderWithEngName() {
        $("[data-test-id=city] input").val("Уфа");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val("Ivanov Ivan");
        $("[data-test-id=phone] input").val("+78951231456 ");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=name] .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    public void shouldSubmitOrderWithDoubleName() {
        $("[data-test-id=city] input").val("Уфа");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val("Иванова Лидия-Виктория");
        $("[data-test-id=phone] input").val("+78951221474 ");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification]").shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(text("Успешно!"));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + meetDate));
    }

    @Test
    public void shouldNotSubmitOrderWithWrongPhone() {
        $("[data-test-id=city] input").val("Уфа");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val("Иванова Лидия-Виктория");
        $("[data-test-id=phone] input").val("+73333333");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=phone] .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(5));
    }
    @Disabled
    @Test
    public void shouldSubmitOrderWithLitter() {
        $("[data-test-id=city] input").val("Уфа");
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(meetDate);
        $("[data-test-id=name] input").val("Иванова Алёна");
        $("[data-test-id=phone] input").val("+78751231496 ");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification]").shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(text("Успешно!"));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + meetDate));
    }



}
