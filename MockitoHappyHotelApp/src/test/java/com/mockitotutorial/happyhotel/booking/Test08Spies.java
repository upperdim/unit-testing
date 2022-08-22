package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.*;
import java.time.LocalDate;


class Test08Spies {
	
	private BookingService bookingService;
	
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;
	
	@BeforeEach
	void setup() {
		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = spy(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	// spy  is a real  object with    real logic that we can modify
	// mock is a dummy object with no real logic
	@Test
	void should_MakeBooking_when_InputOK() {
		// Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		// When
		String bookingId = bookingService.makeBooking(bookingRequest);
		
		// Then
		verify(bookingDAOMock).save(bookingRequest);
		
		// If we make mock(BookingDAO.class) in the @BeforeEach section, this will print null because mock objects return nice values
		// If we make  spy(BookingDAO.class however, it will return a real booking id
		// System.out.println("bookingId = " + bookingId); 
	}
	
	@Test
	void should_CancelBooking_when_InputOK() {
		// Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		bookingRequest.setRoomId("1.3");
		String bookingId = "1"; // pseudo, made up bookingId
		
		// When we call bookingService.cancelBooking with a made up bookingId,
		// it will call BookingDAO.get() with the made up bookingId and try to get the value
		// since bookingId doesn't exist in the Map, it will return null.
		// BookingService.cancelBooking will proceed to use the null value in the remaining of the method
		// and get NullPointerException.
		//
		// For this reason, we want to modify the behavior of spy(BookingDAO.class)
		//
		// Mock: when(mock.method()).thenReturn()
		// Spy:  doReturn().when(spy).method()
		//
		// bookingDAOMock spy get method will return bookingRequest when called with bookingId  
		doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);
		
		// When
		bookingService.cancelBooking(bookingId);
		
		// Then
		// No then.
	}

}
