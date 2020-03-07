package br.com.zup.zupclientservices.application;

import br.com.zup.zupclientservices.domain.Client;
import br.com.zup.zupclientservices.domain.exception.ClientValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ClientService {

    private ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client create(Client client) {
        validateBirthDate(client);

        validateAlreadyExistingCPF(client);

        return repository.saveAndFlush(client);
    }

    public void update(Client client) {
        validateBirthDate(client);

        validateAlreadyExistingCPF(client);

        repository.saveAndFlush(client);
    }

    private void validateBirthDate(Client client) {
        if (!client.getBirthDate().isBefore(LocalDate.now())) {
            throw new ClientValidationException("birthDate", "must be before than today");
        }
    }

    private void validateAlreadyExistingCPF(Client client) {
        Client firstByCpf = repository.findFirstByCpf(client.getCpf());
        if (!isEmpty(firstByCpf) &&
                firstByCpf.getId() != client.getId()) {
            throw new ClientValidationException("cpf", "already exists");
        }
    }
}
