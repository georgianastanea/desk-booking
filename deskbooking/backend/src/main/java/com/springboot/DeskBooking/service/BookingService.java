package com.springboot.DeskBooking.service;

import com.springboot.DeskBooking.dto.BookingDto;
import com.springboot.DeskBooking.dto.BookingHistoryDto;
import com.springboot.DeskBooking.email.EmailSender;
import com.springboot.DeskBooking.entity.Booking;
import com.springboot.DeskBooking.entity.BookingHistory;
import com.springboot.DeskBooking.entity.Office;
import com.springboot.DeskBooking.exceptions.CrudOperationException;
import com.springboot.DeskBooking.repository.BookingHistoryRepository;
import com.springboot.DeskBooking.repository.BookingRepository;
import com.springboot.DeskBooking.repository.OfficeRepository;
import com.springboot.DeskBooking.user.AppUser;
import com.springboot.DeskBooking.user.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class BookingService {
    private final BookingRepository bookingRepository;

    private final OfficeRepository officeRepository;
    private final BookingHistoryRepository historyRepository;

    private final AppUserRepository userRepository;
    private final EmailSender emailSender;
    private final HistoryService historyService;

    public BookingService(BookingRepository bookingRepository, OfficeRepository officeRepository, BookingHistoryRepository historyRepository, AppUserRepository userRepository, HistoryService historyService, EmailSender emailSender) {
        this.bookingRepository = bookingRepository;
        this.officeRepository = officeRepository;
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.historyService = historyService;
        this.emailSender = emailSender;
    }

    public BookingDto addBooking(BookingDto bookingDto) {
        AppUser user = userRepository.findById(bookingDto.getUserId()).orElseThrow(() -> {
            throw new CrudOperationException("User is not registered!");
        });

        Office office = officeRepository.findById(bookingDto.getOfficeId()).orElseThrow(() -> {
            throw new CrudOperationException("Invalid office!");
        });

        Booking booking = Booking.builder().user(user).office(office).date(transformDate(bookingDto.getDate())).build();
        bookingRepository.save(booking);

        bookingDto.setId(booking.getId());
        historyRepository.save(BookingHistory.builder().userId(user.getId()).officeNumber(office.getNumber()).date(booking.getDate()).build());

        sendSummaryEmail(bookingDto);

        return bookingDto;
    }

    public void removeBooking(Long id) {
        bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });

        bookingRepository.deleteById(id);
    }

    public BookingDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });
        return new ModelMapper().map(booking, BookingDto.class);
    }

    public BookingDto updateBooking(Long id, BookingDto newBooking) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("Booking does not exist");
        });

        AppUser user = userRepository.findById(newBooking.getUserId()).orElseThrow(() -> {
            throw new CrudOperationException("User does not exist");
        });
        Office office = officeRepository.findById(newBooking.getOfficeId()).orElseThrow(() -> {
            throw new CrudOperationException("Invalid office!");
        });


        booking.setUser(user);
        booking.setDate(newBooking.getDate());
        booking.setOffice(office);
        bookingRepository.save(booking);
        newBooking.setId(booking.getId());

        return newBooking;
    }

    public List<BookingDto> getAllBookings() {
        Iterable<Booking> iterableBookings = bookingRepository.findAll();
        List<BookingDto> bookings = new ArrayList<>();

        iterableBookings.forEach(booking -> bookings.add(BookingDto.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .officeId(booking.getOffice().getId())
                .date(booking.getDate())
                .build()));

        return bookings;
    }

    public String transformDate(String inputDate) {

        String initialDate = inputDate.substring(6, 21);
        return initialDate.substring(3, 6) + " " + initialDate.substring(6, 9) +
                " " + initialDate.substring(9, 11) + " " + initialDate.substring(11);

    }

    public List<BookingHistoryDto> getBookingsByUser(Long userId) {

        List<BookingHistoryDto> allBookings = historyService.getHistory();

        return allBookings.stream().filter(bookingHistoryDto -> userId.equals(bookingHistoryDto.getUserId()))
                .collect(Collectors.toList());

    }

    public void sendSummaryEmail(BookingDto booking) {

        AppUser user = userRepository.findById(booking.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));

        Office office = officeRepository.findById(booking.getUserId())
                .orElseThrow(() -> new IllegalStateException("Office does not exist!"));

        emailSender.send(user.getEmail(), buildEmail(user.getFirstName(), user.getLastName(), office.getNumber(), booking.getDate()));
    }

    private String buildEmail(String firstName, String lastName, Long officeNumber, String date) {

        String initialDate = date.substring(9, 21);
        String finalDate = initialDate.substring(0, 3) + " " + initialDate.substring(3, 6) +
                " " + initialDate.substring(6, 8) + " " + initialDate.substring(8);
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Reservation summary</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + firstName + " " + lastName + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for booking desk number " + officeNumber + " for " + finalDate + " </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <p> See you soon!</p> </p></blockquote>\n  " +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


}
