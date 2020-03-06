package br.com.zup.zupclientservices.ui;

import br.com.zup.zupclientservices.domain.Client;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClientResourceAssembler extends RepresentationModelAssemblerSupport<Client, ClientResource> {

    public ClientResourceAssembler() {
        super(ClientEndpoint.class, ClientResource.class);
    }

    @Override
    public ClientResource toModel(Client entity) {
        ClientResource resource = instantiateModel(entity);

        resource.setId(entity.getId());
        resource.setName(entity.getName());
        resource.setCpf(entity.getCpf());
        resource.setBirthDate(entity.getBirthDate());
        resource.setPostalCode(entity.getPostalCode());
        resource.setNumber(entity.getNumber());
        resource.setCompĺement(entity.getCompĺement());

        resource.add(linkTo(
                methodOn(ClientEndpoint.class)
                        .getClientById(entity.getId()))
                .withSelfRel());

        return resource;
    }
}
