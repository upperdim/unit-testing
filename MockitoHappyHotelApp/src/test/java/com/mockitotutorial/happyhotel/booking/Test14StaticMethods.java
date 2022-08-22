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
class Test14StaticMethods {

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

	// It was previously impossible for Mockito to mock static classes. 
	// We had to use an additional library on top of Mockito to achieve this. 
	// But not anymore since 3.4. This feature is still experimental and turned off by default.
	//
	// Change the <artifactId>mockito-core</artifactId> to <artifactId>mockito-inline</artifactId> 
	// in dependencies > dependency inside pom.xml on the root folder
	@Test
	void should_CalculateCorrectPrice() {
		// Documentation says we should use try() with resources so the static mock becomes temporary
		try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {
			// Given
			BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
			double expected = 400.0;
			// Mocking a static method is a bit different, we will need a lambda
			mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);
			
			// When
			double actual = bookingService.calculatePriceEuro(bookingRequest);
			
			// Then
			assertEquals(expected, actual);
		}
	}

}
