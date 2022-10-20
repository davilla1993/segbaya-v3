package com.dev.segbaya.service.implementation;

import com.dev.segbaya.domain.PublishHouse;
import com.dev.segbaya.repo.PublishHouseRepo;
import com.dev.segbaya.service.PublishHouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PublishHouseServiceImpl implements PublishHouseService {

    private final PublishHouseRepo publishHouseRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PublishHouse publishBook(PublishHouse publishHouse) {
        return null;
    }

    @Override
    public PublishHouse getPublishHouse(Long id) {
        return publishHouseRepo.findById(id).orElseThrow(
                (() -> new IllegalStateException(
                        "PublishHouse with id "+ id + " does not exist")
                )
        );
    }

    @Override
    public List<PublishHouse> getPublishHouses() {
        return publishHouseRepo.findAll();
    }

    @Override
    public PublishHouse savePublishHouse(PublishHouse publishHouse) {
        Optional<PublishHouse> publishHouseOptional = publishHouseRepo
                .findByEmail(publishHouse.getEmail());
        if (publishHouseOptional.isPresent()) {
            throw new IllegalStateException("email "+ publishHouse.getEmail() + " already taken");
        }
        publishHouse.setPassword(passwordEncoder.encode(publishHouse.getPassword()));
        return publishHouseRepo.save(publishHouse);
    }

    @Transactional
    @Override
    public void updatePublishHouse(Long id, PublishHouse publishHouse) {
        PublishHouse publishHouseOptional = publishHouseRepo.findById(id).orElseThrow(
                (() -> new IllegalStateException(
                        "PublishHouse with id "+ id + " does not exist")
                ));

        if (publishHouse.getName() != null &&
                publishHouse.getName().length() > 0 ) {
            publishHouseOptional.setName(publishHouse.getName());
        }
        if (publishHouse.getEmail() != null &&
                publishHouse.getEmail().length() > 0 ) {
//            Optional<PublishHouse> publishHouseOptional2 = publishHouseRepo
//                    .findByEmail(publishHouse.getEmail());
//            if (publishHouseOptional2.isPresent()) {
//                throw new IllegalStateException("email "+ publishHouse.getEmail() + " already taken");
//            }
            publishHouseOptional.setEmail(publishHouse.getEmail());
        }
        if (publishHouse.getPassword() != null &&
                publishHouse.getPassword().length() > 0 ) {
            publishHouseOptional.setPassword(passwordEncoder.encode(publishHouse.getPassword()));
        }
        if (publishHouse.getImage() != null &&
                publishHouse.getImage().length() > 0 ) {
            publishHouseOptional.setImage(publishHouse.getImage());
        }
        if (publishHouse.getAddress() != null &&
                publishHouse.getAddress().length() > 0 ) {
            publishHouseOptional.setAddress(publishHouse.getAddress());
        }
        if (publishHouse.getWebsite() != null &&
                publishHouse.getWebsite().length() > 0 ) {
            publishHouseOptional.setWebsite(publishHouse.getWebsite());
        }
    }

    @Override
    public void deletePublishHouse(Long id) {
        PublishHouse publishHouse = publishHouseRepo.findById(id).orElseThrow(
                (() -> new IllegalStateException(
                        "PublishHouse with id "+ id + " does not exist")
                ));
        publishHouseRepo.deleteById(publishHouse.getIdPublishHouse());
    }
}
