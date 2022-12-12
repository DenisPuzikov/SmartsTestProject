package ru.puzikov.SmartsTestProject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.SmartsTestProject.backend.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByOffer_Id(Long creditOfferId);
}
