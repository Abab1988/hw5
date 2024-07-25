package com.github.javarar.lucky.ticket;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LuckyTicketTest {

    @DisplayName("Задание 7. Счастливый билет. Ленинградский")
    @ParameterizedTest
    @MethodSource("cases")
    public void luckyTicketProbabilityLeningradskiyTest(int serialNumberLength, double probability) {
        long count = LuckyTicket.luckyTicketProbabilityLeningradskiy(serialNumberLength);
        assertEquals(BigDecimal.valueOf(probability), BigDecimal.valueOf(count / Math.pow(10, serialNumberLength)));
    }

    @DisplayName("Задание 7. Счастливый билет. Московский")
    @ParameterizedTest
    @MethodSource("cases")
    public void luckyTicketProbabilityMoskowskiyTest(int serialNumberLength, double probability) {
        long count = LuckyTicket.luckyTicketProbabilityMoskowskiy(serialNumberLength);
        assertEquals(BigDecimal.valueOf(probability), BigDecimal.valueOf(count / Math.pow(10, serialNumberLength)));
    }

    // При длинах от 1 до 10 вероятности в обеих системах равны https://open-life.org/blog/662.html
    // Размерности 10 и 12 выполняются очень долго. Поэтому они исключены из теста.
    private static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(4, 0.067),
                Arguments.of(6, 0.055252),
                Arguments.of(8, 0.0481603)
        );
    }

}