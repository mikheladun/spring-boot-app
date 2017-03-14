package m;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class FileDataScheduler {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String EMAIL_MESSAGE_ADDRESS = "mail@spring.xyz";
    private final String EMAIL_MESSAGE_TEXT = "You got mail!";

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol("SMTP");
        javaMailSender.setHost("127.0.0.1");
        javaMailSender.setPort(25);
        return javaMailSender;
    }

    /**
     * <p>Poll for new items in the last hour and send an email</p>
     *
     * <p>This will run at every hour with 1 minute delay</p>
     */
    @Scheduled(fixedRate=60*60*1000, initialDelay=1*60*1000)
    public void runTask() throws Exception {
        int count = this.jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM FILEDATA WHERE TIMESTAMP >= ?", 
                Integer.class, 
                System.currentTimeMillis() - (4 * 60 * 60 * 1000) );

        if(count > 0) {
            this.mailSender().send(new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
                    message.setFrom(EMAIL_MESSAGE_ADDRESS);
                    message.addTo(EMAIL_MESSAGE_ADDRESS);
                    message.setSubject(EMAIL_MESSAGE_TEXT);
                    message.setText(EMAIL_MESSAGE_TEXT, true);
                }
            });
        }
    }
}