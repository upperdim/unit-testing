package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;


class Test03ReturningCustomValues {
	
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

	// when(method).thenReturn(return value)
	// ___________________
	// | Test03          |
	// |_________________|
	//
	// | 1              ^ 4
	// V                |
	// ___________________
	// | BookingService  |
	// |_________________|
	//
	// | 2              ^ 3
	// V                |
	// ___________________
	// | RoomServiceMock |
	// |_________________|
	// 
	@Test
	void should_CountAvailablePlaces_when_OneRoomAvailable() {
		// Given
		when(this.roomServiceMock.getAvailableRooms())
			.thenReturn(Collections.singletonList(new Room("Room 1", 2)));

		int expected = 2;

		// When
		int actual = bookingService.getAvailablePlaceCount();
		
		// Then
		assertEquals(expected, actual);
	}
	
	@Test
	void should_CountAvailablePlaces_when_TwoRoomsAvailable() {
		// Given
		List<Room> rooms = Arrays.asList(new Room("Room 1", 2), new Room("Room 2", 5));
		
		when(this.roomServiceMock.getAvailableRooms())
			.thenReturn(rooms);

		int expected = 7;

		// When
		int actual = bookingService.getAvailablePlaceCount();
		
		// Then
		assertEquals(expected, actual);
	}

}
