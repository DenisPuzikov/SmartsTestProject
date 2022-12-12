package ru.puzikov.SmartsTestProject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.puzikov.SmartsTestProject.backend.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findByBankName(String bankName);
}
