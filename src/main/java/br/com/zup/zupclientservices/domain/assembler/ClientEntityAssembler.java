package br.com.zup.zupclientservices.domain.assembler;

import br.com.zup.zupclientservices.domain.Client;
import br.com.zup.zupclientservices.ui.ClientResource;
import org.springframework.stereotype.Component;

@Component
public class ClientEntityAssembler {

    public Client toEntity(ClientResource resource) {
        Client client = new Client();

        client.setId(resource.getId());
        client.setName(resource.getName());
        client.setCpf(resource.getCpf());
        client.setBirthDate(resource.getBirthDate());
        client.setPostalCode(resource.getPostalCode());
        client.setNumber(resource.getNumber());
        client.setComplement(resource.getComplement());

        return client;
    }

}
