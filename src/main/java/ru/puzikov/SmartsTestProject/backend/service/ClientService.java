package ru.puzikov.SmartsTestProject.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.puzikov.SmartsTestProject.backend.entity.Client;
import ru.puzikov.SmartsTestProject.backend.repository.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll(String bankName) {
        return clientRepository.findAllByBank_BankName(bankName);
    }

    public List<Client> findAll(String filterText, String bankName) {
        if (filterText == null || filterText.isEmpty()) {
            return clientRepository.findAllByBank_BankName(bankName);
        } else {
            return clientRepository.search(filterText, bankName);
        }

    }


    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }
}
