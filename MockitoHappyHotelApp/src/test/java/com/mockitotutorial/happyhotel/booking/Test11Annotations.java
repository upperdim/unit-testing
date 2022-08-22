package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

// We have to add the following annotation to the class in order to use Mockito annotations inside of it
@ExtendWith(MockitoExtension.class)
class Test11Annotations {
	
	// Instead of defining them here and doing common mocking operations in @BeforeEach,
	// we can use Mockito annotations for common mocking operations so that we can get the job done here entirely
	// or only leave a small amount of work for @BeforeEach section
	
	// Mocks and spies will be injected to this object
	@InjectMocks
	private BookingService bookingService;
	
	// Mock this class
	// You can as well use @Spy to create a spy for the class
	@Mock
	private PaymentService paymentServiceMock;
	
	@Mock
	private RoomService roomServiceMock;
	
	@Mock
	private BookingDAO bookingDAOMock;
	
	@Mock
	private MailSender mailSenderMock;
	
	// Like mocking, creating argument captors is also common and has an annotation. So you don't have to bother creating one in @BeforeEach
	@Captor
	private ArgumentCaptor<Double> doubleCaptor;
	
//	@BeforeEach
//	void setup() {
//		this.paymentServiceMock = mock(PaymentService.class);
//		this.roomServiceMock = mock(RoomService.class);
//		this.bookingDAOMock = mock(BookingDAO.class);
//		this.mailSenderMock = mock(MailSender.class);
//		
//		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
//		
//		this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
//	}

	@Test
	void should_PayCorrectPrice_when_InputOK() {
		// Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());
		double capturedArgument = doubleCaptor.getValue();
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
