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

// Behaviour Driven Development principle (BDD) makes code more readable.
// Our tests are separated as Given, When, Then but when() method is mostly used in Given instead of When
// BDD features of Mockito give aliases to methods to match BDD. They have the same functionality with different names.

@ExtendWith(MockitoExtension.class)
class Test12Bdd {

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
	
	// given().willReturn() 
	// instead of when().thenReturn()
	@Test
	void should_CountAvailablePlaces_when_OneRoomAvailable() {
		// Given
		// when(this.roomServiceMock.getAvailableRooms())
		//  	.thenReturn(Collections.singletonList(new Room("Room 1", 2)));
		given(this.roomServiceMock.getAvailableRooms())
			.willReturn(Collections.singletonList(new Room("Room 1", 2)));

		int expected = 2;

		// When
		int actual = bookingService.getAvailablePlaceCount();
		
		// Then
		assertEquals(expected, actual);
	}
	
	// then(mockObj).should(times(n)).mockMethod(args) 
	// instead of verify(mockObj, times(n)).mockMethod(args)
	@Test
	void should_InvokePayment_when_Prepaid() {
		// Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		// verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0); // makes sure it was called 1 time
		then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0);
		
		verifyNoMoreInteractions(paymentServiceMock);
	}

}
