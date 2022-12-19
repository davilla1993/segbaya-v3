package com.dev.segbaya.service;

import com.dev.segbaya.domain.PublishHouse;

import java.util.List;

public interface PublishHouseService {
    PublishHouse publishBook(PublishHouse publishHouse);
    PublishHouse getPublishHouseById(Long id);
    List<PublishHouse> getPublishHouses();
    PublishHouse savePublishHouse(PublishHouse publishHouse);
    PublishHouse updatePublishHouse(Long id, PublishHouse publishHouse);
    void deletePublishHouse(Long id);
}
