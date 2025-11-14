package com.kinosoftware.backend.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronScheduler {

    private final MailService mailService;
    private final ReservationService reservationService;
    public CronScheduler(MailService mailService,ReservationService reservationService) {
        this.mailService = mailService;
        this.reservationService=reservationService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void scheduleEmailSend(){
        System.out.println("cron triggered!!!");
        int everyReservation = reservationService.thisDayReservations();
        mailService.sendEmail("tiago.antolagic.duarte0804@gmail.com","test","Hallooooo so viele Reservierung hast du gesamt"+everyReservation);
    }
}
