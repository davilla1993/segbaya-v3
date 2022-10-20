package com.dev.segbaya.service;

import com.dev.segbaya.domain.PublishHouse;

import java.util.List;

public interface PublishHouseService {
    PublishHouse publishBook(PublishHouse publishHouse);
    PublishHouse getPublishHouse(Long id);
    List<PublishHouse> getPublishHouses();
    PublishHouse savePublishHouse(PublishHouse publishHouse);
    void updatePublishHouse(Long id, PublishHouse publishHouse);
    void deletePublishHouse(Long id);
}
