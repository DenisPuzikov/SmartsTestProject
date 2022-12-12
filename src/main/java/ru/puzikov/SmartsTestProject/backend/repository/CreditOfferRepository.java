package ru.puzikov.SmartsTestProject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.SmartsTestProject.backend.entity.CreditOffer;

import java.util.List;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {

    List<CreditOffer> findAllByClient_Bank_Id(Long bankId);
}
