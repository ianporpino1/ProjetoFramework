package com.corretora.dao;

import com.corretora.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	@Query(value = "SELECT total_transacao, tipo_transacao FROM corretoradb.transacao WHERE id_usuario = :idUsuario",nativeQuery = true)
    List<Object[]> calcularSaldo(@Param("idUsuario")Long idUsuario);
	
    @Query(value = "SELECT ticker, quantidade, TRUNCATE(preco,2), TRUNCATE(total_transacao,2), tipo_transacao, data FROM corretoradb.transacao WHERE id_usuario = :idUsuario",nativeQuery = true)
    List<Object[]> calcularResumoTransacoes(@Param("idUsuario")Long idUsuario);

}
