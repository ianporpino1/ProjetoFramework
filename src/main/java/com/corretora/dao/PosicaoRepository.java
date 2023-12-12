package com.corretora.dao;

import com.corretora.model.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosicaoRepository extends JpaRepository<Posicao, Long> {

    @Query(value = "SELECT a.identificador FROM posicao p JOIN ativo a ON p.id_ativo = a.id WHERE id_usuario = :idUsuario",nativeQuery = true)
    List<String> findTickers(@Param("idUsuario")Long idUsuario);
    @Query(value = "SELECT * FROM corretoradb.posicao WHERE identificador = :identificador AND id_usuario = :idUsuario",nativeQuery = true)
    Posicao findPosicaoByIdentificador(@Param("identificador") String identificador,@Param("idUsuario")Long idUsuario);

    @Query(value = "SELECT * FROM corretoradb.posicao WHERE id_ativo = :idAtivo AND id_usuario = :idUsuario",nativeQuery = true)
    Posicao findPosicaoByIdAtivo(@Param("idAtivo") Long idAtivo,@Param("idUsuario")Long idUsuario);

    @Query(value = "SELECT a.identificador, TRUNCATE(p.preco_medio, 2) AS preco_medio, p.quantidade_total, " +
                    "TRUNCATE(p.valor_total, 2) AS valor_total FROM posicao p JOIN ativo a ON p.id_ativo = a.id WHERE p.id_usuario = :idUsuario AND p.status_posicao=1", nativeQuery = true)
    List<Object[]> findFormattedPosicoes(@Param("idUsuario")Long idUsuario);
    @Modifying
    @Query(value = "delete FROM ativo a WHERE a.identificador = :identificador", nativeQuery = true)
    void deleteByTicker(@Param("identificador") String identificador);




}
