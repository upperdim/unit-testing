package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.*;
import java.time.LocalDate;


class Test07VerifyingBehaviour {
	
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

	// verify() verifies that a specific method of a specific class was called with specfiic arguments
	// So that we can make sure the method under test performs the side effects correctly
	//
	// verifyNoMoreInteractions(mockObject) verifies that the method under test does not interact with the mockObject anymore 
	@Test
	void should_InvokePayment_when_Prepaid() {
		// Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		
		// Check whether this method was called with these specific arguments
		// verify(paymentServiceMock).pay(bookingRequest, 400.0);
		verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0); // makes sure it was called 1 time
		verifyNoMoreInteractions(paymentServiceMock);
	}
	
	// verify() and never() combines makes sure that the method was never called
	@Test
	void should_NotInvokePayment_when_NotPrepaid() {
		// Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		verify(paymentServiceMock, never()).pay(any(),  anyDouble());
	}

}
