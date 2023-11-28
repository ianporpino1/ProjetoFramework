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
	
    @Query(value = "SELECT a.identificador, t.quantidade, TRUNCATE(a.preco,2), TRUNCATE(t.total_transacao,2), t.tipo_transacao, t.data FROM transacao t " +
            "LEFT JOIN Ativo a ON t.id_ativo = a.id WHERE id_usuario = :idUsuario",nativeQuery = true)
    List<Object[]> recuperarResumoTransacoes(@Param("idUsuario")Long idUsuario);

}
