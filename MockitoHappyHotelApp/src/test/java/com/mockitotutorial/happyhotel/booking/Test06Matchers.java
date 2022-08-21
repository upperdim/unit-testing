package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;


class Test06Matchers {
	
	private BookingService bookingService;
	
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;
	
	@BeforeEach
	void setup() {
		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	// Previously, .thenSomething() calls were responding to a specific argument
	// passed to the method, constructed by us.
	//
	// Now, let's make them respond to any type of arguments using argument matchers
	//
	// We previously tested line 32 exception possibility on BookingService.makeBooking method,
	// now we will try line 36 exception possibility. PaymentService.pay throws exceptions under certain conditions
	@Test
	void should_NotCompleteBooking_when_PriceTooHigh() {
		// Given (What it should do = throw an exception)
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		// For class types we use any(). Parameter for any() is optional
		// For primitives, we need to use primitive specific matches like anyDouble();
		//
		// Instead of any, if we wanted an exact primitive, we could use eq(400.0) for the second parameter for example
		//
		// anyString() will not mathc a null string object. any() should be used for null strings.
		when(this.paymentServiceMock.pay(any(BookingRequest.class), anyDouble()))
			.thenThrow(BusinessException.class);
		
		// When (What it actually does)
		Executable executable = () -> bookingService.makeBooking(bookingRequest);
		
		// Then (Compare)
		assertThrows(BusinessException.class, executable);
	}

}
