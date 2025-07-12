package com.portfolio.myportfolio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import com.portfolio.myportfolio.dto.ContactRequest;
import com.portfolio.myportfolio.entity.ContactMessage;


public interface ContactService {


    public void processContactMessage(ContactRequest request);

    @Async
    public void sendEmailNotification(ContactRequest request);

    public Page<ContactMessage> getAllMessages(Pageable pageable);

    public long getUnreadCount();

    public ContactMessage markAsRead(Long id);

    public void deleteMessage(Long id);
}