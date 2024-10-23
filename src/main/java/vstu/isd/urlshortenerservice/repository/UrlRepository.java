package vstu.isd.urlshortenerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vstu.isd.urlshortenerservice.entity.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
}
