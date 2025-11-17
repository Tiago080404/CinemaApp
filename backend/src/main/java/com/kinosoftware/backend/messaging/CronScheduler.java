package com.kinosoftware.backend.messaging;

import com.kinosoftware.backend.Service.MailService;
import com.kinosoftware.backend.Service.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CronScheduler {

    private final MailService mailService;
    private final ReservationService reservationService;

    public CronScheduler(MailService mailService, ReservationService reservationService) {
        this.mailService = mailService;
        this.reservationService = reservationService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void scheduleEmailSend() {
        System.out.println("cron triggered!!!");
        int everyReservation = reservationService.thisDayReservations();
        mailService.sendEmail("tiago.antolagic.duarte0804@gmail.com", "Reservierungen in total", "Hallo, so viele Reservierung gesamt heute:" + new Date() + everyReservation);
    }
}
