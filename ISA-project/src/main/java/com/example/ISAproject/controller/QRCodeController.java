package com.example.ISAproject.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;  // Add this import statement

import javax.imageio.ImageIO;

import com.google.zxing.Result;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    @PostMapping("/upload")
    public String uploadQRCode(@RequestParam("file") MultipartFile file) {
        // Logic to process the uploaded QR code file and extract data
        // You can call a service method to handle this logic
        String qrCodeData = processQRCode(file);
        return qrCodeData;
    }

    // Add the logic to process the QR code file and extract data
    private String processQRCode(MultipartFile file) {
        // Implement the logic to read QR code data from the uploaded file
        // You can use a library like ZXing to decode QR codes

        // For example:
        try {
            byte[] qrCodeBytes = file.getBytes();
            String decodedData = decodeQRCode(qrCodeBytes);
            return decodedData;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing QR code.";
        }
    }

    private String decodeQRCode(byte[] qrCodeBytes) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(qrCodeBytes);
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

            if (bufferedImage == null) {
                return "Error decoding QR code. Invalid image format.";
            }

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
            QRCodeReader qrCodeReader = new QRCodeReader();
            Result qrCodeResult = qrCodeReader.decode(binaryBitmap);

            return qrCodeResult.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error decoding QR code.";
        }
    }
}

