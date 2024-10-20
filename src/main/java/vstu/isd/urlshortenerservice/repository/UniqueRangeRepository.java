package vstu.isd.urlshortenerservice.repository;

import java.util.List;

public interface UniqueRangeRepository {
    List<Long> getNextUniqueRange(int size);
}
