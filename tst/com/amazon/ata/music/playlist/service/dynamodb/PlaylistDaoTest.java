package com.amazon.ata.music.playlist.service.dynamodb;

import com.amazon.ata.aws.dynamodb.DynamoDbClientProvider;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaylistDaoTest {
    @Mock
    private DynamoDBMapper dynamoDbMapper;

    private PlaylistDao playlistDao;

    @BeforeEach
    void setUp() {
        initMocks(this);
        playlistDao = new PlaylistDao(dynamoDbMapper);
    }

    @Test
    void getPlaylist_validPlaylist_returnsPlaylist () {
        // GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        String expectedCustomerId = "expectedCustomerId";
        int expectedSongCount = 0;
        List<String> expectedTags = Lists.newArrayList("tag");

        Playlist playlist = new Playlist();
        playlist.setId(expectedId);
        playlist.setName(expectedName);
        playlist.setCustomerId(expectedCustomerId);
        playlist.setSongCount(expectedSongCount);
        playlist.setTags(Sets.newHashSet(expectedTags));

        Mockito.when(dynamoDbMapper.load(Playlist.class, expectedId)).thenReturn(playlist);

        // WHEN
        Playlist playlistResult = playlistDao.getPlaylist(expectedId);

        // THEN
        assertEquals(expectedId, playlistResult.getId());
        assertEquals(expectedName, playlistResult.getName());
        assertEquals(expectedCustomerId, playlistResult.getCustomerId());
        assertEquals(expectedSongCount, playlistResult.getSongCount());
    }

    @Test
    void getPlaylist_nullPlaylist_throwsException() {
        // GIVEN
        String playlistId = null;

        // WHEN + THEN
        Assertions.assertThrows(PlaylistNotFoundException.class, () -> playlistDao.getPlaylist(playlistId));
    }

    @Test
    void savePlaylist_validPlaylist_savesPlaylist() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        String expectedCustomerId = "expectedCustomerId";
        int expectedSongCount = 0;
        List<String> expectedTags = Lists.newArrayList("tag");

        Playlist playlist = new Playlist();
        playlist.setId(expectedId);
        playlist.setName(expectedName);
        playlist.setCustomerId(expectedCustomerId);
        playlist.setSongCount(expectedSongCount);
        playlist.setTags(Sets.newHashSet(expectedTags));

        // WHEN
        playlistDao.savePlaylist(playlist);

        // THEN
        Mockito.verify(dynamoDbMapper).save(playlist);
    }
}
