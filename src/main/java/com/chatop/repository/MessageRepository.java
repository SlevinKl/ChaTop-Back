
package com.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatop.entity.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

}
