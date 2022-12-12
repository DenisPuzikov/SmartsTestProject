package ru.puzikov.SmartsTestProject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.SmartsTestProject.backend.entity.Credit;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findAllByBank_Id(Long bankId);
}
