package br.com.zup.zupclientservices.ui;

import br.com.zup.zupclientservices.application.ClientRepository;
import br.com.zup.zupclientservices.domain.Client;
import br.com.zup.zupclientservices.domain.assembler.ClientEntityAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;

@RestController
@RequestMapping(path = "rs/clients", produces= HAL_JSON_VALUE)
public class ClientEndpoint {

    private ClientRepository repository;

    private ClientResourceAssembler resourceAssembler;

    private PagedResourcesAssembler<Client> pagedResourcesAssembler;

    private ClientEntityAssembler entityAssembler;

    @Autowired
    public ClientEndpoint(ClientRepository repository, ClientResourceAssembler resourceAssembler, PagedResourcesAssembler<Client> pagedResourcesAssembler, ClientEntityAssembler entityAssembler) {
        this.repository = repository;
        this.resourceAssembler = resourceAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.entityAssembler = entityAssembler;
    }


    @GetMapping
    public ResponseEntity<PagedModel<ClientResource>> getClients(Pageable page) {
        Page<Client> clients = repository.findAll(page);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(clients, resourceAssembler));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResource> getClientById(@PathVariable long id) {
        return repository.findById(id)
                .map(resourceAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientResource> createClient(@Valid @RequestBody ClientResource resource) {
        Client client = repository.saveAndFlush(entityAssembler.toEntity(resource));
        return ResponseEntity.ok(resourceAssembler.toModel(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResource> updateClient(@PathVariable long id,
                                                       @Valid @RequestBody ClientResource resource) {
        if (repository.findById(id).isPresent()) {
            resource.setId(id);

            Client client = repository.saveAndFlush(entityAssembler.toEntity(resource));

            return ResponseEntity.ok(resourceAssembler.toModel(client));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClientById(@PathVariable long id) {
        Optional<Client> client = repository.findById(id);

        if (client.isPresent()) {
            repository.delete(client.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
