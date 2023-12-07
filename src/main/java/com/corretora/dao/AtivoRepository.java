package com.corretora.dao;

import com.corretora.model.Posicao;
import com.corretora.model.ativo.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {

    @Modifying
    @Query(value = "delete FROM corretoradb.ativo WHERE identificador = :identificador", nativeQuery = true)
    int deleteByIdentificador(@Param("identificador") String identificador);

    @Query(value = "SELECT * FROM corretoradb.ativo WHERE identificador = :identificador", nativeQuery = true)
    Ativo findByIdentificador(@Param("identificador") String identificador);

    //FAZER UPDATE SE ATIVO JA EXISTIR, PRECO
}
