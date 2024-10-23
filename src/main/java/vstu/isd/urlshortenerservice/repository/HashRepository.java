package vstu.isd.urlshortenerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vstu.isd.urlshortenerservice.entity.Hash;

import java.util.List;

@Repository
public interface HashRepository extends JpaRepository<Hash, String> {
    @Modifying
    @Query(
            nativeQuery = true, value = """
            delete from hash
            where hash IN (
                select hash from hash limit :amount
            )
            returning *
            """
    )
    List<Hash> popAll(int amount);
}
