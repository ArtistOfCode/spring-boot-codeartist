package com.codeartist.component.test.mock;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

/**
 * 邮件发送
 *
 * @author AiJiangnan
 * @date 2025/3/12
 */
public class MockJavaMailSender implements JavaMailSender {

    private static final Logger log = LoggerFactory.getLogger(MockJavaMailSender.class);

    @Override
    public MimeMessage createMimeMessage() {
        return this.createMimeMessage(new ByteArrayInputStream(new byte[0]));
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        try {
            return new MimeMessage(null, contentStream);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        try {
            String from = mimeMessage.getFrom()[0].toString();
            String[] to = Arrays.stream(mimeMessage.getRecipients(Message.RecipientType.TO))
                    .map(Objects::toString).toArray(String[]::new);
            String subject = mimeMessage.getSubject();
            logger(from, to, subject);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        for (MimeMessage mimeMessage : mimeMessages) {
            this.send(mimeMessage);
        }
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        try {
            MimeMessage mimeMessage = createMimeMessage();
            mimeMessagePreparator.prepare(mimeMessage);
            this.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        for (MimeMessagePreparator mimeMessagePreparator : mimeMessagePreparators) {
            this.send(mimeMessagePreparator);
        }
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        logger(simpleMessage.getFrom(), simpleMessage.getTo(), simpleMessage.getSubject());
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        for (SimpleMailMessage simpleMessage : simpleMessages) {
            this.send(simpleMessage);
        }
    }

    private void logger(String from, String[] to, String subject) {
        log.info("Email send mock from:{}, to:{}, subject:{}", from, Arrays.toString(to), subject);
    }
}
