package com.amazon.ata.music.playlist.service.dynamodb;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;

import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for an album using {@link AlbumTrack} to represent the model in DynamoDB.
 */
@Singleton
public class AlbumTrackDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an AlbumTrackDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the album_track table
     */
    @Inject
    public AlbumTrackDao(DynamoDBMapper dynamoDbMapper) {this.dynamoDbMapper = dynamoDbMapper;
    }

    public AlbumTrack getAlbumTrack(String asin, Integer trackNumber ) {
        AlbumTrack albumTrack = dynamoDbMapper.load(AlbumTrack.class, asin, trackNumber);
        if (albumTrack == null) {
            throw new PlaylistNotFoundException("Could not find playlist with " + asin + " and " + trackNumber);
        }

        return albumTrack;
    }
}
