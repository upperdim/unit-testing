package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.*;


class Test10ArgumentCaptors {
	
	private BookingService bookingService;
	
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;
	
	// Argument captor captures the arguments passed to a method
	private ArgumentCaptor<Double> doubleCaptor;
	// private ArgumentCaptor<BookingRequest> bookingRequestCaptor; // example for another type
	
	@BeforeEach
	void setup() {
		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
		
		this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
	}

	@Test
	void should_PayCorrectPrice_when_InputOK() {
		// Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		
		// In the pay() method below, since captors work as matchers, we have to use a matcher for the first argument too. thus, eq()
		verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());
		double capturedArgument = doubleCaptor.getValue(); // get the captured argument
		
		// System.out.println("Captured argument = " + capturedArgument);
		assertEquals(400.0, capturedArgument);
	}
	
	// Capturing multiple arguments
	@Test
	void should_PayCorrectPrices_when_MultipleCalls() {
		// Given
		BookingRequest bookingRequest  = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 02), 2, true);
		List<Double> expectedValues = Arrays.asList(400.0, 100.0);
		
		// When
		bookingService.makeBooking(bookingRequest);
		bookingService.makeBooking(bookingRequest2);
		
		// Then
		verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
		List<Double> capturedArguments = doubleCaptor.getAllValues(); // get the captured arguments (multiple => arraylist)
		
		assertEquals(expectedValues, capturedArguments);
	}

}
