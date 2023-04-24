package com.example.ECommerce.Repository;

import com.example.ECommerce.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface CardRespository extends JpaRepository<Card,Integer> {
}
