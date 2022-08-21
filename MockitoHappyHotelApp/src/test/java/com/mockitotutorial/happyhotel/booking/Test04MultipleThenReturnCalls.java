package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;


class Test04MultipleThenReturnCalls {
	
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

	// First time we call the method, it will return a list of single room
	// Second time we call (second .thenReturn) it will return an empty list
	@Test
	void should_CountAvailablePlaces_when_CalledMultipleTimes() {
		// Given
		when(this.roomServiceMock.getAvailableRooms())
			.thenReturn(Collections.singletonList(new Room("Room 1", 2)))
			.thenReturn(Collections.emptyList());

		int expectedFirstCall = 2;
		int expectedSecondCall = 0;

		// When
		int actualFirstCall = bookingService.getAvailablePlaceCount();
		int actualSecondCall = bookingService.getAvailablePlaceCount();
		
		// Then
		assertAll(
			() -> assertEquals(expectedFirstCall, actualFirstCall),
			() -> assertEquals(expectedFirstCall, actualFirstCall)
		);
	}

}
