package ch.uzh.ifi.seal.monolith2microservices.controllers;

import ch.uzh.ifi.seal.monolith2microservices.conversion.GraphRepresentation;
import ch.uzh.ifi.seal.monolith2microservices.dtos.DecompositionDTO;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import ch.uzh.ifi.seal.monolith2microservices.persistence.DecompositionRepository;
import ch.uzh.ifi.seal.monolith2microservices.persistence.RepositoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by gmazlami on 1/17/17.
 */
@Configuration
@EnableAutoConfiguration
@RestController
@Component
public class MicroservicesController {

    private Logger logger = LoggerFactory.getLogger(MicroservicesController.class);

    @Autowired
    DecompositionRepository decompositionRepository;

    @Autowired
    RepositoryRepository repositoryRepository;


    @CrossOrigin
    @RequestMapping(value="/microservices/{decompositionId}", method= RequestMethod.GET)
    public ResponseEntity<Set<GraphRepresentation>> getMicroservice(@PathVariable long decompositionId) throws Exception{
        Decomposition decomposition = decompositionRepository.findById(decompositionId);
        Set<GraphRepresentation> graph = decomposition.getServices().stream().map(GraphRepresentation::from).collect(Collectors.toSet());
        return new ResponseEntity<Set<GraphRepresentation>>(graph, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/microservices", method = RequestMethod.GET)
    public ResponseEntity<List<DecompositionDTO>> getMicroservices() throws Exception{
        List<Decomposition> decompositions = decompositionRepository.findAll();

        List<DecompositionDTO> dtos = new ArrayList<>();

        for(Decomposition d: decompositions){
            DecompositionDTO dto = new DecompositionDTO();
            dto.setDecompositionId(d.getId());
            dto.setRepo(d.getRepository());
            dto.setParameters(d.getParameters());
            if(dto.getRepo() != null){
                dtos.add(dto);
            }
        }

        return new ResponseEntity<List<DecompositionDTO>>(dtos, HttpStatus.OK);
    }


}
