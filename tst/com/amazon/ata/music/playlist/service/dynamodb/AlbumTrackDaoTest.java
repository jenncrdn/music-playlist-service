package com.amazon.ata.music.playlist.service.dynamodb;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class AlbumTrackDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    private AlbumTrackDao albumTrackDao;

    @BeforeEach
    void setUp() {
        initMocks(this);
        albumTrackDao = new AlbumTrackDao(dynamoDBMapper);
    }

    @Test
    void getAlbumTrack_validAlbumTrack_returnsAlbumTrack() {
        String expectedAsin = "Expected asin";
        Integer expectedTrackNumber = 2;
        String expectedAlbumName = "Expected Name";
        String expectedSongTitle = "Expected Title";

        AlbumTrack albumTrack = new AlbumTrack();
        albumTrack.setAsin(expectedAsin);
        albumTrack.setAlbumName(expectedAlbumName);
        albumTrack.setTrackNumber(expectedTrackNumber);
        albumTrack.setSongTitle(expectedSongTitle);

        Mockito.when(dynamoDBMapper.load(AlbumTrack.class, expectedAsin, expectedTrackNumber)).thenReturn(albumTrack);

        AlbumTrack result = albumTrackDao.getAlbumTrack(expectedAsin, expectedTrackNumber);

        assertEquals(expectedAsin, result.getAsin());
        assertEquals(expectedTrackNumber, result.getTrackNumber());
        assertEquals(expectedAlbumName, result.getAlbumName());
        assertEquals(expectedSongTitle, result.getSongTitle());
    }

    @Test
    void getAlbumTrack_invalidAsin_throwsException() {
        String asin = null;
        Integer trackNumber = 2;

        // WHEN + THEN
        Assertions.assertThrows(PlaylistNotFoundException.class, () -> albumTrackDao.getAlbumTrack(asin, trackNumber));
    }
}
