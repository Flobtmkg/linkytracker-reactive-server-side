package flo.linky.open.dataserver.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import flo.linky.open.dataserver.entities.ServerConfigEntity;
import reactor.core.publisher.Mono;

@Repository
public interface ServerConfigRepository extends R2dbcRepository<ServerConfigEntity, Integer> {
	
		public Mono<ServerConfigEntity> findFirstByOrderByIdDesc();
		
	
}
