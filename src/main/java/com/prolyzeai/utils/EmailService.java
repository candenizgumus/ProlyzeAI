package com.prolyzeai.utils;


import com.prolyzeai.dto.request.MailDto;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService
{
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public boolean send(MailDto mailModel) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(mailModel.to());
            helper.setSubject(mailModel.subject());
            helper.setText(mailModel.message(), true); // true = isHtml
            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email to {}", mailModel.to(), e);
            throw new ProlyzeException(ErrorType.MAIL_SEND_FAIL);
        }
    }


    public void sendManagerInvitationEmail(String email, String name, String companyName, String password) {
        String html = loadTemplate("new-manager-invitation.html", Map.of(
                "name", name,
                "companyName", companyName,
                "email", email,
                "password", password,
                "logoUrl", "https://b.scdn.gr/images/sku_main_images/009454/9454633/xlarge_20220302161129_anderson_prolyze_hydrolysed_whey_protein_800gr_vanilia.jpeg"
        ));

        send(new MailDto(email, "ProlyzeAI'a Hoşgeldiniz", html));
    }

    private String loadTemplate(String templateName, Map<String, String> placeholders) {
        try (InputStream inputStream = new ClassPathResource("templates/" + templateName).getInputStream()) {
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            return content;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load email template: " + templateName, e);
        }
    }




}
