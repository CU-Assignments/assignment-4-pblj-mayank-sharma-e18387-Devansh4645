//22BCS12740_DEVANSH_SANGHI

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private int availableSeats;
    private final Lock lock = new ReentrantLock();

    public TicketBookingSystem(int seats) {
        this.availableSeats = seats;
    }

    public void bookTicket(String customerName) {
        lock.lock();
        try {
            if (availableSeats > 0) {
                System.out.println(customerName + " successfully booked a seat. Remaining seats: " + (--availableSeats));
            } else {
                System.out.println(customerName + " failed to book. No seats available.");
            }
        } finally {
            lock.unlock();
        }
    }
}

class BookingThread extends Thread {
    private TicketBookingSystem system;
    private String customerName;

    public BookingThread(TicketBookingSystem system, String customerName, int priority) {
        this.system = system;
        this.customerName = customerName;
        setPriority(priority);
    }

    @Override
    public void run() {
        system.bookTicket(customerName);
    }
}

public class TicketBookingSystemMain {
    public static void main(String[] args) {
        TicketBookingSystem bookingSystem = new TicketBookingSystem(5);

        BookingThread vip1 = new BookingThread(bookingSystem, "VIP_1", Thread.MAX_PRIORITY);
        BookingThread vip2 = new BookingThread(bookingSystem, "VIP_2", Thread.MAX_PRIORITY);
        BookingThread user1 = new BookingThread(bookingSystem, "User_1", Thread.NORM_PRIORITY);
        BookingThread user2 = new BookingThread(bookingSystem, "User_2", Thread.NORM_PRIORITY);
        BookingThread user3 = new BookingThread(bookingSystem, "User_3", Thread.NORM_PRIORITY);
        BookingThread user4 = new BookingThread(bookingSystem, "User_4", Thread.NORM_PRIORITY);

        vip1.start();
        vip2.start();
        user1.start();
        user2.start();
        user3.start();
        user4.start();
    }
}
