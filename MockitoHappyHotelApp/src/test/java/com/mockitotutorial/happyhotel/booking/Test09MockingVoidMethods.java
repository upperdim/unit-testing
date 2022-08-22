package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;


class Test09MockingVoidMethods {
	
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

	// void methods require different methods for throwing exceptions: doThrow(exception).when(mockObj).mockVoidMethod(args);
	@Test
	void should_ThrowException_when_MailNotReady() {
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		// Previously, we tested bookingService.makeBooking in the case of throwing an exception on line 32
		// now, we want to test it when it throws an exception on line 42, `mailSender.sendBookingConfirmation(bookingId);`
		// We can't just use it like the previously such as  `when(this.mailSenderMockMock.sendBookingConfirmation(any())).thenThrow(BusinessException.class);`
		// because when() can not be called with void inside.
		//
		doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any()); // or doThrow(BusinessException.class)...
		
		// When (What it actually does)
		Executable executable = () -> bookingService.makeBooking(bookingRequest);
		
		// Then (Compare)
		assertThrows(BusinessException.class, executable);
	}
	
	// to ensure a void method is not throwing an exception: doNothing()
	@Test
	void should_NotThrowException_when_MailReady() {
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		doNothing().when(mailSenderMock).sendBookingConfirmation(any()); // you can as well just skip this line and test will function the same
		
		// When (What it actually does)
		bookingService.makeBooking(bookingRequest);
		
		// Then (Compare)
		// no exception thrown
	}

}
