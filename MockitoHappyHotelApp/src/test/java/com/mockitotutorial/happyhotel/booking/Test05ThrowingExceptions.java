package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;


class Test05ThrowingExceptions {
	
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

	// when(...).thenThrow(...): make a mock throw exceptions
	//
	// RoomService.findAvailableRoomId() throws an exception when no rooms are available 
	@Test
	void should_ThrowException_when_NoRoomAvailable() {
		// Given (What it should do = throw an exception)
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		when(roomServiceMock.findAvailableRoomId(bookingRequest))
			.thenThrow(BusinessException.class);
		
		// When (What it actually does)
		Executable executable = () -> bookingService.makeBooking(bookingRequest);
		
		// Then (Compare)
		assertThrows(BusinessException.class, executable);
	}

}
