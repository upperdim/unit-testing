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

//When we use MockitoExtension, a feature called "Strict Stubbing" is automatically enabled.
@ExtendWith(MockitoExtension.class)
class Test13StrictStubbing {

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

	@Test
	void should_InvokePayment_when_Prepaid() {
		// Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		// Booking is not prepaid but we still try to mock the payment service 
		// which will be never used in this unit test because we are not going to invoke the payment service mock
		// because bookingRequest is not prepaid and in the makeBooking() method, 
		// if the bookingRequest is not prepaid, payment service is not invoked at all
		//
		// this will give the error "unnecessary stubbing"
		//
		// stubbing means defining behaviour with methods like when().thenReturn() etc.
		//
		// lenient(). ... method will supress this error
		lenient().when(paymentServiceMock.pay(any(),  anyDouble())).thenReturn("1");
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		// No exception is thrown
	}

}
