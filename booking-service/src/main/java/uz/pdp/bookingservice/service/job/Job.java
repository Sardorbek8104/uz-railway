package uz.pdp.bookingservice.service.job;

import uz.pdp.bookingservice.entity.Ticket;

public interface Job {
   ReserveType type();
   void execute(Ticket ticket);
}
