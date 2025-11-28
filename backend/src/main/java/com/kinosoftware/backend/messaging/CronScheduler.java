package com.kinosoftware.backend.messaging;

import com.kinosoftware.backend.Service.MailService;
import com.kinosoftware.backend.Service.MovieService;
import com.kinosoftware.backend.Service.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CronScheduler {

    private final MailService mailService;
    private final ReservationService reservationService;
    private final MovieService movieService;

    public CronScheduler(MailService mailService, ReservationService reservationService, MovieService movieService) {
        this.mailService = mailService;
        this.reservationService = reservationService;
        this.movieService = movieService;
    }

    @Scheduled(cron = "0 0 */6 * * *")
    public void scheduleEmailSend() {
        System.out.println("cron triggered!!!");
        int everyReservation = reservationService.thisDayReservations();
        mailService.sendEmail("tiago.antolagic-duarte@telefonica.com", "Reservierungen in total", "Hallo, so viele Reservierung gesamt heute:" + new Date() + everyReservation);
    }
    @Scheduled(cron = "0 0 9 * * Mon")
    public void scheduleInsertNewMoviesAndShowTimes(){
        movieService.getMoviesForWeek();
    }
}
