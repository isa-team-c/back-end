package com.example.ISAproject.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.ISAproject.dto.UserDto;
import com.example.ISAproject.model.Equipment;
import com.example.ISAproject.model.Reservation;
import com.example.ISAproject.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private Environment env;

	@Async
	public void sendNotificaitionAsync(User user) throws MailException, InterruptedException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
		//Simulacija duze aktivnosti da bi se uocila razlika
		Thread.sleep(10000);
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Account activation");
		mail.setText("Welcome " + user.getName() + ",\n\nThank you for choosing MedBooking. To activate your account, please click the following link:\n\nhttp://localhost:4200/verification/" + user.getId() + "\n\nBest regards,\nThe MedBooking Team");
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendComplaintResponseMail(User user, String text) throws MailException, InterruptedException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
		//Simulacija duze aktivnosti da bi se uocila razlika
		Thread.sleep(10000);
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Odgovor na zalbu");
		mail.setText("Admin sistema je odgovorio na vasu zalbu. Tekst odgovora glasi: \n\n" + text);
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendReceiveConfirmationMail(User user) throws MailException, InterruptedException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
		//Simulacija duze aktivnosti da bi se uocila razlika
		Thread.sleep(10000);
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Potvrda o preuzimanju rezervisane opreme");
		mail.setText("Obavestavamo Vas da je rezervisana oprema uspesno preuzeta.\n\n");
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendReservationConfirmationEmail(Reservation reservation) throws Exception {
	    System.out.println("Async metoda se izvršava u drugom Threadu u odnosu na prihvaćeni zahtev. Thread id: " + Thread.currentThread().getId());
	    // Simulacija duže aktivnosti da bi se uočila razlika
	    Thread.sleep(10000);
	    System.out.println("Slanje reservation emaila...");

	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

	    // Set email details
	    helper.setTo(reservation.getUser().getEmail());
	    helper.setFrom(env.getProperty("spring.mail.username"));
	    helper.setSubject("Reservation Confirmation");
	    String emailContent = "Dear " + reservation.getUser().getName() + ",\n\n"
	            + "\n\nThank you for your reservation. To access reservation details, please scan the QR code below."
	            + "\n\nBest regards,\nThe MedBooking Team";

	    // Save the QR code image on disk
	    File qrCodeFile = saveQRCodeImage(reservation);

	    // Attach the QR code image to the email
	    helper.addAttachment("qrcode.png", qrCodeFile);

	    // Set the email content
	    helper.setText(emailContent, true);

	    // Send the email
	    javaMailSender.send(mimeMessage);

	    System.out.println("Reservation email sent!");
	}


	

    
	private File saveQRCodeImage(Reservation reservation) throws Exception {
	    int width = 300;
	    int height = 300;

	    QRCodeWriter qrCodeWriter = new QRCodeWriter();
	    String equipmentDetails = reservation.getEquipment().stream()
	            .map(equipment -> "Equipment ID: " + equipment.getId() +
	                    "\nName: " + equipment.getName() +
	                    "\nType: " + equipment.getType() +
	                    "\nDescription: " + equipment.getDescription())
	            .collect(Collectors.joining("\n\n"));
	    String reservationDetails = "Reservation id: " + reservation.getId() +
	            "\nStatus: " + reservation.getStatus() +
	            "\nPrice: " + reservation.getPrice() +
	            "\nEquipment: " + equipmentDetails +
	            "\nAppointment Date: " + reservation.getAppointment().getStartDate() +
	            "\nAppointment Duration: " + reservation.getAppointment().getDuration() +
	            "\nUser ID: " + reservation.getUser().getId() +
	            "\nCompany name: " + reservation.getAppointment().getCompanyAdministrator().getCompany().getName() +
	            "\nCompany Administrator: " + reservation.getAppointment().getCompanyAdministrator().getUser().getName();

	    // Generate the QR code bit matrix
	    BitMatrix bitMatrix = qrCodeWriter.encode(reservationDetails, BarcodeFormat.QR_CODE, width, height);

	    BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
	        }
	    }

	    // Save the QR code image on disk
	   // File qrCodeFile = new File("qrcode.png");
	    

	   
	    
	    // Postavite putanju do direktorijuma gde želite da čuvate QR kodove
	    String directoryPath = "./qrcodes/";

	    String timestamp = String.valueOf(System.currentTimeMillis());
	    //ovde sam izmenila da bi mi bilo lakse da dohvatim qr kodove za odredjenog korisnika
        String qrCodeImageName = reservation.getUser().getId() + "_"+ reservation.getId() + "_" + timestamp + ".png";

        // Create the full path to the QR code image
        //String qrCodeImagePath = directoryPath + qrCodeImageName;

	    // Kreirajte File objekat koristeći novu putanju
	    File qrCodeFile = new File(directoryPath + qrCodeImageName);
	    ImageIO.write(qrImage, "png", qrCodeFile);
	    reservation.setQrCode("qrcodes/" + qrCodeImageName);


	    reservationService.updateQRCode(reservation);
	    
	    
	    return qrCodeFile;

	}

   
}