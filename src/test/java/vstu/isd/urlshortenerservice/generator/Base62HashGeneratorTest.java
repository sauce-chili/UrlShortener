package vstu.isd.urlshortenerservice.generator;

import vstu.isd.urlshortenerservice.repository.UniqueRangeRepository;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class Base62HashGeneratorTest {
    @Mock
    UniqueRangeRepository repository;

    @InjectMocks
    Base62HashGenerator hashGenerator;

    @Test
    void generateOneHash_commonValueNumber() {
        when(repository.getNextUniqueRange(1)).thenReturn(List.of(123L));

        String expected = "1z";
        String actual = hashGenerator.generateHashes(1).findFirst().get();
        assertEquals(expected, actual);
    }

    @Test
    void generateOneHash_NumberForHashIsLow() {
        when(repository.getNextUniqueRange(1)).thenReturn(List.of(1L));

        String expected = "1";
        String actual = hashGenerator.generateHashes(1).findFirst().get();
        assertEquals(expected, actual);
    }

    @Test
    void generateOneHash_NumberForHashIsHigh() {
        when(repository.getNextUniqueRange(1)).thenReturn(List.of(803407143574L));

        String expected = "E8xCUx4";
        String actual = hashGenerator.generateHashes(1).findFirst().get();
        assertEquals(expected, actual);
    }

    @Test
    void generateFewHashes() {
        final int countOfElements = 10;

        List<Long> uniqRange = LongStream.rangeClosed(1, countOfElements).boxed().toList();

        when(repository.getNextUniqueRange(countOfElements)).thenReturn(uniqRange);

        Stream<String> actualUniq = hashGenerator.generateHashes(countOfElements).distinct();

        assertEquals(countOfElements, actualUniq.count());
    }

    @Test
    void generateALotHashes() {
        final int countOfElements = 5000;

        List<Long> uniqRange = LongStream.rangeClosed(1, countOfElements).boxed().toList();

        when(repository.getNextUniqueRange(countOfElements)).thenReturn(uniqRange);

        Stream<String> actualUniq = hashGenerator.generateHashes(countOfElements).distinct();

        assertEquals(countOfElements, actualUniq.count());
    }

    @Test
    void generatingZeroHashes() {
        final int amount = 0;

        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class, () -> hashGenerator.generateHashes(amount) );

        String expectedException = "amount must be greater than 0";
        assertEquals(expectedException, thrown.getMessage());
    }

    @Test
    void generatingLessThanZeroHashes() {
        final int amount = -1;

        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class, () -> hashGenerator.generateHashes(amount) );

        String expectedException = "amount must be greater than 0";
        assertEquals(expectedException, thrown.getMessage());
    }
}
