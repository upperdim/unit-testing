package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test15StaticMethodsAnswers {

	@InjectMocks
	private BookingService bookingService;
	
	@Mock
	private PaymentService paymentServiceMock;
	
	@Mock
	private RoomService roomServiceMock;
	
	@Mock
	private BookingDAO bookingDAOMock;
	
	@Mock
	private MailSender mailSenderMock;
	
	@Captor
	private ArgumentCaptor<Double> doubleCaptor;

	// Answers feature in static method mocking.
	// It is unlikely that we will need to use "answers" feature.
	@Test
	void should_CalculateCorrectPrice() {
		try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {
			// Given
			BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
			double expected = 400.0 * 0.8;
			// Previously, our static method only returned a constant instead of actually converting the amount
			// mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);
			mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenAnswer(inv -> (double) inv.getArgument(0) * 0.8);
			
			// When
			double actual = bookingService.calculatePriceEuro(bookingRequest);
			
			// Then
			assertEquals(expected, actual);
		}
	}

}
