package com.portfolio.myportfolio.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.portfolio.myportfolio.dto.ContactRequest;
import com.portfolio.myportfolio.entity.ContactMessage;
import com.portfolio.myportfolio.repo.ContactMessageRepository;
import com.portfolio.myportfolio.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{
   @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void processContactMessage(ContactRequest request) {
        // Save to database
        ContactMessage message = new ContactMessage();
        message.setName(request.getName());
        message.setEmail(request.getEmail());
        message.setMessage(request.getMessage());
        
        contactMessageRepository.save(message);

        // Send email asynchronously
        sendEmailNotification(request);
    }

    @Async
    public void sendEmailNotification(ContactRequest request) {
        if (mailSender != null) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo("arindampal669@gmail.com"); // Your email
                message.setSubject("New Contact Form Submission from " + request.getName());
                message.setText(
                    "Name: " + request.getName() + "\n" +
                    "Email: " + request.getEmail() + "\n" +
                    "Message: " + request.getMessage()
                );
                message.setReplyTo(request.getEmail());
                
                mailSender.send(message);
            } catch (Exception e) {
                // Log error but don't fail the request
                System.err.println("Failed to send email: " + e.getMessage());
            }
        }
    }

    public Page<ContactMessage> getAllMessages(Pageable pageable) {
        return contactMessageRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public long getUnreadCount() {
        return contactMessageRepository.countByIsReadFalse();
    }

    public ContactMessage markAsRead(Long id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        
        message.setIsRead(true);
        return contactMessageRepository.save(message);
    }

    public void deleteMessage(Long id){
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nothing Found"));
        contactMessageRepository.deleteById(message.getId());
    }
}
