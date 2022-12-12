package ru.puzikov.SmartsTestProject.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.puzikov.SmartsTestProject.backend.entity.Bank;
import ru.puzikov.SmartsTestProject.backend.repository.BankRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;


    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    public void delete(Bank bank) {
        bankRepository.delete(bank);
    }

    public void save(Bank bank) {
        bankRepository.save(bank);
    }

    public Bank findByBankName(String bankName) {
        return bankRepository.findByBankName(bankName);
    }

    public Optional<Bank> findByBankId(Long bankId) {
        return bankRepository.findById(bankId);
    }
}
