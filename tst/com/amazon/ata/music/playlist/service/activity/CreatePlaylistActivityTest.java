package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreatePlaylistActivityTest {
    @Mock
    private PlaylistDao playlistDao;
    private CreatePlaylistActivity createPlaylistActivity;

    @BeforeEach
    void setUp() {
        initMocks(this);
        createPlaylistActivity = new CreatePlaylistActivity(playlistDao);
    }

    @Test
    void handleRequest_createsPlaylist_returnsPlaylistResult() {
        // GIVEN
        String expectedName = "expectedName";
        String expectedCustomerId = "expectedCustomerId";
        List<String> expectedTags = Lists.newArrayList("tag");

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(expectedCustomerId)
                .withTags(expectedTags)
                .build();

        // WHEN
        CreatePlaylistResult result = createPlaylistActivity.handleRequest(request, null);

        // THEN
        verify(playlistDao).savePlaylist(any());
    }

    @Test
    void handleRequest_invalidCharacterUsed_throwsException() {
        // GIVEN
        String expectedName = "expectedName";
        String expectedCustomerId = "expected'CustomerId";
        List<String> expectedTags = Lists.newArrayList("tag");

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withName(expectedName)
                .withCustomerId(expectedCustomerId)
                .withTags(expectedTags)
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> createPlaylistActivity.handleRequest(request, null));
    }

}
