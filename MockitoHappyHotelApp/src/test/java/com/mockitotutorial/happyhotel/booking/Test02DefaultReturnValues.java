package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import java.time.LocalDate;


class Test02DefaultReturnValues {
	
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
		
		// Demonstrates default return values from mocks for different types:
		System.out.println("List returned " + roomServiceMock.getAvailableRooms());
		System.out.println("Object returned " + roomServiceMock.findAvailableRoomId(null));
		System.out.println("Primitive returned " + roomServiceMock.getRoomCount());
	}

	@Test
	void should_CountAvailablePlaces() {
		// Given
		int expected = 0; 
		
		// When
		// Mockito will return "nice mocks" by default. Values such as empty list, null, 0 and false primitives.
		// 
		// Below method calls getAvailableRooms() method which returns a list of type Room. 
		// In this case it will return an empty list.
		// This method will count 0 available rooms because of that reason. Therefore expected value is 0.
		int actual = bookingService.getAvailablePlaceCount();
		
		// Then
		assertEquals(expected, actual);
	}

}
