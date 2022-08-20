package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import java.time.LocalDate;


class Test01FirstMocks {
	
	private BookingService bookingService;
	
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;
	
	@BeforeEach
	void setup() {
		// Class you want to test may require other classes
		// Normally you would create those required classes @BeforeEach test
		// But those classes might not be possible for you to create for unit testing (such as db connections, or internet requests when we are offline)
		// In this case, you can mock those classes for the sake of unit testing
		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	// The method we are testing here doesn't require any dependencies (classes) 
	// but since we still need to create the BookingService which required 4 other classes, we just mock them above
	@Test
	void should_CalculateCorrectPrice_when_CorrectInput() {
		// Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		double expected = 4 * 2 * 50.0;
		
		// When
		double actual = bookingService.calculatePrice(bookingRequest);
		
		// Then
		assertEquals(expected, actual);
	}

}
